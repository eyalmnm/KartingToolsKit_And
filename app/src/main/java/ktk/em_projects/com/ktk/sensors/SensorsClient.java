package ktk.em_projects.com.ktk.sensors;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import ktk.em_projects.com.ktk.R;
import ktk.em_projects.com.ktk.config.Constants;
import ktk.em_projects.com.ktk.objects.LatLngAltSpd;
import ktk.em_projects.com.ktk.ui.main_screen.DriveTrackingScreen;
import ktk.em_projects.com.ktk.ui.widgets.stopwatch.DrivingTrackingStopWatchService;
import ktk.em_projects.com.ktk.utils.DateUtils;
import ktk.em_projects.com.ktk.utils.DriveRecorderFileUtils;
import ktk.em_projects.com.ktk.utils.JSONUtils;
import ktk.em_projects.com.ktk.utils.LocationUtils;
import ktk.em_projects.com.ktk.utils.StringUtils;

import static ktk.em_projects.com.ktk.config.Constants.INTENT_DATA_NAME.WORKING_STATE;


// Ref: http://developer.android.com/guide/components/services.html
// Ref: http://stackoverflow.com/questions/7175161/how-to-get-file-read-line-by-line
// Ref: http://stackoverflow.com/questions/14072748/overwrite-textfile-when-using-file-writer-append-in-android
// Ref: http://stackoverflow.com/questions/9095610/android-fileinputstream-read-txt-file-to-string
// Ref: http://stackoverflow.com/questions/32345768/cannot-resolve-method-setlatesteventinfo

/**
 * Created by eyalmuchtar on 11/02/2016.
 */
public class SensorsClient extends Service {

    private static final String TAG = "SensorsClient";
    private static final int NOTIFICATION_ID = 10;
    private static final String KTK_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/ktk.em_projects.com.ktk/files/"; // "//ktk_storage//";
    private static final long mFrequency = 100;    // milliseconds
    private static final int TICK_WHAT = 10;
    private static final String NEW_LINE = "\n";
    private final int SENSORS_STATE = 1;
    private final int FILE_STATE = 2;
    private EnvironmentalSensor mEnvironmentalSensor;
    private MotionSensor mMotionSensor;
    private PositionSensor mPositionSensor;
    private DrivingTrackingStopWatchService mDrivingTrackingStopWatchService;
    private EngineSensor mEngineSensor;
    private InfraRedSensor mInfraRedSensor;

    private int mWorkingState = SENSORS_STATE;

    // Parameters
    private LatLngAltSpd mLatLngAltSpd;
    private float[] mAccelerometer;
    private float[] mGyroscope;
    private float[] mRotation;
    private float mTemperature;
    private float mPressure;
    private long mElapseTime;
    private float[] mEngineData;
    private float[] mIrData;

    // Recording data
    private volatile boolean isRecording = false;

    private ServiceConnection mEnvironmentalSensorConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mEnvironmentalSensor = ((EnvironmentalSensor.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mEnvironmentalSensor = null;
        }
    };

    private ServiceConnection mPositionSensorConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPositionSensor = ((PositionSensor.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mPositionSensor = null;
        }
    };

    private ServiceConnection mMotionSensorConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMotionSensor = ((MotionSensor.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMotionSensor = null;
        }
    };

