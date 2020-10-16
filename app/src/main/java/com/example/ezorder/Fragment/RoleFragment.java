package com.example.ezorder.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.ezorder.Adapter.RoleFragmentAdapter;
import com.example.ezorder.DBHelper;
import com.example.ezorder.Model.Role;
import com.example.ezorder.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class RoleFragment extends Fragment {
    FloatingActionButton buttonAdd;
    DBHelper helper;
    List<Role> list;
    ListView listView;
    RoleFragmentAdapter adapter;
    EditText edtNameRole, edtRoleNameDetail;
    TextView txtRoleIDDetail;
    int IDs;
    String roleNames;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_role, container, false);
        buttonAdd = view.findViewById(R.id.fb_add_role);
        listView = view.findViewById(R.id.lv_role);
        helper = new DBHelper(getContext());
        list = helper.getAllRole();
        adapter = new RoleFragmentAdapter(getActivity(), list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Role role = (Role) parent.getItemAtPosition(position);
                IDs = role.getRoleID();
                roleNames = role.getRoleName();

                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View view1 = inflater.inflate(R.layout.dialog_role_detail, null);
                edtRoleNameDetail = view1.findViewById(R.id.role_name_detail);
                txtRoleIDDetail = view1.findViewById(R.id.role_ID_detail);
                txtRoleIDDetail.setText(String.valueOf(IDs));
                edtRoleNameDetail.setText(roleNames);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.role_detail)
                        .setView(view1)
                        .setCancelable(true)
                        .setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newName = edtRoleNameDetail.getText().toString().trim();
                                Role role = new Role(IDs, newName);
                                helper.updateRole(role);
                            }
                        })
                        .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Role role = new Role(IDs);
                                helper.deleteRole(role);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) Objects.requireNonNull(getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View view2 = inflater.inflate(R.layout.dialog_add_role, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                edtNameRole = view2.findViewById(R.id.edtNameRole);
                helper = new DBHelper(getActivity());

                builder.setTitle(R.string.add_role)
                        .setView(view2)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String nameRole = edtNameRole.getText().toString().trim();
                                Role role = new Role(nameRole);
                                helper.addRole(role);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }
}