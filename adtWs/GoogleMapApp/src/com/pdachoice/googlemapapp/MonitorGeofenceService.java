package com.pdachoice.googlemapapp;

import java.util.Arrays;
import java.util.List;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.view.SoundEffectConstants;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

public class MonitorGeofenceService extends IntentService {

  public MonitorGeofenceService() {
    this("MonitorGeofenceService");
  }

  public MonitorGeofenceService(String name) {
    super(name);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    // check for errors
    if (LocationClient.hasError(intent) == false) {
      // Get the type of transition (enter or exit, -1 for invalid)
      int transitionType = LocationClient.getGeofenceTransition(intent);
      String title = null, toastText = null;
      if (transitionType >= 0) {
        List<Geofence> fences = LocationClient.getTriggeringGeofences(intent);
        toastText = Arrays.deepToString(fences.toArray());
        title = (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) ? "Enter"
            : "Exit";

        addToNotificationTray(transitionType, title, toastText);
      }
    }
  }

  private void addToNotificationTray(int type, String title, String text) {
    Context context = getApplication();
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
        .setSmallIcon(android.R.drawable.ic_dialog_map)
        .setContentTitle("Geofence").setContentText(title).setContentInfo(text)
//        .setSound(RingtoneManager.getDefaultUri(5))
        .setAutoCancel(true);

    NotificationManager manager = (NotificationManager) context
        .getSystemService(Context.NOTIFICATION_SERVICE);

    // id allows you to update the notification later on.
    manager.notify(type, builder.build());

  }
}
