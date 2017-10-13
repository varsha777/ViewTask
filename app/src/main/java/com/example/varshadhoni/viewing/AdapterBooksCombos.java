package com.example.varshadhoni.viewing;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

public class AdapterBooksCombos extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<DataBooksCombos> data = Collections.emptyList();

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterBooksCombos(Context context, List<DataBooksCombos> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_book_combos, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        DataBooksCombos current = data.get(position);
        myHolder.textBookName.setText(current.bookName);
        myHolder.textDuration.setText("Duration: " + current.duration);
        myHolder.textDescription.setText("About Book : " + current.descrition);
        myHolder.textPrice.setText("Rs. " + current.price);
        myHolder.textPrice.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        // load image into imageview using glide
        Glide.with(context).load(current.bookImage)
                .error(R.mipmap.ic_launcher)
                .into(myHolder.ivBook);

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView textBookName;
        ImageView ivBook;
        TextView textDuration;
        TextView textDescription;
        TextView textPrice;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textBookName = (TextView) itemView.findViewById(R.id.textBookName);
            ivBook = (ImageView) itemView.findViewById(R.id.ivBook);
            textDuration = (TextView) itemView.findViewById(R.id.textDuration);
            textDescription = (TextView) itemView.findViewById(R.id.textDescription);
            textPrice = (TextView) itemView.findViewById(R.id.textPrice);
        }

    }

}
