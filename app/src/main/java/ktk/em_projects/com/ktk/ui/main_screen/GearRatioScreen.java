package ktk.em_projects.com.ktk.ui.main_screen;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

import ktk.em_projects.com.ktk.R;
import ktk.em_projects.com.ktk.utils.StringUtils;

/**
 * Created by E M on 26/01/2015.
 */
public class GearRatioScreen extends Activity {

    private static final String TAG = "GearRatioScreen";

    private EditText frontSprocketEditText;
    private EditText mainSprocketEditText;
    private TextView gearRatioTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gear_ratio_screen);
        Log.d(TAG, "onCreate");

        EasyTracker.getInstance(this).activityStart(this);

        frontSprocketEditText = (EditText) findViewById(R.id.frontSprocketEditText);
        mainSprocketEditText = (EditText) findViewById(R.id.mainSprocketEditText);
        gearRatioTextView = (TextView) findViewById(R.id.gearRatioTextView);

        frontSprocketEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do Nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String sStr = StringUtils.isNullOrEmpty(s.toString()) ? "0" : s.toString();
                int front = Integer.parseInt(sStr);
                int main = 0;
                if (!StringUtils.isNullOrEmpty(mainSprocketEditText.getText().toString())) {
                    main = Integer.parseInt(mainSprocketEditText.getText().toString());
                }
                calculateGearRation(front, main);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do Nothing
            }
        });
        mainSprocketEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do Nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String sStr = StringUtils.isNullOrEmpty(s.toString()) ? "0" : s.toString();
                int main = Integer.parseInt(sStr);
                int front = 0;
                if (!StringUtils.isNullOrEmpty(frontSprocketEditText.getText().toString())) {
                    front = Integer.parseInt(frontSprocketEditText.getText().toString());
                }
                calculateGearRation(front, main);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do Nothing
            }
        });
    }

    private void calculateGearRation(float front, float main) {
        float ratio = main / front;
        gearRatioTextView.setText(String.valueOf(ratio));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyTracker.getInstance(this).activityStop(this);
    }
}
