package ktk.em_projects.com.ktk.ui.fragments.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import ktk.em_projects.com.ktk.R;
import ktk.em_projects.com.ktk.utils.StringUtils;

/**
 * Created by eyal muchtar on 20/02/2017.
 */

public class LapTimeCreatorDialog extends DialogFragment {

    public static final long SECOND = 1000;
    public static final long MINUTE = 60 * SECOND;
    public static final long SEC100 = 10;
    private static final String TAG = "LapTimeCreatorDialog";
    // UI Components
    private EditText lapMinutesEditText;
    private EditText lapSecondsEditText;
    private EditText lap100OfSecEditText;

    private Button lapOkButton;
    private Button lapCancelButton;
    private LapTimeCreatorListener listener = null;

    public static long calcTime(String minutes, String seconds, String hundredsOfSec) {
        long sumMiilis = 0;
        if (false == StringUtils.isNullOrEmpty(minutes)) {
            sumMiilis += Long.parseLong(minutes) * MINUTE;
        }
        if (false == StringUtils.isNullOrEmpty(seconds)) {
            sumMiilis += Long.parseLong(seconds) * SECOND;
        }
        if (false == StringUtils.isNullOrEmpty(hundredsOfSec)) {
            sumMiilis += Long.parseLong(hundredsOfSec) * SEC100;
        }
        return sumMiilis;
    }

    @Override
    public void onAttach(Context context) {
        try {
            listener = (LapTimeCreatorListener) context;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach", e);
            throw new ClassCastException(e.getMessage());
        }
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Handle dialog size
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Dialog myDialog = this.getDialog();
        myDialog.getWindow().setLayout(width, height);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Remove dialog title
        Dialog myDialog = this.getDialog();
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.dialog_lap_time_creator_layout, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        lapMinutesEditText = (EditText) view.findViewById(R.id.lapMinutesEditText);
        lapSecondsEditText = (EditText) view.findViewById(R.id.lapSecondsEditText);
        lap100OfSecEditText = (EditText) view.findViewById(R.id.lap100OfSecEditText);

        lapOkButton = (Button) view.findViewById(R.id.lapOkButton);
        lapCancelButton = (Button) view.findViewById(R.id.lapCancelButton);

        lapOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    String minutes = lapMinutesEditText.getText().toString();
                    String seconds = lapSecondsEditText.getText().toString();
                    String hundredsOfSec = lap100OfSecEditText.getText().toString();
                    long millis = calcTime(minutes, seconds, hundredsOfSec);
                    listener.onLapTimeCreated(millis, minutes, seconds, hundredsOfSec);
                }
                dismiss();
            }
        });
        lapCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.oLapTimeCancelled();
                }
                dismiss();
            }
        });
    }

    public interface LapTimeCreatorListener {
        public void onLapTimeCreated(long millis, String minutes, String seconds, String hundredsOfSec);

        public void oLapTimeCancelled();
    }
}
