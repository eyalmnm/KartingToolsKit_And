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
//import ktk.em_projects.com.ktk.receivers.PressureReceiver;
//import ktk.em_projects.com.ktk.receivers.TemperatureReceiver;

// Ref: http://www.tutorialspoint.com/android/android_sensors.htm
// Ref: http://www.vogella.com/tutorials/AndroidSensor/article.html

public class EnvironmentalSensor extends Service implements SensorEventListener {

	private static final String TAG = "EnvironmentalSensor";

	private SensorManager sensorManager;

	private float pressureMb = 0;
	private float temperatureCelsius = 0;

	private LocalBinder binder = new LocalBinder();

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		super.onCreate();
		sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		Sensor pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
		Sensor temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
		if (pressureSensor != null) {
			sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_GAME);
		}
		if (temperatureSensor != null) {
			sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_GAME);
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
			if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
				temperatureCelsius = event.values[0];
//				sendTemperatureUpdate(temperatureCelsius);
			} else if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
				pressureMb = event.values[0];
//				sendPressureUpdate(pressureMb);
			}
		}
	}

//	private void sendTemperatureUpdate(float temperatureCelsius2) {
//		Intent intent = new Intent(TemperatureReceiver.getAction());
//		intent.putExtra(TemperatureReceiver.getDataName(), temperatureCelsius2);
//		sendBroadcast(intent);
//	}

//	private void sendPressureUpdate(float pressureMb2) {
//		Intent intent = new Intent(PressureReceiver.getAction());
//		intent.putExtra(PressureReceiver.getDataName(), pressureMb2);
//		sendBroadcast(intent);
//	}

	public float getTemperatureCelsius() {
		return temperatureCelsius;
	}

	public float getPressureMb() {
		return pressureMb;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "bound");
		return binder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		sensorManager.unregisterListener(this);
	}

	public class LocalBinder extends Binder {
		public EnvironmentalSensor getService() {
			return EnvironmentalSensor.this;
		}
	}
}
