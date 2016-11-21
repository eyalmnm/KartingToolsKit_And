package ktk.em_projects.com.ktk.sensors;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import ktk.em_projects.com.ktk.tools.bt_tools.BtEngineRPMDetector;
import ktk.em_projects.com.ktk.tools.bt_tools.BtEngineTempIndicator;

/**
 * Created by eyalmuchtar on 26/02/2016.
 */
public class EngineSensor extends Service {

    private static final String TAG = "EngineSensor";

    private BtEngineRPMDetector mBtEngineRPMDetector;
    private BtEngineTempIndicator mBtEngineTempIndicator;

    private LocalBinder binder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class LocalBinder extends Binder {
        public EngineSensor getService() {
            return EngineSensor.this;
        }
    }

}
