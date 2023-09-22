package com.digi.learn;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.digi.learn.Fragments.StartChallengeFragment;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        // Create an explicit intent to open your target class
        Intent intent = new Intent(this, StartChallengeFragment.class);

        // You can include extra data in the intent if needed
        intent.putExtra("key", "value");

        // Create a PendingIntent to open the intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        // Handle the received FCM message (notification).
        // Extract the message body and any data from the notification.
        String soundUri = "android.resource://" + getPackageName() + "/" + R.raw.notification_sound1;

        String messageBody = remoteMessage.getNotification().getBody();
        String title = remoteMessage.getNotification().getTitle();
        String CHANNEL_ID = "DATA";
        CharSequence name;
        NotificationChannel notificationChannel;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Message Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
        }

        Context context;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setSmallIcon(R.drawable.baseline_notifications)
                    .setContentTitle("Game Notification")
                    .setContentText(messageBody)
                    .setAutoCancel(true) // Close the notification when tapped.
                    //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setSound(Uri.parse(soundUri)) // Set custom sound
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.game_icon));

            // Set the PendingIntent for the notification
            notification.setContentIntent(pendingIntent);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            NotificationManagerCompat.from(this).notify(1, notification.build());
            super.onMessageReceived(remoteMessage);





        }


    }
}
