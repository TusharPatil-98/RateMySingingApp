package com.tushar.ratemysinging;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    ProgressDialog p;
    FirebaseFirestore db;
    ArrayList<Users> users = new ArrayList<Users>();
    String TAG = "FirebaseDB";

    boolean finished = false;
    boolean added = false;

    private RecyclerView recyclerView;
    private ArrayList<Users> userDataset = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        LoadData loadData = new LoadData();
        loadData.execute();

        myInit();
        Log.d("TaskCompleted ", "UserList created");

    }

    private void myInit() {
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new MyAdapter(userDataset);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private class LoadData extends AsyncTask<String, String, ArrayList<Users>> {

        Users u;

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
                                        u = new Users(String.valueOf(dc.getDocument().get("Name")), Integer.parseInt(String.valueOf(dc.getDocument().get("Age"))));
                                        Log.d(TAG, "New User: " + dc.getDocument().getData());

                                        if (finished && !added) {
                                            added = true;
                                            userDataset.add(0, u);
                                            adapter.notifyItemChanged(0);
                                        } else {
                                            userDataset.add(u);
                                        }
                                        break;
                                    case MODIFIED:

                                        Log.d(TAG, "User Modified: " + dc.getDocument().getData());

                                        u = new Users(String.valueOf(dc.getDocument().get("Name")), Integer.parseInt(String.valueOf(dc.getDocument().get("Age"))));
                                        if (finished && !added) {
                                            Users temp;
                                            int pos = 0;

                                            for (Users anUserDataset : userDataset) {
                                                temp = anUserDataset;

                                                if (temp.getmName().equals(u.getmName()) && !temp.getmName().equals(u.getmAge())) {
                                                    temp.setmAge(Integer.parseInt(u.getmAge()));
                                                    Log.d("Modified: ", u.getmName());

                                                    break;
                                                }
                                                pos++;
                                            }
                                            adapter.notifyItemChanged(pos);
                                        }
                                        break;
                                    case REMOVED:
                                        Log.d(TAG, "User Removed: " + dc.getDocument().getData());
                                        u = new Users(String.valueOf(dc.getDocument().get("Name")), Integer.parseInt(String.valueOf(dc.getDocument().get("Age"))));

                                        if (finished) {
                                            Users temp;
                                            int pos = 0;

                                            for (Users anUserDataset : userDataset) {
                                                temp = anUserDataset;

                                                if (temp.getmName().equals(u.getmName())) {
                                                    userDataset.remove(pos);

                                                    Log.d("Removed: ", u.getmName());

                                                    break;
                                                }
                                                pos++;
                                            }
                                            adapter.notifyItemRemoved(pos);
                                            adapter.notifyItemRangeChanged(pos, adapter.getItemCount());
                                        }

                                        break;
                                }
                            }
                            p.hide();
                            finished = true;
                            added = false;
                            adapter.notifyDataSetChanged();
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