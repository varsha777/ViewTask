package com.example.varshadhoni.viewing;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

/**
 * Created by VarshaDhoni on 10/13/2017.
 */

public class CustomAdapter extends PagerAdapter {

    List<BooksAdapter> data2 = Collections.emptyList();
    private LayoutInflater inflater;
    private Context ctx;

    public CustomAdapter(Context ctx, List<BooksAdapter> data2) {
        this.ctx = ctx;
        this.data2 = data2;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return data2.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        BooksAdapter current = data2.get(position);
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.swipe, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        Glide.with(ctx).load(current.booksImages)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
        container.addView(v);
        return v;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.invalidate();

    }
}
