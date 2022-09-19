package com.merauke.wisatamerauke;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
     int NOTIFICATION_ID = 42;
     String NOTIFICATION_ID1;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String notifikasi_title = remoteMessage.getNotification().getTitle();
        String notifikasi_body = remoteMessage.getNotification().getBody();
        Uri notifikasi_image = remoteMessage.getNotification().getImageUrl();
        String click_action = remoteMessage.getNotification().getClickAction();
        String tag = remoteMessage.getNotification().getTag();
        String imageUrl = remoteMessage.getData().get("image-url");
        NOTIFICATION_ID1 = remoteMessage.getNotification().getChannelId();
//        String from_sender_id = remoteMessage.getData().get("UserIDs").toString();
//        String buku_id = remoteMessage.getData().get("PostIDs").toString();

//        if (tag.equals("PAKAN")){
//            NOTIFICATION_ID = 1;
//        } else {
//            NOTIFICATION_ID = 2;
//        }


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String Notification_Channel_Id = "com.merauke.wisatamerauke";


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(Notification_Channel_Id,"Notification",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("Wisata Merauke");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder notificationBuilder =  new NotificationCompat.Builder(this,Notification_Channel_Id);
        notificationBuilder
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notifikasi_title)
                .setContentText(notifikasi_body)
                .setContentInfo("info");

        if (remoteMessage.getNotification().getImageUrl() != null) {
            Bitmap bitmap = getBitmapfromUrl(notifikasi_image.toString());
            notificationBuilder.setStyle(
                    new NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap)
                            .bigLargeIcon(null)
            ).setLargeIcon(bitmap);
        }

        Intent resultIntent = new Intent(click_action);
//        resultIntent.putExtra("UserIDs", from_sender_id);
//        resultIntent.putExtra("PostIDs", buku_id);
//        resultIntent.putExtra("PostKey", buku_id);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT );
        notificationBuilder.setContentIntent(resultPendingIntent);
        notificationBuilder.setAutoCancel(true);

        // notificationManager.notify(new Random().nextInt(),notificationBuilder.build());
        notificationManager.notify(NOTIFICATION_ID,notificationBuilder.build());
        //  manager.notify(NOTIFICATION_ID, note);




    }
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            Log.e("awesome", "Error in getting notification image: " + e.getLocalizedMessage());
            return null;
        }
    }
}

