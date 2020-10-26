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

public class FoodFragmentAdapter extends BaseAdapter {
    Context context;
    List<Food> list;

    public FoodFragmentAdapter(Context context, List<Food> list) {
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
        return position;
    }

    static class ViewHolder {
        TextView txtFoodID, txtFoodName, txtFoodPrice, txtFoodUnit, txtFoodStatus;
        ImageView imgFood;

        public ViewHolder(View base) {
            txtFoodID = base.findViewById(R.id.txtFoodID);
            txtFoodName = base.findViewById(R.id.txtFoodName);
            txtFoodPrice = base.findViewById(R.id.txtFoodPrice);
            txtFoodUnit = base.findViewById(R.id.txtFoodUnit);
            txtFoodStatus = base.findViewById(R.id.txtFoodStatus);
            imgFood = base.findViewById(R.id.imgFood);
        }
    }

    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.food_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Food food = list.get(position);
        viewHolder.txtFoodID.setText(String.valueOf(food.getFoodID()));
        viewHolder.txtFoodName.setText(food.getFoodName());
        viewHolder.txtFoodPrice.setText(String.valueOf(food.getFoodPrice()));
        viewHolder.txtFoodUnit.setText(food.getFoodUnit());
        if (food.getFoodStatus() == 1) {
            viewHolder.txtFoodStatus.setText(R.string.food_ready);
        } else viewHolder.txtFoodStatus.setText(R.string.food_empty);

        byte[] arrs = Base64.decode(food.getFoodImage(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(arrs, 0, arrs.length);
        viewHolder.imgFood.setImageBitmap(bitmap);
        return view;
    }
}
