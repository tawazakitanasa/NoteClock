package com.example.noteclock;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    final String CHANEL_ID="NOTE CLOCK";

    @Override
    public void onReceive(Context context, Intent intent) {
        String time = null;
        NotificationManager notificationManager = null;
        if (intent.getAction().equals("BaoThuc")) {
            time = intent.getStringExtra("time");
            notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANEL_ID, "Chanel_1", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("Mieu ta thong báo");
                notificationManager.createNotificationChannel(channel);
            }
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANEL_ID)
                .setContentTitle("Thông báo " + time)
                .setContentText("Đã đến giờ " + time)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setColor(Color.RED)
                .setCategory(NotificationCompat.CATEGORY_ALARM);
        notificationManager.notify(getNotificationid(),builder.build());
    }
    private int getNotificationid(){
        int time=(int)new Date().getTime();
        return time;
    }
}
