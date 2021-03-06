package com.tushar.ratemysinging;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Users> mUserData;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView1;
        public TextView textView2;

        public MyViewHolder(View v) {
            super(v);
            textView1 = v.findViewById(R.id.user_name);
            textView2 = v.findViewById(R.id.user_age);
        }
    }

    public MyAdapter(ArrayList<Users> myDataset) {
        mUserData = myDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View userView = inflater.inflate(R.layout.user_details,parent,false);

        MyViewHolder myView = new MyViewHolder(userView);
        return myView;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Users userData = mUserData.get(position);
        TextView tw1 = holder.textView1;
        TextView tw2 = holder.textView2;
        tw1.setText(userData.getmName());
        tw2.setText(userData.getmAge());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mUserData.size();
    }
}
