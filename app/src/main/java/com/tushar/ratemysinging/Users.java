package com.tushar.ratemysinging;

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

    public void setmName(String Name) {
        mName = Name;
    }

    public void setmAge(int Age) {
        mAge = Age;
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