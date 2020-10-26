package com.example.ezorder.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ezorder.Model.Table;
import com.example.ezorder.R;

import java.util.List;

public class TableFragmentAdapter extends BaseAdapter {
    Context context;
    List<Table> list;

    public TableFragmentAdapter(Context context, List<Table> list) {
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

    static class ViewHolder {
        ImageView imageView;
        TextView tableID;
        TextView tableNumber;
        TextView tableStatus;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.table_item, null);
            holder.imageView = convertView.findViewById(R.id.img_table);
            holder.tableID = convertView.findViewById(R.id.tableID);
            holder.tableNumber = convertView.findViewById(R.id.tableNumber);
            holder.tableStatus = convertView.findViewById(R.id.tableStatus);
        } else {
            return convertView;
        }
        holder.imageView.setImageResource(R.drawable.ic_dinner_table);
        holder.tableID.setText(list.get(position).getTableID());
        holder.tableNumber.setText(String.valueOf(list.get(position).getNumber()));
        if (list.get(position).getStatus() == 1) {
            holder.tableStatus.setText(R.string.table_full);
        } else {
            holder.tableStatus.setText(R.string.table_empty);
        }

        return convertView;
    }
}
