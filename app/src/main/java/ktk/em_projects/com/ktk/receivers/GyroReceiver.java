package ktk.em_projects.com.ktk.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import ktk.em_projects.com.ktk.config.Constants;

public class GyroReceiver extends BroadcastReceiver {

	private static final String ACTION = "ktk.em_projects.com.ktk.receivers.gyroscope";
	private static final String DATA_NAME = Constants.INTENT_DATA_NAME.GYROSCOPE;
	
	private OnGyroscopeChangedListener listener;
	
	public GyroReceiver(OnGyroscopeChangedListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(listener != null) {
			float[] gyro = intent.getFloatArrayExtra(DATA_NAME);
			listener.onGyroscopeChanged(gyro);
		}
	}

	public IntentFilter getIntentFilter () {
		IntentFilter intentFilter = new IntentFilter(ACTION);
		return intentFilter;
	}
	
	public static String getAction () {
		return ACTION;
	}
	
	public static String getDataName () {
		return DATA_NAME;
	}	

	public interface OnGyroscopeChangedListener {
		public void onGyroscopeChanged(float[] gyroValues);
	}
}
