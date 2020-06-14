package com.hod.collections;

import java.util.Comparator;

public class EmailComparator implements Comparator<Customer> {

    @Override public int compare(Customer customer, Customer other) {
        return customer.getEmail().compareTo(other.getEmail());
    }
}
