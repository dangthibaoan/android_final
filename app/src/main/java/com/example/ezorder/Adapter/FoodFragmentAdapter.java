package com.example.ezorder.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ezorder.R;

import java.util.List;

public class FoodFragmentAdapter extends BaseAdapter {
    private Context context;
    private int img;
    private List<String> list;

    public FoodFragmentAdapter(Context context, int img, List<String> list) {
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

    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.food_item, null);

        ImageView imageView = convertView.findViewById(R.id.imgFood);
        imageView.setImageResource(img);
        TextView textView = convertView.findViewById(R.id.foodName);
        textView.setText(list.get(position));

        return convertView;
    }
}
