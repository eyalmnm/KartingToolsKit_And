package ktk.em_projects.com.ktk.sensors;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

// Ref: http://www.tutorialspoint.com/android/android_sensors.htm
// Ref: http://www.vogella.com/tutorials/AndroidSensor/article.html
// Ref: http://developer.android.com/training/location/retrieve-current.html

public class PositionSensor extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    private boolean mConnected = false;

    private LocalBinder binder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mGoogleApiClient.connect();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }


    @Override
    public IBinder onBind(Intent intent) {
        mGoogleApiClient.connect();
        return binder;
    }

    @Override
    public void onConnected(Bundle bundle) {
        mConnected = true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        mConnected = false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mConnected = false;
    }

    public Location getLocation() {
        if (mConnected) {
            return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } else {
            return null;
        }
    }

    public class LocalBinder extends Binder {
        public PositionSensor getService() {
            return PositionSensor.this;
        }
    }
}
