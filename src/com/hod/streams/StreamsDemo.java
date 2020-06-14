package com.hod.streams;

import com.hod.lambdas.Genre;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsDemo {
    private static List<Movie> movies = List.of(
            new Movie("b", 10, Genre.THRILLER),
            new Movie("a", 20, Genre.ACTION),
            new Movie("c", 30, Genre.ACTION),
            new Movie("d", 30, Genre.COMEDY)
    );

    public static void test1() {
        // Declarative (Functional) Programming
        // filtering example
        Predicate<Movie> isPopular = movie -> movie.getLikes() > 10;
        var count = movies.stream()
                        .filter(isPopular)
                        .count();

        System.out.println(count);
    }

    public static void test2() {
        int[] numbers = {1, 2, 3};
        Arrays.stream(numbers)
                .forEach(n -> System.out.println(n));

        //slicing example
        var stream = Stream.generate(() -> Math.random());
        stream
            .limit(3) //intermediate operation
            .forEach(n -> System.out.println(n)); //terminal operation
    }

    public static void test3() {
        // mapping example
        movies.stream()
                .map(movie -> movie.getTitle())
                .forEach(name -> System.out.println(name));

        var stream = Stream.of(List.of(1, 2, 3), List.of(4, 5, 6));

        //flatten the 2 lists into one
        stream.flatMap(list -> list.stream())
                .forEach(n -> System.out.println(n));
    }

    public static void test4() {
        //slicing example - pagination
        movies.stream()
                .skip(1)
                .limit(2)
                .forEach(m -> System.out.println(m.getTitle()));

        movies.stream()
                .takeWhile(m -> m.getLikes() < 30)
                .forEach(m -> System.out.println(m.getTitle()));
    }

    public static void test5() {
        //sorting streams
        movies.stream()
//                .sorted((a, b) -> a.getTitle().compareTo(b.getTitle())) //can be replaced by below line
                .sorted(Comparator.comparing(Movie::getTitle).reversed())
                .forEach(System.out::println);
    }

    public static void test6() {
        //unique elements
        movies.stream()
//                .map(m -> m.getLikes()) //can also be written like line below
                .map(Movie::getLikes)
                .distinct()
//                .forEach(like -> System.out.println(like))
                .forEach(System.out::println);

    }

    public static void test7() {
        //peeking elements - can be used to debug
        movies.stream()
                .filter(m -> m.getLikes() > 10)
                .peek(m -> System.out.println("filtered: " + m.getTitle()))
                .map(Movie::getTitle)
                .peek(t -> System.out.println("mapped: " + t))
                .forEach(System.out::println);
    }

    public static void test8() {
        //reducers - matchers
        var result = movies.stream()
//                .allMatch(m -> m.getLikes() > 20);
//                .noneMatch(m -> m.getLikes() > 20);
                .anyMatch(m -> m.getLikes() > 20);
        System.out.println(result);

        //more reducers
        var res = movies.stream()
//                    .findAny()
                    .max(Comparator.comparing(Movie::getLikes))
//                    .findFirst()
                    .get();
        System.out.println(res.getTitle());
    }

    public static void test9() {
        //reducing a stream using accumulator
        // [10, 20, 30]
        // [30, 30]
        // [60]
        Optional<Integer> sum = movies.stream()
                .map(m -> m.getLikes())
//                .reduce((a, b) -> a + b)
                .reduce(Integer::sum);

        System.out.println(sum.orElse(0));
    }

    public static void test10() {
        //collectors
        var result = movies.stream()
                .filter(m -> m.getLikes() > 10)
//                .collect(Collectors.toSet())
//                .collect(Collectors.toMap(k -> k.getTitle(), v -> v.getLikes())) // key (title) value (likes);
//                .collect(Collectors.toMap(Movie::getTitle, Movie::getLikes));
//                .collect(Collectors.toMap(Movie::getTitle, m -> m)); // map movie item as value
                .collect(Collectors.toMap(Movie::getTitle, Function.identity()));
//                .collect(Collectors.toList());

        System.out.println(result);
    }

    public static void test11() {
        //more collectors
        var result = movies.stream()
                        .filter(m -> m.getLikes() > 10)
//                        .collect(Collectors.summingInt(m -> m.getLikes()));
//                        .collect(Collectors.summarizingInt(m -> m.getLikes()));
                        .map(Movie::getTitle)
                        .collect(Collectors.joining(", "));

        System.out.println(result);
    }

    public static void test12() {
        // more collectors - grouping
        var result = movies.stream()
//                        .collect(Collectors.groupingBy(Movie::getGenre));
//                        .collect(Collectors.groupingBy(Movie::getGenre, Collectors.toSet()));
//                        .collect(Collectors.groupingBy(Movie::getGenre, Collectors.counting())); //count the number of movies in each category
                            .collect(Collectors.groupingBy(Movie::getGenre,
                                    Collectors.mapping(Movie::getTitle, Collectors.joining(", ")))); // join the name of the movies with a comma
        System.out.println(result);
    }

    public static void test13() {
        //partitioning - Movies that have > 20 likes and Movies that have > 30 likes
        var result = movies.stream()
//                .collect(Collectors.partitioningBy(m -> m.getLikes() > 20));
                .collect(Collectors.partitioningBy(
                        m -> m.getLikes() > 20,
                        Collectors.mapping(
                                Movie::getTitle,
                                Collectors.joining(", ")))); //downstream collector

        System.out.println(result);
    }

    public static void test14() {
        //primitive type streams
        IntStream.rangeClosed(1, 5)
                .forEach(System.out::println);
    }
}
