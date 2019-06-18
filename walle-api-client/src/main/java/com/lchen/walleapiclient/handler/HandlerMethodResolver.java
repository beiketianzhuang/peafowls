

package com.lchen.walleapiclient.handler;

import com.fasterxml.classmate.MemberResolver;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.ResolvedTypeWithMembers;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.members.ResolvedMethod;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import com.lchen.walleapiclient.model.ResolvedMethodParameter;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;

/**
 * @author : lchen
 * @date : 2019/6/18
 */
public class HandlerMethodResolver {

  private static final String SPRING4_DISCOVERER = "org.springframework.core.DefaultParameterNameDiscoverer";
  private final ParameterNameDiscoverer parameterNameDiscover = parameterNameDiscoverer();
  private final TypeResolver typeResolver;

  public HandlerMethodResolver(TypeResolver typeResolver) {
    this.typeResolver = typeResolver;
  }

  public ResolvedType methodReturnType(HandlerMethod handlerMethod) {
    return resolvedMethod(handlerMethod).map(toReturnType(typeResolver)::apply).orElse(typeResolver.resolve(Void.TYPE));
  }

  public static Optional<Class> useType(Class beanType) {
    if (Proxy.class.isAssignableFrom(beanType)) {
      return Optional.absent();
    }
    if (Class.class.getName().equals(beanType.getName())) {
      return Optional.absent();
    }
    return Optional.fromNullable(beanType);
  }

  public List<ResolvedMethodParameter> methodParameters(final HandlerMethod methodToResolve) {
    return resolvedMethod(methodToResolve).map(toParameters(methodToResolve)::apply).orElse(Lists.<ResolvedMethodParameter>newArrayList());
  }

  boolean contravariant(ResolvedType candidateMethodReturnValue, Type returnValueOnMethod) {
    return isSubClass(candidateMethodReturnValue, returnValueOnMethod)
        || isGenericTypeSubclass(candidateMethodReturnValue, returnValueOnMethod);
  }


  @VisibleForTesting
  static Ordering<ResolvedMethod> byArgumentCount() {
    return Ordering.from((first, second) -> Ints.compare(first.getArgumentCount(), second.getArgumentCount()));
  }

  @VisibleForTesting
  boolean bothAreVoids(ResolvedType candidateMethodReturnValue, Type returnType) {
    return (Void.class == candidateMethodReturnValue.getErasedType()
                || Void.TYPE == candidateMethodReturnValue.getErasedType())
        && (Void.TYPE == returnType
                || Void.class == returnType);
  }
  @VisibleForTesting
  boolean isGenericTypeSubclass(ResolvedType candidateMethodReturnValue, Type returnValueOnMethod) {
    return returnValueOnMethod instanceof ParameterizedType &&
        candidateMethodReturnValue.getErasedType()
            .isAssignableFrom((Class<?>) ((ParameterizedType) returnValueOnMethod).getRawType());
  }

  @VisibleForTesting
  boolean isSubClass(ResolvedType candidateMethodReturnValue, Type returnValueOnMethod) {
    return returnValueOnMethod instanceof Class
        && candidateMethodReturnValue.getErasedType().isAssignableFrom((Class<?>) returnValueOnMethod);
  }

  @VisibleForTesting
  boolean covariant(ResolvedType candidateMethodArgument, Type argumentOnMethod) {
    return isSuperClass(candidateMethodArgument, argumentOnMethod)
        || isGenericTypeSuperClass(candidateMethodArgument, argumentOnMethod);
  }

  @VisibleForTesting
  boolean isGenericTypeSuperClass(ResolvedType candidateMethodArgument, Type argumentOnMethod) {
    return argumentOnMethod instanceof ParameterizedType &&
        ((Class<?>) ((ParameterizedType) argumentOnMethod).getRawType())
            .isAssignableFrom(candidateMethodArgument.getErasedType());
  }

  @VisibleForTesting
  boolean isSuperClass(ResolvedType candidateMethodArgument, Type argumentOnMethod) {
    return argumentOnMethod instanceof Class
        && ((Class<?>) argumentOnMethod).isAssignableFrom(candidateMethodArgument.getErasedType());
  }

  private java.util.Optional<ResolvedMethod> resolvedMethod(HandlerMethod handlerMethod) {
    if (handlerMethod == null) {
      return java.util.Optional.empty();
    }
    Class hostClass = useType(handlerMethod.getBeanType())
        .or(handlerMethod.getMethod().getDeclaringClass());
    ResolvedType beanType = typeResolver.resolve(hostClass);
    MemberResolver resolver = new MemberResolver(typeResolver);
    resolver.setIncludeLangObject(false);
    ResolvedTypeWithMembers typeWithMembers = resolver.resolve(beanType, null, null);
    Iterable<ResolvedMethod> filtered = filter(newArrayList(typeWithMembers.getMemberMethods()),
        methodNamesAreSame(handlerMethod.getMethod()));
    return resolveToMethodWithMaxResolvedTypes(filtered, handlerMethod.getMethod());
  }

