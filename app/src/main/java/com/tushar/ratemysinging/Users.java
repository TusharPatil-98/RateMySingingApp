package com.tushar.ratemysinging;

import java.util.ArrayList;

public class Users {
    private String mName;
    private int mAge;

    public Users(String name, int age) {
        mName = name;
        mAge = age;
    }

    public String getName() {
        return mName;
    }

    public String getAge() {
        return String.valueOf(mAge);
    }

    private static int lastUserId = 0;

    public static ArrayList<Users> createUsersList(int numContacts) {
        ArrayList<Users> users = new ArrayList<Users>();

        for (int i = 1; i <= numContacts; i++) {
            users.add(new Users("User " + ++lastUserId, i*10));
        }

        return users;
    }


    

}
