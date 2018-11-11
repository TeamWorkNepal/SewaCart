package sewacart.com.sewacart.finalpackage.rest.firebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.controller.SharedPreferenceController;

public class FirebaseMsgService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseIDService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        SharedPreferenceController.saveUserToken(getApplicationContext(), s);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "onMessageReceived");
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        String clickAction = remoteMessage.getNotification().getClickAction();

        Intent intent = new Intent(clickAction);
        String orderId = remoteMessage.getData().get("order_id");

        intent.putExtra("orderId", orderId);
        PendingIntent resultPending = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel mChannel = null;
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            mChannel = new NotificationChannel("sewacart", "Sewacart  Channel ", NotificationManager.IMPORTANCE_MAX);
            mNotificationManager.createNotificationChannel(mChannel);

            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            mChannel.enableVibration(true);
            mChannel.setShowBadge(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            Notification.Builder mBuilder = new Notification.Builder(this, "sushichet")
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .setAutoCancel(true);

            mBuilder.setChannelId(mChannel.getId());
            int notificatonId = (int) System.currentTimeMillis();
            mBuilder.setContentIntent(resultPending);
            mNotificationManager.notify(notificatonId, mBuilder.build());
        } else {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "sushichet")
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setColor(Color.rgb(30, 136, 229))
                    .setSound(sound)
                    .setAutoCancel(true)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setVibrate(new long[]{Notification.DEFAULT_VIBRATE})
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);


            mBuilder.setContentIntent(resultPending);
            int notificatonId = (int) System.currentTimeMillis();
            mNotificationManager.notify(notificatonId, mBuilder.build());
        }
    }
}
