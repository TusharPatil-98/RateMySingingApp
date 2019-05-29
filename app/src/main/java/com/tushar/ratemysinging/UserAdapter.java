package com.tushar.ratemysinging;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<Users> mUsers;

    public UserAdapter(List<Users> users){
        mUsers = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();

        LayoutInflater inflator = LayoutInflater.from(context);
        View userView = inflator.inflate(R.layout.user_details,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder viewHolder, int i) {
        Users user = mUsers.get(i);

        TextView textView1 = viewHolder.nameTextView;
        textView1.setText(user.getName());

        TextView textView2 = viewHolder.ageTextView;
        textView2.setText(user.getAge());
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public TextView ageTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.user_name);
            ageTextView = itemView.findViewById(R.id.user_age);
        }
    }
}
