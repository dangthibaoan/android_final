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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ezorder.Adapter.RoleFragmentAdapter;
import com.example.ezorder.Model.Role;
import com.example.ezorder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoleFragment extends Fragment {
    private static final String TAG = "ROLE FRG";
    FloatingActionButton buttonAdd;
    ListView listView;
    RoleFragmentAdapter adapter;
    EditText edtNameRole, edtRoleID, edtRoleNameDetail;
    TextView txtRoleIDDetail;
    int IDs;
    String roleNames;
    FirebaseFirestore db;
    DocumentReference reference;
    Thread thread1;
    List<Role> list;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_role, container, false);
        buttonAdd = view.findViewById(R.id.fb_add_role);
        listView = view.findViewById(R.id.lv_role);
        db = FirebaseFirestore.getInstance();

        //  Thread: Add role
        thread1 = new Thread() {
            @Override
            public void run() {
                int roleID = Integer.parseInt(edtRoleID.getText().toString().trim());
                String nameRole = edtNameRole.getText().toString().trim();
                Role role = new Role(roleID, nameRole);
                db.collection("Role")
                        .document(String.valueOf(roleID))
                        .set(role)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: Add role success");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.getMessage());
                            }
                        });
            }
        };

        db.collection("Role")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                Role role = snapshot.toObject(Role.class);
                                list.add(role);
                            }
                            adapter = new RoleFragmentAdapter(getActivity(), list);
                            listView.setAdapter(adapter);
                        } else {
                            Log.d("ROLE", "onComplete: " + task.getException());
                        }
                    }
                });

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
                        .setPositiveButton(R.string.button_Update, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newName = edtRoleNameDetail.getText().toString().trim();
                                reference = db.collection("Role").document(String.valueOf(IDs));
                                reference.update("roleName", newName)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: Update role success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: Update role error" + e);
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("Role")
                                        .document(String.valueOf(IDs))
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: Delete role success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: Delete role fail " + e);
                                            }
                                        });
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
                edtRoleID = view2.findViewById(R.id.edtRoleID);

                builder.setTitle(R.string.add_role)
                        .setView(view2)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @SuppressLint("ShowToast")
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                thread1.start();
                                Toast.makeText(getActivity(), "Thêm role thành công", Toast.LENGTH_SHORT);
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

    @Override
    public void onDestroyView() {
        thread1.interrupt();
        super.onDestroyView();
    }
}