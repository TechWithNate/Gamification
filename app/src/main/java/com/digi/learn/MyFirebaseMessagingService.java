package com.digi.learn;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;


import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle the received FCM message (notification).

        // Extract the message body and any data from the notification.
        String messageBody = remoteMessage.getNotification().getBody();

        // Create an Intent to open the activity you want when the notification is tapped.
        Intent intent = new Intent(this, Quiz.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To ensure a new instance of the activity is not created.

        // Create a PendingIntent for the intent.
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        // Create the notification.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.baseline_notifications)
                .setContentTitle("Game Notification")
                .setContentText(messageBody)
                .setAutoCancel(true) // Close the notification when tapped.
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.game_icon));

        // Show the notification.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
