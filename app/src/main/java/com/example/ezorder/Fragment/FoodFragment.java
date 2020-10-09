package com.example.ezorder.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ezorder.Adapter.ChefAdapter;
import com.example.ezorder.Adapter.FoodFragmentAdapter;
import com.example.ezorder.R;

import java.util.ArrayList;
import java.util.List;


public class FoodFragment extends Fragment {
    ListView listView;
    List<String> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_food, container, false);
        listView = view.findViewById(R.id.food_fragment_listview);
        list = new ArrayList<>();
        list.add("Apple");
        list.add("Orange");
        list.add("Banana");
        list.add("Melon");
        list.add("Cherry");

        FoodFragmentAdapter adapter = new FoodFragmentAdapter(getContext(), R.drawable.ic_dinner_table
                , list);
        listView.setAdapter(adapter);
        return view;
    }
}