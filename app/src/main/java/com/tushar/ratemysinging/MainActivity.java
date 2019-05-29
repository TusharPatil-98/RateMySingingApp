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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressDialog p;

    ArrayList<Users> users = new ArrayList<Users>();
    String TAG = "FirebaseDB";
    UserAdapter adapter;
    RecyclerView recyclerViewUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadData loadData = new LoadData();
        loadData.execute();


        Log.d("TaskCompleted ", "UserList created");
        recyclerViewUsers = findViewById(R.id.recycler_view);
        recyclerViewUsers.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    public void displayContent(Users newUser) {
        users.add(newUser);
        adapter = new UserAdapter(users);
        recyclerViewUsers.setAdapter(adapter);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    public void AddContent(View view) {
        displayContent(new Users("Demo", 15));
    }


    private class LoadData extends AsyncTask<String, String, ArrayList<Users>> {

        ArrayList<Users> users = new ArrayList<Users>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("Please wait...It is downloading");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected ArrayList<Users> doInBackground(String... strings) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                                    //users.add(new Users( name, age));
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                           /*
                            adapter = new UserAdapter(users);
                            recyclerViewUsers.setAdapter(adapter);
                            recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
*/
                            p.hide();

                        }

                    });
            Log.d("AsyncTask ", "Task Completed ");
            return users;
        }

        @Override
        protected void onPostExecute(ArrayList<Users> users) {
            super.onPostExecute(users);
        }
    }
}