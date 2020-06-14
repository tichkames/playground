package com.hod.collections;

public class Customer implements Comparable <Customer>{
    private String name;
    private String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override public int compareTo(Customer customer) {
        return this.name.compareTo(customer.name);
    }

    @Override public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
