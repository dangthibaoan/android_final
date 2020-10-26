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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ezorder.Adapter.TableFragmentAdapter;
import com.example.ezorder.Model.Table;
import com.example.ezorder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class TableFragment extends Fragment {
    private static final String TAG = "Table FRG";

    ListView listView;
    FloatingActionButton floatingActionButton;
    TableFragmentAdapter adapter;
    EditText edtTableID, edtTableName, getEdtTableNameDetail;
    SwitchMaterial swTableStatus, swTableStatusDetail;
    TextView txtTableID;
    int status;
    String tbID;
    FirebaseFirestore db;
    List<Table> tableList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        floatingActionButton = view.findViewById(R.id.fb_add_table);
        listView = view.findViewById(R.id.lvTable);
        db = FirebaseFirestore.getInstance();

        db.collection("Table")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        tableList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                Table table = snapshot.toObject(Table.class);
                                tableList.add(table);
                            }
                            adapter = new TableFragmentAdapter(getActivity(), tableList);
                            listView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "onComplete: Load data error " + task.getException());
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Table table = (Table) parent.getItemAtPosition(position);
                tbID = table.getTableID();
                int tNum = table.getNumber();
                status = table.getStatus();

                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View view2 = inflater.inflate(R.layout.dialog_table_detail, null);
                getEdtTableNameDetail = view2.findViewById(R.id.edt_table_detail_num);
                txtTableID = view2.findViewById(R.id.edt_table_detail_id);
                swTableStatusDetail = view2.findViewById(R.id.table_status_edit);
                txtTableID.setText(tbID);
                getEdtTableNameDetail.setText(String.valueOf(tNum));
                swTableStatusDetail.setChecked(status == 1);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.table_detail)
                        .setView(view2)
                        .setPositiveButton(R.string.button_Update, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Table table = new Table();
                                table.setTableID(tbID);
                                table.setNumber(Integer.parseInt(getEdtTableNameDetail.getText().toString().trim()));
                                if (swTableStatusDetail.isChecked()) {
                                    table.setStatus(1);
                                } else {
                                    table.setStatus(0);
                                }
                                db.collection("Table")
                                        .document(table.getTableID())
                                        .update("number", table.getNumber(), "status", table.getStatus())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: Update table success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: Update table error " + e);
                                            }
                                        });
                            }
                        })
                        .setNegativeButton(R.string.button_Delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Table table = new Table();
                                table.setTableID(tbID);
                                db.collection("Table")
                                        .document(table.getTableID())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: Delete table success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: Delete table error " + e);
                                            }
                                        });
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
                @SuppressLint("InflateParams") View view1 = inflater.inflate(R.layout.dialog_add_table, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                edtTableID = view1.findViewById(R.id.edtTableID);
                edtTableName = view1.findViewById(R.id.edtTableNumber);
                swTableStatus = view1.findViewById(R.id.swTableStatus);

                builder.setTitle(R.string.add_table)
                        .setView(view1)
                        .setPositiveButton(R.string.button_OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Table table = new Table();
                                table.setTableID(edtTableID.getText().toString().trim());
                                table.setNumber(Integer.parseInt(edtTableName.getText().toString().trim()));
                                if (swTableStatus.isChecked()) {
                                    table.setStatus(1);
                                } else {
                                    table.setStatus(0);
                                }

                                db.collection("Table")
                                        .document(String.valueOf(table.getTableID()))
                                        .set(table)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: Add table success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: Add table error " + e);
                                            }
                                        });
                            }
                        })
                        .setNegativeButton(R.string.button_Cancel, new DialogInterface.OnClickListener() {
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