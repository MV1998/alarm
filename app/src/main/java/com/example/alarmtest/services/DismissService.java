package com.example.alarmtest.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.alarmtest.MyApplication;
import com.example.alarmtest.MyPlayer;

import static android.content.Context.ALARM_SERVICE;

public class DismissService extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        MyPlayer.getInstance().stopSound();
        startAlert();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAlert() {

        Intent intent = new Intent(MyApplication.getInstance(), AlarmService.class);
        intent.putExtra("data", "Some data here to put");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                MyApplication.getInstance(), 12345, intent, 0);
        AlarmManager alarmManager = (AlarmManager) MyApplication.getInstance().getSystemService(ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis()
                            + (10 * 1000), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + (10 * 1000), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + (10 * 1000), pendingIntent);
        }
        Toast.makeText(MyApplication.getInstance(), "Alarm set in 10 seconds", Toast.LENGTH_LONG).show();

    }
}
