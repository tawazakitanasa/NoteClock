package com.example.noteclock.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.List;


public class FragmentHome extends Fragment {
    private RecycleViewAdapter adapter;
    private TextView tvCount;
    private RecyclerView recyclerView;
    private SQLite db;
    private Calendar myCalendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcv_list);
        tvCount = view.findViewById(R.id.tvcount);
        adapter = new RecycleViewAdapter();
        db = new SQLite(getContext());
        List<Note> list = db.getAll();
        adapter.setNotes(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener((RecycleViewAdapter.ItemListener) this);
    }

   
}