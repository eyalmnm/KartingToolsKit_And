package ktk.em_projects.com.ktk.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import ktk.em_projects.com.ktk.config.Constants;

public class AccelerationReceiver extends BroadcastReceiver {

    private static final String ACTION = "ktk.em_projects.com.ktk.receivers.acceleration";
    private static final String DATA_NAME = Constants.INTENT_DATA_NAME.ACCELERATION;

    private OnAccelerationChangedListener listener;

    public AccelerationReceiver(OnAccelerationChangedListener listener) {
        this.listener = listener;
    }

    public static String getAction() {
        return ACTION;
    }

    public static String getDataName() {
        return DATA_NAME;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener != null) {
            float[] acceleration = intent.getFloatArrayExtra(DATA_NAME);
            listener.onAccelerationChanged(acceleration);
        }
    }

    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter(ACTION);
        return intentFilter;
    }

    public interface OnAccelerationChangedListener {
        public void onAccelerationChanged(float[] accelerationValues);
    }
}
