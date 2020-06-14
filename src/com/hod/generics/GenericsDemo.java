package com.hod.generics;

public class GenericsDemo {

    public static void test1() {
        var user1 = new User(50);
        var user2 = new User(20);
        if(user1.compareTo(user2) > 0){
            System.out.println("User 1 is > User 2");
        }
    }

    public static void test2() {
        System.out.println(Utils.max(new User(50),new User(20)));
    }

    public static void test3() {
        var instructors = new GenericList<Instructor>();
        var users = new GenericList<User>();
        users.add(new User(500));
        Utils.printUser(users);
    }
}
