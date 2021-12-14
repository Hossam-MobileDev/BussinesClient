package com.hashtagco.bussinesclient.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.hashtagco.bussinesclient.BannerActivity;
import com.hashtagco.bussinesclient.Common.Common;
import com.hashtagco.bussinesclient.Helper.NotificationHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hashtagco.bussinesclient.R;

import java.util.Random;



public class MyFirebaseMessagingService extends FirebaseMessagingService
{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData()!=null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                sendNotificationAPI26(remoteMessage);
            else
                sendNotification(remoteMessage);

        }

    }

    private void sendNotificationAPI26(RemoteMessage remoteMessage)
    {


        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        //Here will fix to click to Notification -> go to Order list
        PendingIntent pendingIntent;
        NotificationHelper helper;
        Notification.Builder builder;


        if (Common.currentUser!=null)
        {
            Intent intent=new Intent(this, BannerActivity.class);
            intent.putExtra(Common.PHONE_TEXT, Common.currentUser.getPhone());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
            Uri uriDefaultSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            helper=new NotificationHelper(this);

            MediaPlayer mp= MediaPlayer.create(MyFirebaseMessagingService.this, R.raw.sound_effect);
            mp.start();


            builder=helper.getFoodSpottingChannelNotification(title,body,pendingIntent,uriDefaultSound);

            //Gen Random Id for notification to show all Notification
            helper.getManager().notify(new Random().nextInt(),builder.build());

        }else //Fix Crash if Notification Send from News System (Common.currentUser == null)
        {
            Uri uriDefaultSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            MediaPlayer mp= MediaPlayer.create(MyFirebaseMessagingService.this, R.raw.sound_effect);
            mp.start();

            helper=new NotificationHelper(this);

            builder=helper.getFoodSpottingChannelNotification(title,body,uriDefaultSound);

            //Gen Random Id for notification to show all Notification
            helper.getManager().notify(new Random().nextInt(),builder.build());
        }
    }

    private void sendNotification(RemoteMessage remoteMessage)
    {

        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        Intent intent=new Intent(this, BannerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri uriDefaultSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        MediaPlayer mp= MediaPlayer.create(MyFirebaseMessagingService.this, R.raw.sound_effect);
        mp.start();

        // Uri uriDefaultSound= RingtoneManager.getDefaultUri(R.raw.notificationsoundbasuony);//Sound
      /*  Bitmap img = BitmapFactory.decodeResource(MyFirebaseMessagingService.this.getResources(),
                R.drawable.notif);*/



        long[] v = {500,1000};//Time Vibration

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setContentIntent(pendingIntent)
              //  .setSmallIcon(R.drawable.notif)
                 .setSound(uriDefaultSound)
              //  .setLargeIcon(img)
                .setAutoCancel(true)
                .setColor(Color.BLUE)
                .setVibrate(v)
                .build();



        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify( new Random().nextInt(), notification);


    }




}
