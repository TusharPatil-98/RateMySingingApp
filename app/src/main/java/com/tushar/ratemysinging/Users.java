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
