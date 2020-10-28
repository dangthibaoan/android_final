package com.example.ezorder.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ezorder.Model.Food;
import com.example.ezorder.R;

import java.util.List;

public class Order_Sub_1_Adapter extends BaseAdapter {
    private final Context context;
    private final List<Food> list;

    public Order_Sub_1_Adapter(Context context, List<Food> list) {
        this.context = context;
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
        convertView = inflater.inflate(R.layout.choose_food, null);

        Food food = (Food) getItem(position);
        String s = String.format("%s\n%s / %s",food.getFoodName(),food.getFoodPrice(),food.getFoodUnit());
        TextView textView = convertView.findViewById(R.id.choose_food_title);
        textView.setText(s);

        byte[] arrs = Base64.decode(food.getFoodImage(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(arrs, 0, arrs.length);
        ImageView imageView = convertView.findViewById(R.id.choose_food_image);
        imageView.setImageBitmap(bitmap);

        return convertView;
    }
}
