package com.tushar.ratemysinging;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;


public class Users {
    private String mName;
    private int mAge;

    public Users(String Name, int Age){
        mName = Name;
        mAge = Age;
    }

    public String getmName(){
        return mName;
    }

    public String getmAge(){
        return String.valueOf(mAge);
    }

}

/*
public class Users {
    private String mName;
    private int mAge;
    public Timestamp mtimeStamp;

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
    static ArrayList<Users> users = new ArrayList<Users>();

    public static ArrayList<Users> createUsersList(int numContacts, FirebaseFirestore db) {
        return users;
    }


}
*/