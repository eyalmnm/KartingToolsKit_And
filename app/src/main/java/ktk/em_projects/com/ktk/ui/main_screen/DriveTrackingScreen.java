/*
 * This activity can be run in one of two mode: real time and history.
 * When this activity called, the application must supply the file to read the data from. 
 * Otherwise, the activity run on real time mode, this means display data from the sensors.
 * If GPS no available, in real time mode, the application will not started and a proper message
 * will be displayed. If Calibration of the motion sensor not finished the application can be started
 * according to user's decision. The motion's gouge will be displayed as grayed out image. 
 */
package ktk.em_projects.com.ktk.ui.main_screen;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import ktk.em_projects.com.ktk.R;
import ktk.em_projects.com.ktk.sensors.SensorsClient;

// For Logging data.
// Ref: http://stackoverflow.com/questions/5987012/append-to-file-android
// Ref: http://android-er.blogspot.co.il/2011/09/use-handler-and-runnable-to-generate.html

//For small Custom GPS marker - gray_map_marker.xml
//Ref: http://stackoverflow.com/questions/16828945/small-custom-markers-on-android-google-maps-v2

//For draw interactive Polyline on route google maps v2 android
//Ref: http://stackoverflow.com/questions/17425499/how-to-draw-interactive-polyline-on-route-google-maps-v2-android

//TODO  android how to switch between terrain and sattelite in Google map fragments
//Ref: http://wptrafficanalyzer.in/blog/google-map-android-api-v2-switching-between-normal-view-satellite-view-and-terrain-view/
//Ref: http://www.google.com/search?client=ms-android-samsung&source=android-home&site=webhp&source=hp&ei=hyPBVIG-F9PbaI_ZgbgG&q=android+how+to+switch+between+terrain+and+sattelite+in+Google+map+fragments&oq=android+how+to+switch+between+terrain+and+sattelite+in+Google+map+fragments&gs_l=mobile-gws-hp.3..30i10.5790.133675.0.135868.85.60.7.18.18.1.2502.33082.2-7j17j15j5j3j2j2j3.54.0.msedr...0...1c.1.61.mobile-gws-hp..23.62.26259.3.WeGebrVFwQI&rlz=1Y1XIUG_iwIL602IL603
//TODO  android how to switch between terrain and sattelite in Google map fragments

// For Accelerometer calibration and indicator
//Ref: http://www.kircherelectronics.com/blog/index.php/11-android/sensors/7-android-accelerometer

// For getting last best location
//Ref: https://developer.android.com/training/location/retrieve-current.html

// For calculate travelled distance
// Ref: http://stackoverflow.com/questions/8132198/how-to-calculate-distance-travelled

public class DriveTrackingScreen extends Activity {

    private static final String TAG = "DriveTrackingScreen";

    private static final long FREQUENCY = 100;    // milliseconds
    private static final int TICK_WHAT = 2;

    private Context context;

    // For retrieving location from GoogleServices
    private GoogleApiClient googleApiClient;

    // Screen's UI components
    private TextView elapsedTimeTextView;

    private MapFragment mapFragment;
    private GoogleMap googleMap;
    private Location lastBestLocation;

    // For retrieving sensors data;
    private SensorsClient sensorsClient;
    private ServiceConnection SensorsClientServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    // Use for retrieve the current state and update the screen every period of time.
    private Handler handler = new Handler() {
        public void handleMessage(Message m) {
            updateView();
            sendMessageDelayed(Message.obtain(this, TICK_WHAT), FREQUENCY);
        }
    };

    private void updateView() {
        Log.e(TAG, "updateView NOT IMPLEMENTED"); // TODO
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.drive_tracking_screen);

        context = this;

        // Google Analytics tracking
        EasyTracker.getInstance(this).activityStart(this);

        // Initial Sensors Client service.
        startService(new Intent(this, SensorsClient.class));
        bindSensorsClientService();

        // Initial screen's components (Map and other UI Components)
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        // Getting reference to google map
        googleMap = mapFragment.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    private void bindSensorsClientService() {
        Log.e(TAG, "bindSensorsClientService NOT IMPLEMENTED"); // TODO
    }


    @Override
    protected void onDestroy() {
        EasyTracker.getInstance(this).activityStop(this);
        unbindSensorsClientService();
        super.onDestroy();
    }

    private void unbindSensorsClientService() {
        Log.e(TAG, "unbindSensorsClientService NOT IMPLEMENTED"); // TODO
    }
}
