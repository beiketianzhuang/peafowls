package com.lchen.walleapiadmin;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

public class HttpRequesstTest {
    static class ObjPool<T, R> {
        final List<T> pool;
        // 用信号量实现限流器
        final Semaphore sem;

        // 构造函数
        ObjPool(int size, T t) {
            pool = new Vector<T>() {
            };
            for (int i = 0; i < size; i++) {
                pool.add(t);
            }
            sem = new Semaphore(size);
        }

        // 利用对象池的对象，调用 func
        R exec(Function<T, R> func) throws InterruptedException {
            T t = null;
            sem.acquire();
            try {
                t = pool.remove(0);
                return func.apply(t);
            } finally {
                pool.add(t);
                sem.release();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        List<Integer> list1 = Lists.newArrayList();
        list1.add(1);
        list1.add(5);
        list1.add(6);
        List<Integer> list2 = Lists.newArrayList();
        list2.add(2);
        list2.add(12);
        System.out.println(Stream.of(list1, list2).flatMap(Collection::stream).collect(Collectors.toSet()));
        System.out.println(list1);

        List<Person> peoples = Collections.singletonList(new Person());
        Map<String, Person> personMap = peoples.stream().collect(toMap(Person::getName, person -> person));
        Optional<Person> personOptional = peoples.stream().min(comparing(Person::getAge));
        String s = personOptional.map(person -> {
            System.out.println("");
            return person.getLocation();
        }).orElse("");

        Optional<Man> max = peoples.stream()
                .flatMap(person -> person.getMans().stream())
                .max(comparing(Man::getAge));

        String[] strs = new String[] {"hello","world"};
        Arrays.stream(strs).map(a-> a.split("")).flatMap(Arrays::stream);
        Integer integer = peoples.stream()
                .max(comparing(Person::getAge))
                .map(Person::getAge)
                .get();

        String collect = peoples.stream().map(Person::getName).collect(joining());
    }

    @Data
    static class Person {
        private String name;
        private String location;
        private Integer age;
        private List<Man> mans;

    }

    @Data
    static class Man {
        private String name;
        private Integer age;
    }
}
