package com.example.noteclock.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteclock.Adapter.RecycleViewAdapter;
import com.example.noteclock.R;
import com.example.noteclock.SQL.SQLite;
import com.example.noteclock.model.Note;

import java.util.List;


public class FragmentSearch extends Fragment {
    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private EditText txtsearch;
    private Button btnTimKiem;
    private SQLite db;
    public FragmentSearch() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcv_list_search);
        txtsearch = view.findViewById(R.id.search);
        btnTimKiem = view.findViewById(R.id.btnTimKiem);
        db = new SQLite(getContext());
        adapter = new RecycleViewAdapter();

        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = txtsearch.getText().toString();

                if (!search.isEmpty()) {
                    List<Note> list = db.getBySearch(search);

                    if (list.isEmpty()) {
                        Toast.makeText(getContext(), "Không tìm thấy thông tin.", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.setNotes(list);
                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getContext(), "vui lòng nhập thông tin tìm kiếm.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Note> noteList=db.getAll();
        adapter.setNotes(noteList);
        adapter.notifyDataSetChanged();
    }
}