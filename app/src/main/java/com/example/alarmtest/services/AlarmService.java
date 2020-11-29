package com.example.alarmtest.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.alarmtest.AlertDetails;
import com.example.alarmtest.MyApplication;
import com.example.alarmtest.MyPlayer;
import com.example.alarmtest.R;


public class AlarmService extends BroadcastReceiver {

    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {

        if(!intent.getExtras().getString("data").isEmpty()){
            Toast.makeText(context, ""+intent.getExtras().getString("data"), Toast.LENGTH_SHORT).show();
            Log.d("TAG", "onReceive: " + intent.getExtras().getString("data"));
            createNotificationChannel();

            MyPlayer.getInstance().playSound();
        }
    }

    private void createNotificationChannel() {

        String NOTIFICATION_CHANNEL_ID = "Channel";

        Intent intent = new Intent(MyApplication.getInstance(), AlertDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getInstance(), 0, intent, 0);

        Uri sound = Uri. parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + MyApplication.getInstance().getPackageName() + "/raw/water.mp3") ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MyApplication.getInstance(), NOTIFICATION_CHANNEL_ID )
                .setSmallIcon(R.drawable. ic_launcher_foreground )
                .setContentTitle( "Test" )
                .setSound(sound)
                .setContentIntent(pendingIntent)
                .setDeleteIntent(createOnDismissNotification())
                .setContentText( "Hello! This is my first push notification");
        NotificationManager mNotificationManager = (NotificationManager) MyApplication.getInstance().getSystemService(Context. NOTIFICATION_SERVICE );
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            notificationChannel.enableVibration( true ) ;
            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;
            notificationChannel.setSound(sound , audioAttributes) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                mBuilder.build()) ;
    }

    public PendingIntent createOnDismissNotification(){
        Intent intent = new Intent(MyApplication.getInstance(), DismissService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getInstance(), 0, intent, 0);
        return pendingIntent;
    }
}
