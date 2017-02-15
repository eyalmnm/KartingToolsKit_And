package ktk.em_projects.com.ktk.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import ktk.em_projects.com.ktk.R;

/**
 * Created by E M on 02/02/2015.
 */
public class TimeEntryFragment extends DialogFragment {

    private static final String TAG = "TimeEntryFragment";

    private final int PIN_LENGTH = 6;
    private TimeEntryListener listener;
    private String userEntered;
    private boolean keyPadLockedFlag = false;
    private Context appContext;
    private int requestCode = -1;
    //    private TextView titleView;
    private TextView statusView;
    private TextView pinBox0;
    private TextView pinBox1;
    private TextView pinBox2;
    private TextView pinBox3;
    private TextView pinBox4;
    private TextView pinBox5;
    private TextView[] pinBoxArray;
    private Button buttonExit;
    private Button buttonDeleteBack;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    public TimeEntryFragment() {
        requestCode = -1;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (TimeEntryListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage());
        }
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.time_entry_fragment, null);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        userEntered = "";
        appContext = getActivity();

        Typeface xpressive = Typeface.createFromAsset(appContext.getAssets(), "fonts/XpressiveBold.ttf");

        buttonExit = (Button) view.findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new View.OnClickListener() {

                                          public void onClick(View v) {

                                              //Exit app
                                              Intent i = new Intent();
                                              i.setAction(Intent.ACTION_MAIN);
                                              i.addCategory(Intent.CATEGORY_HOME);
                                              appContext.startActivity(i);
                                              dismiss();
                                          }
                                      }
        );
        buttonExit.setTypeface(xpressive);

        buttonDeleteBack = (Button) view.findViewById(R.id.buttonDeleteBack);
        buttonDeleteBack.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View v) {

                                                    if (keyPadLockedFlag == true) {
                                                        return;
                                                    }

                                                    if (userEntered.length() > 0) {
                                                        userEntered = userEntered.substring(0, userEntered.length() - 1);
                                                        pinBoxArray[userEntered.length()].setText("");
                                                    }
                                                }
                                            }
        );

        pinBox0 = (TextView) view.findViewById(R.id.pinBox0);
        pinBox1 = (TextView) view.findViewById(R.id.pinBox1);
        pinBox2 = (TextView) view.findViewById(R.id.pinBox2);
        pinBox3 = (TextView) view.findViewById(R.id.pinBox3);
        pinBox4 = (TextView) view.findViewById(R.id.pinBox4);
        pinBox5 = (TextView) view.findViewById(R.id.pinBox5);

        pinBoxArray = new TextView[PIN_LENGTH];
        pinBoxArray[0] = pinBox0;
        pinBoxArray[1] = pinBox1;
        pinBoxArray[2] = pinBox2;
        pinBoxArray[3] = pinBox3;
        pinBoxArray[4] = pinBox4;
        pinBoxArray[5] = pinBox5;

//        titleView = (TextView) view.findViewById(R.id.titleBox);
//        titleView.setTypeface(xpressive);

        statusView = (TextView) view.findViewById(R.id.statusMessage);
        statusView.setTypeface(xpressive);
        View.OnClickListener pinButtonHandler = new View.OnClickListener() {
            public void onClick(View v) {

                if (keyPadLockedFlag == true) {
                    return;
                }

                Button pressedButton = (Button) v;

                if (userEntered.length() < PIN_LENGTH) {
                    userEntered = userEntered + pressedButton.getText();
                    Log.v("PinView", "User entered=" + userEntered);

                    //Update pin boxes
                    pinBoxArray[userEntered.length() - 1].setText(pressedButton.getText()); //("8");

                    if (userEntered.length() == PIN_LENGTH) {
                        //Check if entered PIN is correct
                        if (requestCode == -1)
                            throw new NullPointerException("requestCode never set.");
                        if (listener != null) {
                            String str0 = pinBoxArray[0].getText().toString();
                            String str1 = pinBoxArray[1].getText().toString();
                            String str2 = pinBoxArray[2].getText().toString();
                            String str3 = pinBoxArray[3].getText().toString();
                            String str4 = pinBoxArray[4].getText().toString();
                            String str5 = pinBoxArray[5].getText().toString();
                            String timeString = str0 + str1 + ":" + str2 + str3 + "." + str4 + str5;
                            long timeLong = calculateTime(str0, str1, str2, str3, str4, str5);
                            listener.setTimeEntry(timeString, timeLong, requestCode);
                        }
                        dismiss();
                    }
                } else {
                    //Roll over
                    pinBoxArray[0].setText("");
                    pinBoxArray[1].setText("");
                    pinBoxArray[2].setText("");
                    pinBoxArray[3].setText("");
                    pinBoxArray[4].setText("");
                    pinBoxArray[5].setText("");

                    userEntered = "";

                    statusView.setText("");

                    userEntered = userEntered + pressedButton.getText();
                    Log.v("PinView", "User entered=" + userEntered);

                    //Update pin boxes
                    pinBoxArray[userEntered.length() - 1].setText("8");

                }


            }
        };

        button0 = (Button) view.findViewById(R.id.button0);
        button0.setTypeface(xpressive);
        button0.setOnClickListener(pinButtonHandler);

        button1 = (Button) view.findViewById(R.id.button1);
        button1.setTypeface(xpressive);
        button1.setOnClickListener(pinButtonHandler);

        button2 = (Button) view.findViewById(R.id.button2);
        button2.setTypeface(xpressive);
        button2.setOnClickListener(pinButtonHandler);


        button3 = (Button) view.findViewById(R.id.button3);
        button3.setTypeface(xpressive);
        button3.setOnClickListener(pinButtonHandler);

        button4 = (Button) view.findViewById(R.id.button4);
        button4.setTypeface(xpressive);
        button4.setOnClickListener(pinButtonHandler);

        button5 = (Button) view.findViewById(R.id.button5);
        button5.setTypeface(xpressive);
        button5.setOnClickListener(pinButtonHandler);

        button6 = (Button) view.findViewById(R.id.button6);
        button6.setTypeface(xpressive);
        button6.setOnClickListener(pinButtonHandler);

        button7 = (Button) view.findViewById(R.id.button7);
        button7.setTypeface(xpressive);
        button7.setOnClickListener(pinButtonHandler);

        button8 = (Button) view.findViewById(R.id.button8);
        button8.setTypeface(xpressive);
        button8.setOnClickListener(pinButtonHandler);

        button9 = (Button) view.findViewById(R.id.button9);
        button9.setTypeface(xpressive);
        button9.setOnClickListener(pinButtonHandler);


        buttonDeleteBack = (Button) view.findViewById(R.id.buttonDeleteBack);
        buttonDeleteBack.setTypeface(xpressive);

        return view;
    }

    private long calculateTime(String str0, String str1, String str2, String str3, String str4, String str5) {
        long retLong = 0;
        retLong += Integer.parseInt(str5) * 10;
        retLong += Integer.parseInt(str4) * 100;
        retLong += Integer.parseInt(str3) * 1000;
        retLong += Integer.parseInt(str2) * 10000;
        retLong += Integer.parseInt(str1) * 60000;
        retLong += Integer.parseInt(str0) * 600000;
        return retLong;
    }


    public static interface TimeEntryListener {
        public void setTimeEntry(String timeString, long timeLong, int requestCode);
    }
}
