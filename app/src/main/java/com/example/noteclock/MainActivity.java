package com.example.noteclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.noteclock.Adapter.ViewpageAdapter;
import com.example.noteclock.SQL.SQLite;
import com.example.noteclock.model.Note;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 mViewpager2;
    private BottomNavigationView mBottomNavigationView;
    private FloatingActionButton fab;
    private Calendar myCalendar = Calendar.getInstance();
    private SQLite db;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private int gio,phut,ngay,thang,nam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mViewpager2.setAdapter(new ViewpageAdapter(this));

        mViewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.mHome).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.mToday).setChecked(true);
                        break;
                }
            }
        });

        mBottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.mHome) {
                mViewpager2.setCurrentItem(0);
            } else if (item.getItemId() == R.id.mToday) {
                mViewpager2.setCurrentItem(1);
            } else {
                mViewpager2.setCurrentItem(2);
            }
            return true;
        });

        fab.setOnClickListener(view -> openDialog(Gravity.CENTER));
        db = new SQLite(this);
        List<Note> list = db.getAll();
        for (Note note : list) {
            String time = note.getTime();
            String[] timeParts = time.split(":");
            int gio = 0;
            int phut = 0;
            if (timeParts.length >= 2 && !timeParts[0].isEmpty() && !timeParts[1].isEmpty()) {
                gio = Integer.parseInt(timeParts[0]);
                phut = Integer.parseInt(timeParts[1]);
            }
            String sgio = String.valueOf(gio);
            String sphut = String.valueOf(phut);
            if (gio < 10) {
                sgio = "0" + gio;
            }
            if (phut < 10) {
                sphut = "0" + phut;
            }
            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
            intent.setAction("MyAction");
            intent.putExtra("time", sgio + ":" + sphut);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), pendingIntent);
        }
}

    private void initViews() {
        mBottomNavigationView = findViewById(R.id.bottom_nav);
        mViewpager2 = findViewById(R.id.view_page_2);
        fab = findViewById(R.id.fab_add);
    }
    private void openDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_dialog);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            windowAttributes.gravity = gravity;
            window.setAttributes(windowAttributes);
            dialog.setCancelable(Gravity.CENTER == gravity);

            EditText txtDate = dialog.findViewById(R.id.txt_date);
            EditText txtTime = dialog.findViewById(R.id.txt_time);
            TextInputEditText txtTitle = dialog.findViewById(R.id.txt_title);
            TextInputEditText txtContent = dialog.findViewById(R.id.txt_content);
            Button btnAdd = dialog.findViewById(R.id.btn_add);

            selectDate(txtDate);
            txtTime.setOnClickListener(v -> selectTime(txtTime));

            btnAdd.setOnClickListener(view -> {
                String selectedDate = txtDate.getText().toString();
                String selectedTime = txtTime.getText().toString();
                String title = txtTitle.getText().toString();
                String content = txtContent.getText().toString();
                db=new SQLite(this);
                db.Add(new Note(title,content,selectedDate,selectedTime));
                //tach chuoi ngay
                String[] dates=selectedDate.split("/");
                if(dates.length>0){
                    String day=dates[0];
                    String month=dates[1];
                    String year=dates[2];
                  /*  Log.e("Day",day);
                    Log.e("Moth",month);
                    Log.e("Year",year);*/
                }


                //tach chuoi gio va ep ve int
                String[] times = selectedTime.split(":");
                if (times.length >0) {
                    String hour = times[0];
                    String minute = times[1];
                    gio=Integer.parseInt(hour);
                    phut=Integer.parseInt(minute);
                 /*   Log.e("Giờ", Integer.toString(gio));
                    Log.e("Phút", Integer.toString(phut));*/
                } else {
                    Log.e("Lỗi", "Chuỗi không hợp lệ");
                }

                Toast.makeText(MainActivity.this, "Tạo mới ghi chú thành công", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            });

            dialog.show();
        }
    }
//Chọn ngày
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
                new DatePickerDialog(MainActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
//Chọn ngày
    private String updateDate() {
        String format = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat.format(myCalendar.getTime());
    }
//Chọn giờ
    private void selectTime(final EditText txtTime) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
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