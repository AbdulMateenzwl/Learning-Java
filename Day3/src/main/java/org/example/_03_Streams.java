package org.example;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class _03_Streams {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // filter(Predicate)
        System.out.println("Filter even numbers:");
        list.stream().filter(n -> n % 2 == 0).forEach(n -> System.out.print(n + " "));
        // map(Function)
        System.out.println("Map to squares:");
        list.stream().map(n -> n * n).forEach(n -> System.out.print(n + " "));
        System.out.println();

        // distinct()
        System.out.println("Distinct numbers:");
        Stream.of(1, 2, 2, 3, 4, 4, 5).distinct().forEach(n -> System.out.println(n));

        // sorted()
        System.out.println("Sorted numbers:");
        Stream.of(5, 3, 2, 4, 1).sorted().forEach(n -> System.out.print(n + " "));

        // skip(n)
        System.out.println("Skip first 5 numbers:");
        list.stream().skip(5).forEach(n -> System.out.print(n + " "));

        // limit(n)
        System.out.println("Limit to first 5 numbers:");
        list.stream().limit(5).forEach(n -> System.out.print(n + " "));

        // reduce(BinaryOperator)
        System.out.println("Reduce to sum:");
        Optional<Integer> sum = list.stream().reduce((a, b) -> a + b);
        sum.ifPresent(s -> {
            System.out.println("Sum: " + s);
        });

        // collect(Collectors)
        List<Integer> evens = list.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());
        System.out.println("Evens: " + evens);

        // count()
        long count = list.stream().count();
        System.out.println("Count: " + count);

        // allMatch, anyMatch, noneMatch
        System.out.println("All > 0: " + list.stream().allMatch(n -> n > 0));
        System.out.println("Any > 5: " + list.stream().anyMatch(n -> n > 5));
        System.out.println("None < 0: " + list.stream().noneMatch(n -> n < 0));

        // findFirst()
        list.stream().findFirst().ifPresent(n -> System.out.println("First: " + n));

        // flatMap(Function)
        List<List<String>> namesNested = Arrays.asList(
                Arrays.asList("A", "B"),
                Arrays.asList("C", "D")
        );
        List<String> namesFlat = namesNested.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.println("Flattened: " + namesFlat);

        // peek(Consumer)
        List<Integer> peeked = list.stream()
                .peek(n -> System.out.println("Processing: " + n))
                .collect(Collectors.toList());

        // toArray()
        Object[] array = list.stream().toArray();
        System.out.println("Array: " + Arrays.toString(array));
    }
}