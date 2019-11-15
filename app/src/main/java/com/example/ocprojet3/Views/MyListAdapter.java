package com.example.ocprojet3.Views;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.ocprojet3.Models.Kennel;
import com.example.ocprojet3.R;

import java.util.List;

public class MyListAdapter<T> extends RecyclerView.Adapter<MyListViewHolder> {

    private final Activity context;
    private final List<T> items;
    private RequestManager glide;

    public MyListAdapter(Activity context, List<T> items, RequestManager glide) {
        this.context=context;
        this.items=items;
        this.glide=glide;
    }

    public T getItem(int position){
     return items.get(position);
    }

    @NonNull
    @Override
    public MyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();

        View view;
        if(Kennel.class.equals(this.items.get(1).getClass())) {
            view = inflater.inflate(R.layout.listitems, parent, false);
        }
        else {
            view = inflater.inflate(R.layout.photolistitems, parent, false);
        }

        return new MyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyListViewHolder holder, int position) {
        holder.updateWithT(this.items.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
