package com.lchen.walleapiclient.handler;

import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Optional;
import com.lchen.walleapiclient.model.RequestHandlerKey;
import com.lchen.walleapiclient.model.ResolvedMethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

/**
 * @author : lchen
 * @date : 2019/6/18
 */
public interface ApiRequestHandler {


  boolean isAnnotatedWith(Class<? extends Annotation> annotation);

  PatternsRequestCondition getPatternsCondition();

  String groupName();

  String getName();

  Set<RequestMethod> supportedMethods();

  Set<? extends MediaType> produces();

  Set<? extends MediaType> consumes();

  Set<NameValueExpression<String>> headers();

  Set<NameValueExpression<String>> params();

  <T extends Annotation> Optional<T> findAnnotation(Class<T> annotation);

  RequestHandlerKey key();

  List<ResolvedMethodParameter> getParameters();

  ResolvedType getReturnType();

  <T extends Annotation> Optional<T> findControllerAnnotation(Class<T> annotation);

//  @Incubating
  ApiRequestHandler combine(ApiRequestHandler other);
}
