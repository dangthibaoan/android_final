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

import com.example.ezorder.Adapter.TableFragmentAdapter;
import com.example.ezorder.DBHelper;
import com.example.ezorder.Model.Table;
import com.example.ezorder.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class TableFragment extends Fragment {
    DBHelper helper;
    ListView listView;
    FloatingActionButton floatingActionButton;
    TableFragmentAdapter adapter;
    List<Table> list;
    EditText edtTableName, getEdtTableNameDetail;
    SwitchMaterial swTableStatus, swTableStatusDetail;
    TextView txtTableID;
    int status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        helper = new DBHelper(getContext());
        floatingActionButton = view.findViewById(R.id.fb_add_table);
        listView = view.findViewById(R.id.lvTable);
        list = helper.getAllTable();
        adapter = new TableFragmentAdapter(getActivity(), list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Table table = (Table) parent.getItemAtPosition(position);
                final int tID = table.getTableID();
                int tNum = table.getNumber();
                status = table.getStatus();

                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View view2 = inflater.inflate(R.layout.dialog_table_detail, null);
                getEdtTableNameDetail = view2.findViewById(R.id.edt_table_detail_num);
                txtTableID = view2.findViewById(R.id.edt_table_detail_id);
                swTableStatusDetail = view2.findViewById(R.id.table_status_edit);
                txtTableID.setText(String.valueOf(tID));
                getEdtTableNameDetail.setText(String.valueOf(tNum));
                swTableStatusDetail.setChecked(status == 1);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.table_detail)
                        .setView(view2)
                        .setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Table table = new Table();
                                table.setTableID(tID);
                                table.setNumber(Integer.parseInt(getEdtTableNameDetail.getText().toString().trim()));
                                if (swTableStatusDetail.isChecked()) {
                                    table.setStatus(1);
                                } else {
                                    table.setStatus(0);
                                }
                                helper.updateTable(table);
                            }
                        })
                        .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Table table = new Table();
                                table.setTableID(tID);
                                helper.deleteTable(table);
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
                edtTableName = view1.findViewById(R.id.edtTableNumber);
                swTableStatus = view1.findViewById(R.id.swTableStatus);

                builder.setTitle(R.string.add_table)
                        .setView(view1)
                        .setPositiveButton(R.string.button_OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Table table = new Table();
                                table.setNumber(Integer.parseInt(edtTableName.getText().toString().trim()));
                                if (swTableStatus.isChecked()) {
                                    table.setStatus(1);
                                } else {
                                    table.setStatus(0);
                                }
                                helper.addTable(table);
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