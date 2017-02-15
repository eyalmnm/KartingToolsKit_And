package ktk.em_projects.com.ktk.sensors;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
//import ktk.em_projects.com.ktk.receivers.AccelerationReceiver;
//import ktk.em_projects.com.ktk.receivers.GyroReceiver;
//import ktk.em_projects.com.ktk.receivers.RotationReceiver;

// Ref: http://www.tutorialspoint.com/android/android_sensors.htm
// Ref: http://www.vogella.com/tutorials/AndroidSensor/article.html

public class MotionSensor extends Service implements SensorEventListener {

    private static final String TAG = "MotionSensor";

    private SensorManager sensorManager;

    private float[] rotation;
    private float[] acceleration;
    private float[] gyro;

    private LocalBinder binder = new LocalBinder();

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        Sensor rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (rotationSensor != null) {
            sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_GAME);
        }
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        }
        if (gyroscopeSensor != null) {
            sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
        // return Service.START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        transmitEventValue(event);
    }

    private void transmitEventValue(SensorEvent event) {
        if (event.accuracy == SensorManager.SENSOR_STATUS_ACCURACY_HIGH
                || event.accuracy == SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM) {
            if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
                rotation = event.values;
//				sendRotationUpdate(rotation);
            } else if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                acceleration = event.values;
//				sendAccelerometerUpdate(acceleration);
            } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                gyro = event.values;
//				sendGyroscopeUpdate(gyro);
            }
        }
    }

//	private void sendGyroscopeUpdate(float[] gyro2) {
//		Intent intent = new Intent(GyroReceiver.getAction());
//		intent.putExtra(GyroReceiver.getDataName(), gyro2);
//		sendBroadcast(intent);
//	}

//	private void sendAccelerometerUpdate(float[] acceleration2) {
//		Intent intent = new Intent(AccelerationReceiver.getAction());
//		intent.putExtra(AccelerationReceiver.getDataName(), acceleration2);
//		sendBroadcast(intent);
//	}

//	private void sendRotationUpdate(float[] rotation2) {
//		Intent intent = new Intent(RotationReceiver.getAction());
//		intent.putExtra(RotationReceiver.getDataName(), rotation2);
//		sendBroadcast(intent);
//	}

    public float[] getRotation() {
        return rotation;
    }

    public float[] getAcceleration() {
        return acceleration;
    }

    public float[] getGyro() {
        return gyro;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    public class LocalBinder extends Binder {
        public MotionSensor getService() {
            return MotionSensor.this;
        }
    }
}
