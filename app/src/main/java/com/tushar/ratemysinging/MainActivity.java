package com.tushar.ratemysinging;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import static com.google.firebase.firestore.DocumentChange.Type.ADDED;
import static com.google.firebase.firestore.DocumentChange.Type.MODIFIED;
import static com.google.firebase.firestore.DocumentChange.Type.REMOVED;

public class MainActivity extends AppCompatActivity {

    ProgressDialog p;
    FirebaseFirestore db;
    ArrayList<Users> users = new ArrayList<Users>();
    String TAG = "FirebaseDB";
    UserAdapter adapter;
    RecyclerView recyclerViewUsers;
    int count = 0;
    Boolean finished = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        LoadData loadData = new LoadData();
        loadData.execute();


        Log.d("TaskCompleted ", "UserList created");
        recyclerViewUsers = findViewById(R.id.recycler_view);
        recyclerViewUsers.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }

    public void displayContent(Users newUser) {
        users.add(newUser);
        //Collections.sort(users,new SortbyTimeStamp());
        refresh();
    }

    public void refresh(){
        adapter = new UserAdapter(users);

        recyclerViewUsers.setAdapter(adapter);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }
    public void AddContent(View view) {

    }


    private class LoadData extends AsyncTask<String, String, ArrayList<Users>> {

        ArrayList<Users> users = new ArrayList<Users>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected ArrayList<Users> doInBackground(String... strings) {
/*
            db.collection("USER")
                    .orderBy("createdDate", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            int pos = 0;
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + "  => " + document.getData());
                                    String name = String.valueOf(document.get("Name"));
                                    int age = Integer.parseInt(String.valueOf(document.get("Age")));
                                    displayContent(new Users(name, age));
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }

                            p.hide();

                        }

                    });

*/

            db.collection("USER")
                    .orderBy("createdDate", Query.Direction.DESCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshots,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.w(TAG, "listen:error", e);
                                return;
                            }


                            for (DocumentChange dc : snapshots.getDocumentChanges()) {
                                switch (dc.getType()) {

                                    case ADDED:
                                        Users u = new Users(String.valueOf(dc.getDocument().get("Name")), Integer.parseInt(String.valueOf(dc.getDocument().get("Age"))));
                                        Log.d(TAG, "New User: " + dc.getDocument().getData());
                                        boolean duplicate = false;
                                        for(Users tempU: users){
                                            if (tempU.getName().equals(u.getName()) && tempU.getAge() == u.getAge()) {
                                                duplicate = true;
                                                break;
                                            }
                                        }

                                        if (!duplicate && !finished) {
                                            displayContent(u);
                                        }

                                        if(!duplicate && finished){
                                            users.add(0,u);
                                            Log.d("Added New:"," Added After Finish");
                                            adapter.notifyItemInserted(0);
                                        }
                                        break;
                                    case MODIFIED:
                                        Log.d(TAG, "USer Modified: " + dc.getDocument().getData());
                                        users = new ArrayList<Users>();
                                        adapter.notifyItemRemoved(0);
                                        refresh();
                                        int i = 0;

                                        break;
                                    case REMOVED:
                                        Log.d(TAG, "User Removed: " + dc.getDocument().getData());
                                        for(Users tempU: users){

                                        }

                                        adapter.notifyDataSetChanged();
                                        refresh();
                                        break;
                                }
                            }
                            p.hide();
                            finished = true;
                        }
                    });


        /*
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.w(TAG, "Listen failed.", e);
                                return;
                            }

                            //List<String> cities = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : value) {
                                if (doc.get("Name") != null) {
                                    displayContent(new Users(doc.getString("Name"),Integer.parseInt(String.valueOf(doc.get("Age")))));
                                    Log.d("Changes::: ",doc.getString("Name"));
                                }
                            }
                        }
                    });
*/

            Log.d("AsyncTask ", "Task Completed ");
            return users;
        }

        @Override
        protected void onPostExecute(ArrayList<Users> users) {
            super.onPostExecute(users);
        }
    }
}