package com.hod.generics;

public class User implements Comparable<User> {

    private int points;

    public User(int points) {
        this.points = points;
    }

    @Override public int compareTo(User user) {
        // this < user -> -1
        // this == user -> 0
        // this > user -> 1
        if(this.points < user.points)
            return -1;
        if(this.points > user.points)
            return 1;

        return 0;
    }

    public int getPoints() {
        return points;
    }

    @Override public String toString() {
        return "User{" +
                "points=" + points +
                '}';
    }
}
