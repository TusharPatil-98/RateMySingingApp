package com.tushar.ratemysinging;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList<Users> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewUsers = (RecyclerView) findViewById(R.id.recycler_view);

        users = Users.createUsersList(10);

        UserAdapter adapter = new UserAdapter(users);

        recyclerViewUsers.setAdapter(adapter);

        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
    }




}
