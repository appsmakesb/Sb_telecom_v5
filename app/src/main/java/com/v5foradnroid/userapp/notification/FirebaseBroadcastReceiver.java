package com.v5foradnroid.userapp.notification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.v5foradnroid.userapp.R;
import com.v5foradnroid.userapp.Start;

import java.util.Date;
import org.antlr.runtime.debug.DebugEventListener;
import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseBroadcastReceiver extends WakefulBroadcastReceiver {
    JSONObject data;
    String message;
    String title;

    public void onReceive(Context context, Intent intent) {
        try {
            JSONObject jSONObject = new JSONObject(intent.getExtras().getString("message"));
            data = jSONObject;
            message = jSONObject.getString("title");
            title = data.getString("body");
        } catch (NullPointerException | JSONException e) {
            e.printStackTrace();
        }
        Log.d("BroadcastReceiver::", "BroadcastReceiver");
        sendNotification(title, message, "String page", context);
    }

    private void sendNotification(String str, String str2, String str3, Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, DebugEventListener.PROTOCOL_VERSION);
        Intent intent = new Intent(context, Start.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(603979776);
        PendingIntent pendingIntent = null;
        if (Build.VERSION.SDK_INT >= 31) {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        }
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(str);
        bigTextStyle.setBigContentTitle(str2);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.notif);
        builder.setContentTitle(str);
        builder.setContentText(str2);
        builder.setPriority(2);
        builder.setAutoCancel(true);
        builder.setStyle(bigTextStyle);
        @SuppressLint("WrongConstant") NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        int time = (int) ((new Date().getTime() / 1000) % 2147483647L);
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel(DebugEventListener.PROTOCOL_VERSION, DebugEventListener.PROTOCOL_VERSION, NotificationManager.IMPORTANCE_DEFAULT));
        }
        notificationManager.notify(time, builder.build());
    }
}
