package ktk.em_projects.com.ktk.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import ktk.em_projects.com.ktk.config.Constants;

public class PressureReceiver extends BroadcastReceiver {

    private static final String ACTION = "ktk.em_projects.com.ktk.receivers.pressure";
    private static final String DATA_NAME = Constants.INTENT_DATA_NAME.PRESSURE_MB;

    private OnPressureChangedListener listener;

    public PressureReceiver(OnPressureChangedListener listener) {
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
            float temperature = intent.getFloatExtra(Constants.INTENT_DATA_NAME.TEMPERATURE_CELSIUS, 100);
            listener.onPressureChanged(temperature);
        }
    }

    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter(ACTION);
        return intentFilter;
    }

    public interface OnPressureChangedListener {
        public void onPressureChanged(float temperature);
    }
}
