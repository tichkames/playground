package com.hod.generics;

public class GenericList<T extends User> {
    private T[] items = (T[]) new User[10];
    private int count = 0;

    public void add(T item){
        items[count++] = item;
    }

    public T get(int index){
        return items[index];
    }
}
