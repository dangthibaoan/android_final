package com.example.ezorder.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ezorder.Model.Role;
import com.example.ezorder.R;

import java.util.List;

public class RoleFragmentAdapter extends BaseAdapter {
    private final Context context;
    private final List<Role> list;

    public RoleFragmentAdapter(Context context, List<Role> list) {
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
        TextView tRoleName;
        ImageView imgRole;

        public ViewHolder(View base) {
            tRoleName = base.findViewById(R.id.txtRoleName);
            imgRole = base.findViewById(R.id.imgUserRole);
        }
    }

    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.role_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tRoleName.setText(list.get(position).getRoleName());
        viewHolder.imgRole.setImageResource(R.drawable.baseline_admin_panel_settings_black_24dp);
        return view;
    }
}
