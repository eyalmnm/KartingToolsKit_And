package ktk.em_projects.com.ktk.ui.widgets.stopwatch;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.util.Log;

import ktk.em_projects.com.ktk.R;
import ktk.em_projects.com.ktk.ui.main_screen.DriveTrackingScreen;

public class DrivingTrackingStopWatchService extends StopwatchService {

    private static final String TAG = "DrivingTrackingStopWatc";

    @SuppressWarnings("deprecation")
    @Override
    public void updateNotification() {
        Log.d(TAG, "updating notification");

        Context context = getApplicationContext();
        CharSequence contentTitle = getResources().getString(R.string.app_name);
        CharSequence contentText = getFormattedElapsedTime();

        Intent notificationIntent = new Intent(this, DriveTrackingScreen.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // the next two lines initialize the Notification, using the configurations above
        m_notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        m_notificationMgr.notify(NOTIFICATION_ID, m_notification);
    }

    public void start() {
        Log.d(TAG, "start");
        super.start();
    }

    public void pause() {
        Log.d(TAG, "pause");
        super.pause();
    }

    public void lap() {
        Log.d(TAG, "lap");
        super.lap();
    }

    public void reset() {
        Log.d(TAG, "reset");
        super.reset();
    }

    public long getElapsedTime() {
        return super.getElapsedTime();
    }

    public String getFormattedElapsedTime() {
        return super.getFormattedElapsedTime();
    }

    public boolean isStopwatchRunning() {
        return super.isStopwatchRunning();
    }

    public class LocalBinder extends Binder {
        public DrivingTrackingStopWatchService getService() {
            return DrivingTrackingStopWatchService.this;
        }
    }
}
