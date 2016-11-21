package ktk.em_projects.com.ktk.ui.main_screen;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.analytics.tracking.android.EasyTracker;

import ktk.em_projects.com.ktk.R;

/**
 * Created by E M on 26/01/2015.
 */

// https://psgka.wordpress.com/getting-starting-in-karting/flag-meanings/

public class FlagsScreen extends Activity {

    private static final String TAG = "FlagsScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flags_screen);
        Log.d(TAG, "onCreate");

        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyTracker.getInstance(this).activityStop(this);
    }
}
