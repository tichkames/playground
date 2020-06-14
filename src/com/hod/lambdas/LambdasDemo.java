package com.hod.lambdas;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class LambdasDemo {

    private String message;

    public LambdasDemo() {
    }

    private LambdasDemo(String message) {
        this.message = message;
    }

    public static void test1() {
//        String prefix = "-";
//        greet(message -> System.out.println(prefix + message));
        greet(LambdasDemo::print);

//        greet(LambdasDemo::new); //Constructor method reference

        //OLD WAY
//        greet(new Printer() {
//            @Override public void print(String message) {
//                System.out.println(message);
//            }
//        });
    }

    public static void print(String message){
        System.out.println(message);
    }

    public void print(){
        System.out.println(this.message);
    }

    public static void greet(Printer printer) {
        printer.print("Hello World");
    }

    public static void test2() {
        List<String> list = List.of("a", "b", "c");

        Consumer<String> print = (item) -> System.out.println(item);
        Consumer<String> printUpperCase = (item) -> System.out.println(item.toUpperCase());

        list.forEach(print.andThen(printUpperCase));
    }

    public static void test3() {
        Supplier<Double> getRandom = () -> Math.random();

        var random = getRandom.get();
        System.out.println(random);
    }

    public static void test4() {
        Function<String, Integer> map = str -> str.length();
        var length = map.apply("Hello World");
        System.out.println(length);

        // "key:value"
        //first: "key=value"
        //second "{key=value}"
        Function<String, String> replaceColon = str -> str.replace(":", "=");
        Function<String, String> addBraces = str -> "{".concat(str).concat("}");

        var result = replaceColon
                .andThen(addBraces)
                .apply("key:value");
        System.out.println(result);

        result = addBraces.compose(replaceColon).apply("key:value");
        System.out.println(result);
    }

    public static void test5() {
        //"sky
        Predicate<String> isLongerThan5 = str -> str.length() > 5;
        var result = isLongerThan5.test("sky-news");
        System.out.println(result);

        Predicate<String> hasLeftBrace = str -> str.startsWith("{");
        Predicate<String> hasRightBrace = str -> str.endsWith("}");

        var res = hasLeftBrace.and(hasRightBrace).test("{res}");
        System.out.println(res);
    }

    public static void test6() {
        // a, b -> a + b -> square
        BinaryOperator<Integer> add = (a, b) -> a + b;
        Function<Integer, Integer> square = a -> a * a;
        var result = add.andThen(square).apply(1, 2);
        System.out.println(result);
    }

    public static void test7() {
        UnaryOperator<Integer> square = n -> n * n;
        UnaryOperator<Integer> increment = n -> n + 1;

        var result = increment.andThen(square).apply(1);
        System.out.println(result);
    }
}
