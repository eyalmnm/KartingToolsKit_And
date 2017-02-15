package ktk.em_projects.com.ktk.ui.main_screen;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

import ktk.em_projects.com.ktk.MainActivity;
import ktk.em_projects.com.ktk.R;
import ktk.em_projects.com.ktk.ui.widgets.stopwatch.StopwatchService;

public class StopwatchScreen extends ListActivity {
    private static String TAG = "StopwatchActivity";
    // Timer to update the elapsedTime display
    private final long mFrequency = 100;    // milliseconds
    private final int TICK_WHAT = 2;
    // View elements in stopwatch.xml
    private TextView m_elapsedTime;
    private Button m_start;
    private Button m_pause;
    private Button m_reset;
    private Button m_lap;
    private ArrayAdapter<String> m_lapList;
    // Connection to the backgorund StopwatchService
    private StopwatchService m_stopwatchService;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message m) {
            updateElapsedTime();
            sendMessageDelayed(Message.obtain(this, TICK_WHAT), mFrequency);
        }
    };
    private ServiceConnection m_stopwatchServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            m_stopwatchService = ((StopwatchService.LocalBinder) service).getService();
            showCorrectButtons();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            m_stopwatchService = null;
        }
    };

    private void bindStopwatchService() {
        bindService(new Intent(this, StopwatchService.class),
                m_stopwatchServiceConn, Context.BIND_AUTO_CREATE);
    }

    private void unbindStopwatchService() {
        if (m_stopwatchService != null) {
            unbindService(m_stopwatchServiceConn);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_watch_screen);

        EasyTracker.getInstance(this).activityStart(this);

        setListAdapter(new ArrayAdapter<String>(this, R.layout.laps_row));

        startService(new Intent(this, StopwatchService.class));
        bindStopwatchService();

        m_elapsedTime = (TextView) findViewById(R.id.ElapsedTime);

        m_start = (Button) findViewById(R.id.StartButton);
        m_pause = (Button) findViewById(R.id.PauseButton);
        m_reset = (Button) findViewById(R.id.ResetButton);
        m_lap = (Button) findViewById(R.id.LapButton);

        m_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartClicked();
            }
        });
        m_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPauseClicked();
            }
        });
        m_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetClicked();
            }
        });
        m_lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLapClicked();
            }
        });

        m_lapList = (ArrayAdapter<String>) getListAdapter();

        mHandler.sendMessageDelayed(Message.obtain(mHandler, TICK_WHAT), mFrequency);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyTracker.getInstance(this).activityStop(this);
        unbindStopwatchService();
    }

    private void showCorrectButtons() {
        Log.d(TAG, "showCorrectButtons");

        if (m_stopwatchService != null) {
            if (m_stopwatchService.isStopwatchRunning()) {
                showPauseLapButtons();
            } else {
                showStartResetButtons();
            }
        }
    }

    private void showPauseLapButtons() {
        Log.d(TAG, "showPauseLapButtons");

        m_start.setVisibility(View.GONE);
        m_reset.setVisibility(View.GONE);
        m_pause.setVisibility(View.VISIBLE);
        m_lap.setVisibility(View.VISIBLE);
    }

    private void showStartResetButtons() {
        Log.d(TAG, "showStartResetButtons");

        m_start.setVisibility(View.VISIBLE);
        m_reset.setVisibility(View.VISIBLE);
        m_pause.setVisibility(View.GONE);
        m_lap.setVisibility(View.GONE);
    }

    public void onStartClicked() {
        Log.d(TAG, "start button clicked");
        m_stopwatchService.start();

        showPauseLapButtons();
    }

    public void onPauseClicked() {
        Log.d(TAG, "pause button clicked");
        m_stopwatchService.pause();

        showStartResetButtons();
    }

    public void onResetClicked() {
        Log.d(TAG, "reset button clicked");
        m_stopwatchService.reset();

        m_lapList.clear();
    }

    public void onLapClicked() {
        Log.d(TAG, "lap button clicked");
        m_stopwatchService.lap();

        m_lapList.insert(m_stopwatchService.getFormattedElapsedTime(), 0);
    }

    public void updateElapsedTime() {
        if (m_stopwatchService != null)
            m_elapsedTime.setText(m_stopwatchService.getFormattedElapsedTime());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}