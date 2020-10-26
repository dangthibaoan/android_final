package com.example.ezorder.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ezorder.Adapter.UserFragmentAdapter;
import com.example.ezorder.Model.Role;
import com.example.ezorder.Model.User;
import com.example.ezorder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {
    private static final String TAG = "User FRG";

    ListView listView;
    List<User> listUser;
    List<Role> roleList;
    UserFragmentAdapter adapter;
    FloatingActionButton floatingActionButton;
    EditText eID, eName, ePhone, eAccountName, eAccountPass;
    EditText eNameDetail, ePhoneDetail, eAccountNameDetail, eAccountPassDetail;
    TextView eUserIDDetail;
    Spinner spinnerDetail;
    Spinner spinner;
    int usID;
    String roleNames;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        listView = view.findViewById(R.id.user_fragment_listview);
        floatingActionButton = view.findViewById(R.id.fb_add_user);
        db = FirebaseFirestore.getInstance();

        db.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        listUser = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                User user = snapshot.toObject(User.class);
                                listUser.add(user);
                            }
                            adapter = new UserFragmentAdapter(getActivity(), listUser);
                            listView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "onComplete: Load User data error " + task.getException());
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                User user = (User) parent.getItemAtPosition(position);
                usID = user.getUserID();
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View view2 = inflater.inflate(R.layout.dialog_user_detail, null);

                eUserIDDetail = view2.findViewById(R.id.eUserID2);
                eNameDetail = view2.findViewById(R.id.eUserName2);
                ePhoneDetail = view2.findViewById(R.id.eUserPhone2);
                eAccountNameDetail = view2.findViewById(R.id.eAccountName2);
                eAccountPassDetail = view2.findViewById(R.id.eAccountPass2);
                eUserIDDetail = view2.findViewById(R.id.eUserID2);
                spinnerDetail = view2.findViewById(R.id.spinnerRole2);

                db.collection("Role")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                roleList = new ArrayList<>();
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                        Role role = snapshot.toObject(Role.class);
                                        roleList.add(role);
                                    }
                                    @SuppressWarnings({"rawtypes", "unchecked"}) ArrayAdapter spinAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, roleList);
                                    spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerDetail.setAdapter(spinAdapter);
                                } else {
                                    Log.d(TAG, "onComplete: Load role spinner error" + task.getException());
                                }
                            }
                        });

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

                spinnerDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Role role = (Role) parent.getSelectedItem();
                        roleNames = role.getRoleName();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.userDetail)
                        .setView(view2)
                        .setCancelable(true)
                        .setPositiveButton(R.string.button_Update, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = Integer.parseInt(eUserIDDetail.getText().toString().trim());
                                String name = eNameDetail.getText().toString().trim();
                                String phone = ePhoneDetail.getText().toString().trim();
                                String acName = eAccountNameDetail.getText().toString().trim();
                                String acPass = eAccountPassDetail.getText().toString().trim();

                                db.collection("User")
                                        .document(String.valueOf(id))
                                        .update(
                                                "userName", name,
                                                "userRole", roleNames,
                                                "accountName", acName,
                                                "accountPass", acPass,
                                                "phone", phone
                                        )
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: Update User success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: Update User fail" + e);
                                            }
                                        });
                            }
                        })
                        .setNegativeButton(R.string.button_Delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("User")
                                        .document(String.valueOf(usID))
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: Delete User success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: Delete User error " + e);
                                            }
                                        });
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // TODO : Add User
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View view1 = inflater.inflate(R.layout.dialog_add_user, null);

                eID = view1.findViewById(R.id.edtUserID);
                eName = view1.findViewById(R.id.eUserName);
                ePhone = view1.findViewById(R.id.eUserPhone);
                eAccountName = view1.findViewById(R.id.eAccountName);
                eAccountPass = view1.findViewById(R.id.eAccountPass);
                spinner = view1.findViewById(R.id.spinnerRole);

                db.collection("Role")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                roleList = new ArrayList<>();
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                        Role role = snapshot.toObject(Role.class);
                                        roleList.add(role);
                                    }
                                    @SuppressWarnings({"rawtypes", "unchecked"}) ArrayAdapter spinAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, roleList);
                                    spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner.setAdapter(spinAdapter);
                                } else {
                                    Log.d(TAG, "onComplete: Load role spinner error" + task.getException());
                                }
                            }
                        });

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Role role = (Role) parent.getSelectedItem();
                        roleNames = role.getRoleName();
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
                                int ID = Integer.parseInt(eID.getText().toString().trim());
                                String name = eName.getText().toString().trim();
                                String phone = ePhone.getText().toString().trim();
                                String acName = eAccountName.getText().toString().trim();
                                String acPass = eAccountPass.getText().toString().trim();

                                User user = new User(ID, name, phone, acName, acPass, roleNames);
                                db.collection("User")
                                        .document(String.valueOf(ID))
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: Add User success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: Add User error " + e);
                                            }
                                        });
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