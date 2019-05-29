package com.tushar.ratemysinging;

import com.google.firebase.firestore.FirebaseFirestore;

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
    static ArrayList<Users> users = new ArrayList<Users>();

    public static ArrayList<Users> createUsersList(int numContacts, FirebaseFirestore db) {
/*

        for (int i = 1; i <= numContacts; i++) {
            users.add(new Users("User " + ++lastUserId, i*10));
        }


        db.collection("USER")
                .orderBy("createdDate", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "  => " + document.getData());
                                String name = String.valueOf(document.get("Name"));
                                int age = Integer.parseInt(String.valueOf(document.get("Age")));
                                users.add(new Users("A " + name,age+5));

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
*/
        return users;
    }


}
