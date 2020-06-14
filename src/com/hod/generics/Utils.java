package com.hod.generics;

public class Utils {

    public static <T extends Comparable<T>> T max(T first, T second) {
        return (first.compareTo(second) > 0 ? first : second);
    }

    public static void printUser(GenericList<? extends User> users) {
        System.out.println(users.get(0));
    }
}
