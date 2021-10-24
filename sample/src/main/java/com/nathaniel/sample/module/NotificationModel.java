package com.nathaniel.sample.module;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.nathaniel.sample.R;


public class NotificationModel {
    private final int NOTIFICATION_ID = 0x101;
    private final Context mContext;
    private Notification notification;
    private NotificationManager notificationManager;
    private RemoteViews mRemoteViews;

    public NotificationModel(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotificationProgress() {
        NotificationCompat.Builder builderProgress = new NotificationCompat.Builder(mContext);
        builderProgress.setOngoing(true);
        builderProgress.setSmallIcon(R.mipmap.ic_launcher);
        mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.layout_notification);
        mRemoteViews.setProgressBar(R.id.pb, 100, 0, false);
        mRemoteViews.setImageViewResource(R.id.iv, R.mipmap.ic_launcher);
        builderProgress.setContent(mRemoteViews);
        notification = builderProgress.build();
        notification.flags = Notification.FLAG_NO_CLEAR;
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public void updateNotification(int total) {
        mRemoteViews.setTextViewText(R.id.tv_progress, total + "%");
        mRemoteViews.setProgressBar(R.id.pb, 100, total, false);
        notificationManager.notify(NOTIFICATION_ID, notification);
        if (total == 100) {
            notificationManager.cancel(NOTIFICATION_ID);
        }
    }
}
