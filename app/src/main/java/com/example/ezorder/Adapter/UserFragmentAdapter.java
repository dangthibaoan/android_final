package com.example.ezorder.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ezorder.DBHelper;
import com.example.ezorder.Model.User;
import com.example.ezorder.R;

import java.util.List;

public class UserFragmentAdapter extends BaseAdapter {
    Context context;
    List<User> userList;
    DBHelper helper;

    public UserFragmentAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.user_item, null);

        User user = userList.get(position);
        helper = new DBHelper(context);

        TextView txtUserID = convertView.findViewById(R.id.userID);
        txtUserID.setText(String.valueOf(user.getUserID()));
        TextView txtUserFullName = convertView.findViewById(R.id.userFullName);
        txtUserFullName.setText(user.getUserName());
        TextView txtAccountName = convertView.findViewById(R.id.accountName);
        txtAccountName.setText(user.getAccountName());
        TextView txtAccountPass = convertView.findViewById(R.id.accountPassword);
        txtAccountPass.setText(user.getAccountPass());
        TextView txtUserPhone = convertView.findViewById(R.id.userPhone);
        txtUserPhone.setText(user.getPhone());
        TextView txtUserRole = convertView.findViewById(R.id.userRole);
        txtUserRole.setText(helper.getNamwRole(String.valueOf(user.getUser_role())));

        return convertView;
    }
}
