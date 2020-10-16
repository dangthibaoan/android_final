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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.ezorder.Adapter.UserFragmentAdapter;
import com.example.ezorder.DBHelper;
import com.example.ezorder.Model.Role;
import com.example.ezorder.Model.User;
import com.example.ezorder.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class UserFragment extends Fragment {
    DBHelper dbHelper;
    ListView listView;
    List<User> listUser;
    List<Role> roleList;
    FloatingActionButton floatingActionButton;
    EditText eName, ePhone, eAccountName, eAccountPass;
    EditText eNameDetail, ePhoneDetail, eAccountNameDetail, eAccountPassDetail;
    TextView eUserIDDetail;
    Spinner spinnerDetail;
    Spinner spinner;
    int roleID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DBHelper(getContext());
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        roleList = dbHelper.getAllRole();
        listUser = dbHelper.getAllUser();

        listView = view.findViewById(R.id.user_fragment_listview);
        floatingActionButton = view.findViewById(R.id.fb_add_user);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                User user = (User) parent.getItemAtPosition(position);
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View view2 = inflater.inflate(R.layout.dialog_user_detail, null);

                eUserIDDetail = view2.findViewById(R.id.eUserID2);
                eNameDetail = view2.findViewById(R.id.eUserName2);
                ePhoneDetail = view2.findViewById(R.id.eUserPhone2);
                eAccountNameDetail = view2.findViewById(R.id.eAccountName2);
                eAccountPassDetail = view2.findViewById(R.id.eAccountPass2);
                eUserIDDetail = view2.findViewById(R.id.eUserID2);
                spinnerDetail = view2.findViewById(R.id.spinnerRole2);

                int uID = user.getUserID();
                String uName = user.getUserName();
                String uPhone = user.getPhone();
                String uAccName = user.getAccountName();
                String uAccPass = user.getAccountPass();

                eUserIDDetail.setText(String.valueOf(uID));
                eNameDetail.setText(uName);
                ePhoneDetail.setText(uPhone);
                eAccountNameDetail.setText(uAccName);
                eAccountPassDetail.setText(uAccPass);
                spinnerDetail.setSelection(roleID);

                @SuppressWarnings({"rawtypes", "unchecked"}) ArrayAdapter spinAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, roleList);
                spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDetail.setAdapter(spinAdapter);

                spinnerDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Role role = (Role) parent.getSelectedItem();
                        roleID = role.getRoleID();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.userDetail)
                        .setView(view2)
                        .setCancelable(true)
                        .setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = Integer.parseInt(eUserIDDetail.getText().toString().trim());
                                String name = eName.getText().toString().trim();
                                String phone = ePhone.getText().toString().trim();
                                String acName = eAccountName.getText().toString().trim();
                                String acPass = eAccountPass.getText().toString().trim();
                                User user = new User(id, name, phone, acName, acPass, roleID);
                                dbHelper.updateUser(user);
                            }
                        })
                        .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                User user = new User((int) id);
                                dbHelper.deleteUser(user);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View view1 = inflater.inflate(R.layout.dialog_add_user, null);

                @SuppressWarnings({"rawtypes", "unchecked"}) ArrayAdapter spinAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, roleList);
                spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                eName = view1.findViewById(R.id.eUserName);
                ePhone = view1.findViewById(R.id.eUserPhone);
                eAccountName = view1.findViewById(R.id.eAccountName);
                eAccountPass = view1.findViewById(R.id.eAccountPass);
                spinner = view1.findViewById(R.id.spinnerRole);

                spinner.setAdapter(spinAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Role role = (Role) parent.getSelectedItem();
                        roleID = role.getRoleID();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.add_User)
                        .setView(view1)
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = eName.getText().toString().trim();
                                String phone = ePhone.getText().toString().trim();
                                String acName = eAccountName.getText().toString().trim();
                                String acPass = eAccountPass.getText().toString().trim();
                                User user = new User(name, phone, acName, acPass, roleID);
                                dbHelper.addUser(user);
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

        UserFragmentAdapter adapter = new UserFragmentAdapter(getContext(), listUser);
        listView.setAdapter(adapter);
        return view;
    }
}