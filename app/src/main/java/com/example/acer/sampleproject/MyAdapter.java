package com.example.acer.sampleproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyModelClass> modleobjects;

    public MyAdapter(Context context, ArrayList<MyModelClass> modleobjects) {
        this.context = context;
        this.modleobjects = modleobjects;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(context).inflate(R.layout.design,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder myViewHolder, int i) {

        MyModelClass myModelClass=modleobjects.get(i);
        myViewHolder.title.setText("Title:"+myModelClass.getTitle());
        myViewHolder.location.setText("Location:"+myModelClass.getLocation());
        myViewHolder.apply.setText("How to Apply:"+myModelClass.getHowApply());

    }

    @Override
    public int getItemCount() {
        return modleobjects.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,location,apply;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            location=itemView.findViewById(R.id.location);
            apply=itemView.findViewById(R.id.apply);
        }
    }
}
