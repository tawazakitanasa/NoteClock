package com.example.noteclock.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FragmentToDay extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecycleViewAdapter adapter;
    private TextView tvCount;
    private RecyclerView recyclerView;
    private SQLite db;
    private Calendar myCalendar = Calendar.getInstance();
    public FragmentToDay() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_to_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.rcv_list);
        tvCount=view.findViewById(R.id.tvcount);
        adapter=new RecycleViewAdapter();
        db=new SQLite(getContext());
        Date today=new Date();
        SimpleDateFormat td=new SimpleDateFormat("dd/MM/yyyy");
        List<Note> list=db.getBydate(td.format(today));
        adapter.setNotes(list);
        tvCount.setText("Ghi chú hôm nay "+Count(list));
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

    }

    private int Count(List<Note> list){
        int count=0;
        count=list.size();
        return count;
    }

    @Override
    public void onItemClick(View view, int position) {
        ImageView img_edit_note, img_delete_note;
        img_edit_note = view.findViewById(R.id.img_edit_note);
        img_delete_note = view.findViewById(R.id.img_delete_note);

        img_edit_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = adapter.getNote(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn sửa ghi chú?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Note note = adapter.getNote(position);
                        final Dialog dialogupdate = new Dialog(getContext());
                        dialogupdate.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialogupdate.setContentView(R.layout.update_dialog);
                        Window window = dialogupdate.getWindow();
                        if (window != null) {
                            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            WindowManager.LayoutParams windowAttributes = window.getAttributes();
                            windowAttributes.gravity = Gravity.CENTER;
                            window.setAttributes(windowAttributes);
                            dialogupdate.setCancelable(Gravity.CENTER == Gravity.CENTER);
                            EditText txtDate = dialogupdate.findViewById(R.id.txt_date);
                            EditText txtTime = dialogupdate.findViewById(R.id.txt_time);
                            TextInputEditText txtTitle = dialogupdate.findViewById(R.id.txt_title);
                            TextInputEditText txtContent = dialogupdate.findViewById(R.id.txt_content);
                            Button btn_update = dialogupdate.findViewById(R.id.btn_update);
                            selectDate(txtDate);
                            txtTime.setOnClickListener(v -> selectTime(txtTime));
                            txtTitle.setText(note.getTitle());
                            txtContent.setText(note.getContent());
                            txtDate.setText(note.getDate());
                            txtTime.setText(note.getTime());

                            btn_update.setOnClickListener(view -> {
                                String selectedDate = txtDate.getText().toString();
                                String selectedTime = txtTime.getText().toString();
                                String title = txtTitle.getText().toString();
                                String content = txtContent.getText().toString();
                                Note noteupdate=new Note(note.getId(),title,content,selectedDate,selectedTime);
                                db.Update(noteupdate);
                                Toast.makeText(getContext(), "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                                dialogupdate.dismiss();
                            });

                            dialogupdate.show();
                        }
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        img_delete_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xóa ghi chú?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Note note = adapter.getNote(position);
                        db.Delete(note.getId());
                        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Date today=new Date();
        SimpleDateFormat td=new SimpleDateFormat("dd/MM/yyyy");
        List<Note> list=db.getBydate(td.format(today));
        adapter.setNotes(list);
        tvCount.setText("Ghi chú hôm nay "+Count(list));
    }
    private void selectDate(final EditText txtDate) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                txtDate.setText(updateDate());
            }
        };

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private String updateDate() {
        String format = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat.format(myCalendar.getTime());
    }

    private void selectTime(final EditText txtTime) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                currentTime.set(Calendar.HOUR_OF_DAY, h);
                currentTime.set(Calendar.MINUTE, m);
                String timeFormat = "HH:mm";
                SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat, Locale.US);
                txtTime.setText(dateFormat.format(currentTime.getTime()));
            }
        }, hour, minute, true);
        timePickerDialog.setTitle("Chọn giờ");
        timePickerDialog.show();
    }
}