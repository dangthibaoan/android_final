package com.example.ezorder.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ezorder.R;

import java.util.List;

public class ChefAdapter extends BaseAdapter {

    private Context context;
    private int img;
    private List<String> list;

    public ChefAdapter(Context context, int img, List<String> list) {
        this.context = context;
        this.img = img;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.chef_item, null);
        TextView textView = convertView.findViewById(R.id.chef_food_name);
        textView.setText(list.get(position));
        ImageView imageView = convertView.findViewById(R.id.chef_food_img);
        imageView.setImageResource(img);
        return convertView;
    }
}
