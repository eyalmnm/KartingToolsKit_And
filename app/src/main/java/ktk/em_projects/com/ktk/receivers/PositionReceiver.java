package ktk.em_projects.com.ktk.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;

import ktk.em_projects.com.ktk.config.Constants;

public class PositionReceiver extends BroadcastReceiver {

	private static final String ACTION = "ktk.em_projects.com.ktk.receivers.position";
	private static final String DATA_NAME = Constants.INTENT_DATA_NAME.POSITION;
	
	private OnPositionChangedListener listener;	
	
	public PositionReceiver(OnPositionChangedListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(listener != null) {
			Location location = intent.getParcelableExtra(DATA_NAME);
			listener.onPositionChanged(location);
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

	public interface OnPositionChangedListener {
		public void onPositionChanged(Location location);
	}
}