    private ServiceConnection mDrivingTrackingStopWatchServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDrivingTrackingStopWatchService = ((DrivingTrackingStopWatchService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private File mFile;
    private String mFileName;
    private FileWriter mFileWriter;
    private FileInputStream mFileInputStream;
    private InputStreamReader mInputStreamReader;
    private BufferedReader mBufferedReader;
    private LocalBinder mBinder = new LocalBinder();
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getSensorsData();
            updateNotification();
            sendMessageDelayed(Message.obtain(this, TICK_WHAT), mFrequency);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        createNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        initService(intent);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        initService(intent);
        return mBinder;
    }

    private void initService(Intent intent) {
        mWorkingState = intent.getIntExtra(WORKING_STATE, SENSORS_STATE);
        mFileName = intent.getStringExtra(Constants.INTENT_DATA_NAME.FILE_NAME);
        // Init the service
        if (mWorkingState == SENSORS_STATE) {
            connectAndBindEnvironmentalSensor();
            connectAndBindPositionSensor();
            connectAndBindMotionSensor();
            connectAndBindStopWatch();
        } else {
            if (StringUtils.isNullOrEmpty(mFileName))
                throw new NullPointerException("File Name can not be null in FILE_STATE mode");
            connectFile(intent);
        }
    }

    public void createNotification() {
        Log.d(TAG, "creating notification");

        int icon = R.drawable.ic_launcher;  //.stop_watch_icon;
        CharSequence tickerText = getResources().getString(R.string.app_name);
        long when = System.currentTimeMillis();

        mNotification = new Notification(icon, tickerText, when);
        mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
        mNotification.flags |= Notification.FLAG_NO_CLEAR;
    }

    public void updateNotification() {
        Log.d(TAG, "updating notification");

        Context context = getApplicationContext();
        CharSequence contentTitle = getResources().getString(R.string.app_name);
        CharSequence contentText = DateUtils.formatElapsedTime(mElapseTime);

        Intent notificationIntent = new Intent(this, DriveTrackingScreen.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // Ref: http://stackoverflow.com/questions/32345768/cannot-resolve-method-setlatesteventinfo
        // the next two lines initialize the Notification, using the configurations above
//        mNotification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        Notification.Builder builder = new Notification.Builder(context)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(contentTitle);
        mNotification = builder.build();
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }

    private void connectFile(Intent intent) {
        File dir = createDirIfNotExist(KTK_DIR);
        mFile = new File(KTK_DIR, mFileName);
        if (mFile == null)
            throw new NullPointerException("File can not be null in FILE_STATE mode");
        try {
            mFileInputStream = new FileInputStream(mFile);
            mInputStreamReader = new InputStreamReader(mFileInputStream);
            mBufferedReader = new BufferedReader(mInputStreamReader);
        } catch (IOException e) {
            Log.e(TAG, "connectFile", e);
        } finally {
            closeOpenedFileReaderComponents();
        }
    }

    private void closeOpenedFileReaderComponents() {
        if (mBufferedReader != null) {
            try {
                mBufferedReader.close();
            } catch (IOException e) {
            }
            mBufferedReader = null;
        }
        if (mInputStreamReader != null) {
            try {
                mInputStreamReader.close();
            } catch (IOException e) {
            }
            mInputStreamReader = null;
        }
        if (mFileInputStream != null) {
            try {
                mFileInputStream.close();
            } catch (IOException e) {
            }
            mFileInputStream = null;
        }
    }

    private void connectAndBindEnvironmentalSensor() {
        Intent intent = new Intent(this, EnvironmentalSensor.class);
        startService(intent);
        bindService(intent, mEnvironmentalSensorConnection, Context.BIND_AUTO_CREATE);
    }

    private void connectAndBindPositionSensor() {
        Intent intent = new Intent(this, PositionSensor.class);
        startService(intent);
        bindService(intent, mPositionSensorConnection, Context.BIND_AUTO_CREATE);
    }

    private void connectAndBindMotionSensor() {
        Intent intent = new Intent(this, MotionSensor.class);
        startService(intent);
        bindService(intent, mMotionSensorConnection, Context.BIND_AUTO_CREATE);
    }

    private void connectAndBindStopWatch() {
        Intent intent = new Intent(this, DrivingTrackingStopWatchService.class);
        startService(intent);
        bindService(intent, mDrivingTrackingStopWatchServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void getSensorsData() {
        resetParameters();
        if (mWorkingState == SENSORS_STATE) {
            mLatLngAltSpd = LocationUtils.convertLocationToLatLngAltSpd(mPositionSensor.getLocation());
            mGyroscope = mMotionSensor.getGyro();
            mAccelerometer = mMotionSensor.getAcceleration();
            mRotation = mMotionSensor.getRotation();
            mTemperature = mEnvironmentalSensor.getTemperatureCelsius();
            mPressure = mEnvironmentalSensor.getPressureMb();
            mElapseTime = mDrivingTrackingStopWatchService.getElapsedTime();
            saveToFile(mElapseTime, mLatLngAltSpd, mGyroscope, mAccelerometer, mRotation, mTemperature, mPressure);
        } else {
            String line = readParametersFromFile();
            if (!StringUtils.isNullOrEmpty(line)) {
                try {
                    parseParametersJson(new JSONObject(line));
                } catch (JSONException e) {
                    Log.e(TAG, "getSensorsData", e);
                }
            }
        }
    }

    private String readParametersFromFile() {
        if (mBufferedReader != null) {
            try {
                return mBufferedReader.readLine();
            } catch (IOException e) {
                Log.e(TAG, "readParametersFromFile", e);
            }
        }
        return null;
    }

    private void saveToFile(long elapseTime, LatLngAltSpd currentLocation, float[] gyroscope, float[] accelerometer, float[] rotation, float temperature, float pressure) {
        JSONObject json = convertToJsonObject(elapseTime, currentLocation, gyroscope, accelerometer, rotation, temperature, pressure);
        if (json != null && isRecording) writeToFile(json.toString() + NEW_LINE);
    }

    private void writeToFile(String s) {
        if (mFile == null) {
            File dir = createDirIfNotExist(KTK_DIR);
            if (StringUtils.isNullOrEmpty(mFileName)) {
                mFileName = DriveRecorderFileUtils.generateFileName(System.currentTimeMillis());
            }
            mFile = new File(dir, mFileName);
        }
        if (mFileWriter == null) {
            try {
                mFileWriter = new FileWriter(mFile, true);
            } catch (IOException e) {
                Log.e(TAG, "writeToFile", e);
            } finally {
                if (mFileWriter != null) {
                    try {
                        mFileWriter.flush();
                        mFileWriter.close();
                    } catch (IOException e) {
                    }
                    mFileWriter = null;
                }
            }
        }
        try {
            mFileWriter.append(s);
//            mFileWriter.flush();
        } catch (IOException e) {
            Log.e(TAG, "writeToFile", e);
        } finally {
            if (mFileWriter != null) {
                try {
                    mFileWriter.flush();
                    mFileWriter.close();
                } catch (IOException e) {
                }
                mFileWriter = null;
            }
        }
    }

    private File createDirIfNotExist(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) dir.mkdir();
        return dir;
    }

    private void resetParameters() {
        mLatLngAltSpd = null;
        mAccelerometer = null;
        mGyroscope = null;
        mRotation = null;
        mTemperature = 0;
        mPressure = 0;
        mElapseTime = 0;
    }

    private JSONObject convertToJsonObject(long elapsedTime, LatLngAltSpd currentLocation, float[] gyroscope, float[] accelerometer, float[] rotation, float temperature, float pressure) {
        JSONObject ret = new JSONObject();
        try {
            ret.put(Constants.JSON_NAME.TIME_STAMP, elapsedTime);
            if (currentLocation != null) {
                ret.put(Constants.JSON_NAME.POSITION_LAT, currentLocation.getLatitude());
                ret.put(Constants.JSON_NAME.POSITION_LNG, currentLocation.getLongitude());
                ret.put(Constants.JSON_NAME.POSITION_SPD, currentLocation.getSpeed());
                ret.put(Constants.JSON_NAME.POSITION_ALT, currentLocation.getAltitude());
                ret.put(Constants.JSON_NAME.POSITION_ACCURACY, currentLocation.getAccuracy());
            }
            if (gyroscope != null) {
                ret.put(Constants.JSON_NAME.GYROSCOPE, JSONUtils.convertToJsonArray(gyroscope));
            }
            if (accelerometer != null) {
                ret.put(Constants.JSON_NAME.ACCELERATION, JSONUtils.convertToJsonArray(accelerometer));
            }
            if (rotation != null) {
                ret.put(Constants.JSON_NAME.ROTATION, JSONUtils.convertToJsonArray(rotation));
            }
            ret.put(Constants.JSON_NAME.TEMPERATURE_CELSIUS, temperature);
            ret.put(Constants.JSON_NAME.PRESSURE_MB, pressure);
            return ret;
        } catch (JSONException e) {
            Log.e(TAG, "convertToJsonObject", e);
            ret = null;
        }
        return ret;
    }

    private void parseParametersJson(JSONObject json) {
        resetParameters();
        if (json != null) {
            mElapseTime = JSONUtils.getLongValue(json, Constants.JSON_NAME.TIME_STAMP);
            double lat = JSONUtils.getDoubleValue(json, Constants.JSON_NAME.POSITION_LAT);
            double lng = JSONUtils.getDoubleValue(json, Constants.JSON_NAME.POSITION_LNG);
            double spd = JSONUtils.getDoubleValue(json, Constants.JSON_NAME.POSITION_SPD);
            double alt = JSONUtils.getDoubleValue(json, Constants.JSON_NAME.POSITION_ALT);
            double accuracy = JSONUtils.getDoubleValue(json, Constants.JSON_NAME.POSITION_ACCURACY);
            mLatLngAltSpd = new LatLngAltSpd(lat, lng, spd, alt, accuracy);
            mGyroscope = JSONUtils.getFloatArrayValue(json, Constants.JSON_NAME.GYROSCOPE);
            mAccelerometer = JSONUtils.getFloatArrayValue(json, Constants.JSON_NAME.ACCELERATION);
            mRotation = JSONUtils.getFloatArrayValue(json, Constants.JSON_NAME.ROTATION);
            mTemperature = JSONUtils.getFloatValue(json, Constants.JSON_NAME.TEMPERATURE_CELSIUS);
            mPressure = JSONUtils.getFloatValue(json, Constants.JSON_NAME.PRESSURE_MB);
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        disconnectAndUnbindEnvironmentalSensor();
        disconnectAndUnbindPositionSensor();
        disconnectAndUnbindMotionSensor();
        disconnectAndBindStopWatch();
        closeFileComponents();
        closeOpenedFileReaderComponents();
        return super.onUnbind(intent);
    }

    private void closeFileComponents() {
        if (mFileWriter != null) {
            try {
                mFileWriter.flush();
                mFileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mFileWriter = null;
            }
        }
    }

    private void disconnectAndUnbindEnvironmentalSensor() {
        if (mEnvironmentalSensorConnection != null) unbindService(mEnvironmentalSensorConnection);
        stopService(new Intent(this, EnvironmentalSensor.class));
    }

    private void disconnectAndUnbindPositionSensor() {
        if (mPositionSensorConnection != null) unbindService(mPositionSensorConnection);
        stopService(new Intent(this, PositionSensor.class));
    }

    private void disconnectAndUnbindMotionSensor() {
        if (mMotionSensorConnection != null) unbindService(mMotionSensorConnection);
        stopService(new Intent(this, MotionSensor.class));
    }

    private void disconnectAndBindStopWatch() {
        if (mDrivingTrackingStopWatchServiceConnection != null)
            unbindService(mDrivingTrackingStopWatchServiceConnection);
        stopService(new Intent(this, DrivingTrackingStopWatchService.class));
    }

    public float[] getAccelerometer() {
        return mAccelerometer;
    }

    public float[] getGyroscope() {
        return mGyroscope;
    }

    public LatLngAltSpd getLatLngAltSpd() {
        return mLatLngAltSpd;
    }

    public float getPressure() {
        return mPressure;
    }

    public float[] getRotation() {
        return mRotation;
    }

    public float getTemperature() {
        return mTemperature;
    }

    public long getElapsedTime() {
        return mElapseTime;
    }

    public boolean startRecording() {
        if (isRecording) return false;
        else {
            isRecording = true;
            return true;
        }
    }

    public boolean stopRecording() {
        if (!isRecording) return false;
        else {
            isRecording = false;
            if (mFileWriter != null) try {
                mFileWriter.flush();
            } catch (IOException e) {
                Log.e(TAG, "stopRecording", e);
            }
            return true;
        }
    }

    public class LocalBinder extends Binder {
        public SensorsClient getService() {
            return SensorsClient.this;
        }
    }
}
