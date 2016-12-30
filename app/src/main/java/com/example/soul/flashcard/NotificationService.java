package com.example.soul.flashcard;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class NotificationService extends Service {
    private Context context;

    public static int ID_NOTIF = 4372;

    public NotificationService(Context c) {
        context = c;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void sendNotification(){
        Intent intent = new Intent(context, Notification.class);
        //intent.setAction()

        TaskStackBuilder builder = TaskStackBuilder.create(context);
        builder.addParentStack(Notification.class);
        builder.addNextIntent(intent);

        PendingIntent pendingIntent = builder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.notifMessage))
                .setSmallIcon(R.drawable.ic_assignment_black_24dp)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notifMessage)))
                .setColor(0x660066)
                .setLights(0x660066,60,20)
                .setVibrate(new long[] {0,300,100,300})
                .setAutoCancel(true)
                .build();

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(ID_NOTIF, notification);
    }

}
