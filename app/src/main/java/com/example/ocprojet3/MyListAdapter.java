package com.example.ocprojet3;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ocprojet3.Models.Dog;
import com.example.ocprojet3.Models.Kennel;

import java.util.List;

public class MyListAdapter<T> extends ArrayAdapter<String> {

    private final Activity context;
    private final List<T> items;

    MyListAdapter(Activity context, List<T> items) {
        super(context, R.layout.listitem);
        this.context=context;
        this.items=items;
    }

    T getItems(int position){
     return items.get(position);
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listitem, null, true);

        TextView titleText = rowView.findViewById(R.id.title);
        ImageView imageView = rowView.findViewById(R.id.icon);
        TextView subtitleText = rowView.findViewById(R.id.subtitle);

        if (items.get(position).getClass() == Dog.class) {
            Dog dog = (Dog) items.get(position);
            titleText.setText(dog.getName());
            imageView.setImageResource(R.drawable.ic_launcher_background);
            subtitleText.setText(dog.getBreed());

        } else if (items.get(position).getClass() == Kennel.class){
            Kennel kennel = (Kennel) items.get(position);
            titleText.setText(kennel.getName());
            subtitleText.setText(kennel.getAddressId());

        } else {
            titleText.setText("Error");
            imageView.setImageResource(R.drawable.ic_launcher_background);
            subtitleText.setText("Error");
        }

        return rowView;
    }
}
