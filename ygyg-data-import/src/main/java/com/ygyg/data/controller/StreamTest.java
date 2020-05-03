package com.ygyg.data.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @author lianglong
 * @date 2020/4/23
 */
public class StreamTest {


    public static void main(String[] args) {

        Stream<String> stringStream = Stream.of("hello,world", "mail", "double", "one");

        List<Stream<String>> collect = stringStream.map(t -> letters(t)).collect(Collectors.toList());

        for (Stream<String> stream : collect) {

            stream.forEach(System.out::print);
            System.out.println();
        }
        stringStream = Stream.of("hello,world", "mail", "double", "one");
        List<String> collect1 = stringStream.flatMap(t -> letters(t)).collect(Collectors.toList());
        for (String s : collect1) {

            System.out.print(s);
        }
        stringStream = Stream.of("hello,world", "mail", "double", "one");
        Map<Integer, Set<String>> collect2 = stringStream.collect(groupingBy(String::length, mapping(Function.identity(), toSet())));
        stringStream = Stream.of("hello,world", "mail", "double", "one", "tail");

        System.out.println(1);

    }


    public static Stream<String> letters(String s) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            result.add(s.substring(i, i + 1));

        }
        return result.stream();
    }


}
