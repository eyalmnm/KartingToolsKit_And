package ktk.em_projects.com.ktk.ui.main_screen;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

import ktk.em_projects.com.ktk.R;
import ktk.em_projects.com.ktk.utils.StringUtils;

/**
 * Created by E M on 26/01/2015.
 */

// http://stackoverflow.com/questions/3576507/is-it-possible-to-change-the-radio-button-icon-in-an-android-radio-button-group
// http://stackoverflow.com/questions/7117209/how-to-know-key-presses-in-edittext

public class FuelOilMixScreen extends Activity {

    private static final String TAG = "FuelOilMixScreen";

    private RadioGroup calculationOptionsRadioGroup;
    //    private RadioButton oilPercentageRadioButton;
//    private RadioButton oilRatioRadioButton;
    private EditText oilPercentageEditText;
    private EditText fuelQuantityEditText;
    private TextView oilQuantityTextView;

    private boolean percentageSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuel_oil_mix_screen);
        Log.d(TAG, "onCreate");

        EasyTracker.getInstance(this).activityStart(this);

        calculationOptionsRadioGroup = (RadioGroup) findViewById(R.id.calculationOptionsRadioGroup);
//        oilPercentageRadioButton = (RadioButton) findViewById(R.id.oilPercentageRadioButton);
//        oilRatioRadioButton = (RadioButton) findViewById(R.id.oilRatioRadioButton);
        oilPercentageEditText = (EditText) findViewById(R.id.oilPercentageEditText);
        fuelQuantityEditText = (EditText) findViewById(R.id.fuelQuantityEditText);
        oilQuantityTextView = (TextView) findViewById(R.id.oilQuantityTextView);

        calculationOptionsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.oilPercentageRadioButton) {
                    percentageSelected = true;
                } else {
                    percentageSelected = false;
                }
                float oil = 0;
                float fuel = 0;
                if (!StringUtils.isNullOrEmpty(oilPercentageEditText.getText().toString())) {
                    oil = Float.parseFloat(oilPercentageEditText.getText().toString());
                }
                if (!StringUtils.isNullOrEmpty(fuelQuantityEditText.getText().toString())) {
                    fuel = Float.parseFloat(fuelQuantityEditText.getText().toString());
                }
                calculateOilQuantity(oil, fuel);
            }
        });

        oilPercentageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do Nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String sStr = StringUtils.isNullOrEmpty(s.toString()) ? "0" : s.toString();
                float oil = Float.parseFloat(sStr);
                float fuel = 0;
                if (!StringUtils.isNullOrEmpty(fuelQuantityEditText.getText().toString())) {
                    fuel = Float.parseFloat(fuelQuantityEditText.getText().toString());
                }
                calculateOilQuantity(oil, fuel);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // DO Nothing
            }
        });
        fuelQuantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do Nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String sStr = StringUtils.isNullOrEmpty(s.toString()) ? "0" : s.toString();
                float fuel = Float.parseFloat(sStr);
                float oil = 0;
                if (!StringUtils.isNullOrEmpty(oilPercentageEditText.getText().toString())) {
                    oil = Float.parseFloat(oilPercentageEditText.getText().toString());
                }
                calculateOilQuantity(oil, fuel);

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do Nothing
            }
        });
    }

    private void calculateOilQuantity(float oil, float fuel) {
        String text = "----";
        if (oil != 0 && fuel != 0) {
            if (percentageSelected) {
                text = calculatePercentage(oil, fuel);
            } else {
                text = calculateRatio(oil, fuel);
            }
        }
        oilQuantityTextView.setText(text + "ml");
    }

    private String calculatePercentage(float oil, float fuel) {
        float quantity = fuel * 10 * oil;
        return String.valueOf(quantity);
    }

    private String calculateRatio(float oil, float fuel) {
        float quantity = fuel * 1000 / oil;
        return String.valueOf(quantity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyTracker.getInstance(this).activityStop(this);
    }
}
