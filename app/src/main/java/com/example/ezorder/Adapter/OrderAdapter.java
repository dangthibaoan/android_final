package com.example.ezorder.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ezorder.Model.Table;
import com.example.ezorder.R;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    Context context;
    int images;
    List<Table> list;

    public OrderAdapter(Context context, int images, List<Table> list) {
        this.context = context;
        this.images = images;
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
        convertView = inflater.inflate(R.layout.table_order_item, null);

        Table tbl = (Table) getItem(position);

        TextView textView = convertView.findViewById(R.id.textView);
        ImageView imageView = convertView.findViewById(R.id.imageView);

        String s = String.format("Bàn %d", tbl.getNumber());
        if (tbl.getStatus()==0){
            textView.setTextColor(Color.BLACK);
            s+=" - Trống";
        }
        else{
            textView.setTextColor(Color.RED);
            s+= " - Đã gọi món";
        }
        textView.setText(s);
        imageView.setImageResource(images);
        return convertView;
    }
}
