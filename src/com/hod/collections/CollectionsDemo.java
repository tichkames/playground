package com.hod.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsDemo {

    public static void test1() {
        Collection<String> collection = new ArrayList<>();
        Collections.addAll(collection, "a", "b", "c");

        System.out.println(collection);
    }

    public static void test2(){
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("b", "2-email"));
        customers.add(new Customer("a", "3-email"));
        customers.add(new Customer("c", "1-email"));

        Collections.sort(customers, new EmailComparator());
        System.out.println(customers);
    }
}