  private static Function<ResolvedMethod, ResolvedType> toReturnType(final TypeResolver resolver) {
    return input -> Optional.fromNullable(input.getReturnType()).or(resolver.resolve(Void.TYPE));
  }

  private Function<ResolvedMethod, List<ResolvedMethodParameter>> toParameters(final HandlerMethod methodToResolve) {
    return input -> {
      List<ResolvedMethodParameter> parameters = newArrayList();
      MethodParameter[] methodParameters = methodToResolve.getMethodParameters();
      for (int i = 0; i < input.getArgumentCount(); i++) {
        parameters.add(new ResolvedMethodParameter(
                discoveredName(methodParameters[i]).orElse(String.format("param%s", i)),
            methodParameters[i],
            input.getArgumentType(i)));
      }
      return parameters;
    };
  }

  private static Iterable<ResolvedMethod> methodsWithSameNumberOfParams(
      Iterable<ResolvedMethod> filtered,
      final Method methodToResolve) {

    return filter(filtered, input -> input.getArgumentCount() == methodToResolve.getParameterTypes().length);
  }

  private static Predicate<ResolvedMethod> methodNamesAreSame(final Method methodToResolve) {
    return input -> input.getRawMember().getName().equals(methodToResolve.getName());
  }

  private java.util.Optional<ResolvedMethod> resolveToMethodWithMaxResolvedTypes(
      Iterable<ResolvedMethod> filtered,
      Method methodToResolve) {
    if (Iterables.size(filtered) > 1) {
      Iterable<ResolvedMethod> covariantMethods = covariantMethods(filtered, methodToResolve);
      if (Iterables.size(covariantMethods) == 0) {
        return StreamSupport.stream(filtered.spliterator(), false).filter(sameMethod(methodToResolve)).findFirst();
      } else if (Iterables.size(covariantMethods) == 1) {
        return StreamSupport.stream(covariantMethods.spliterator(), false).findFirst();
      } else {
        return java.util.Optional.of(byArgumentCount().max(covariantMethods));
      }
    }
    return StreamSupport.stream(filtered.spliterator(), false).findFirst();
  }

  private java.util.function.Predicate<ResolvedMethod> sameMethod(final Method methodToResolve) {
    return input -> methodToResolve.equals(input.getRawMember());
  }

  private Iterable<ResolvedMethod> covariantMethods(
      Iterable<ResolvedMethod> filtered,
      final Method methodToResolve) {

    return StreamSupport.stream(methodsWithSameNumberOfParams(filtered, methodToResolve).spliterator(), false).filter(onlyCovariantMethods(methodToResolve)).collect(Collectors.toList());
  }

  private java.util.function.Predicate<ResolvedMethod> onlyCovariantMethods(final Method methodToResolve) {
    return input -> {
      for (int index = 0; index < input.getArgumentCount(); index++) {
        if (!covariant(input.getArgumentType(index), methodToResolve.getGenericParameterTypes()[index])) {
          return false;
        }
      }
      ResolvedType candidateMethodReturnValue = returnTypeOrVoid(input);
      return bothAreVoids(candidateMethodReturnValue, methodToResolve.getGenericReturnType())
          || contravariant(candidateMethodReturnValue, methodToResolve.getGenericReturnType());
    };
  }

  private ResolvedType returnTypeOrVoid(ResolvedMethod input) {
    ResolvedType returnType = input.getReturnType();
    if (returnType == null) {
      returnType = typeResolver.resolve(Void.class);
    }
    return returnType;
  }


  private java.util.Optional<String> discoveredName(MethodParameter methodParameter) {
    String[] discoveredNames = parameterNameDiscover.getParameterNames(methodParameter.getMethod());
    int discoveredNameCount = java.util.Optional.ofNullable(discoveredNames).orElse(new String[0]).length;
    assert discoveredNames != null;
    return methodParameter.getParameterIndex() < discoveredNameCount
           ? java.util.Optional.ofNullable(emptyToNull(discoveredNames[methodParameter.getParameterIndex()]))
           : java.util.Optional.ofNullable(methodParameter.getParameterName());
  }


  private ParameterNameDiscoverer parameterNameDiscoverer() {
    ParameterNameDiscoverer dicoverer;
    try {
      dicoverer = (ParameterNameDiscoverer) Class.forName(SPRING4_DISCOVERER).newInstance();
    } catch (Exception e) {
      dicoverer = new LocalVariableTableParameterNameDiscoverer();
    }
    return dicoverer;
  }
}
