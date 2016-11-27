package ktk.em_projects.com.ktk.ui.fragments.dialogs;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.google.analytics.tracking.android.EasyTracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ktk.em_projects.com.ktk.R;
import ktk.em_projects.com.ktk.objects.HallOfFameHolder;
import ktk.em_projects.com.ktk.storage.HallOfFameDbHandler;
import ktk.em_projects.com.ktk.ui.fragments.TimeEntryFragment;
import ktk.em_projects.com.ktk.ui.widgets.smoothprogressbar.SmoothProgressBar;
import ktk.em_projects.com.ktk.utils.StringUtils;

/**
 * Created by E M on 28/01/2015.
 */

public class HallOfFameEventCreator extends Activity implements
		TimeEntryFragment.TimeEntryListener {

	private static final String TAG = "HallOfFameEventCreator";

	private static final String TIME_PATTERN = "HH:mm";
	private static final String DATE_PATTERN = "yyyy-MM-dd";

	private static final int TOTAL_RUN_TIME_REQUEST_CODE = 10;
	private static final int BEST_LAP_TIME_REQUEST_CODE = 20;

	private Calendar runCalendar;
	private long totalRunTimeLong;
	private long bestLapTimeLong;
	private String totalRunTimeStr;
	private String bestLapTimeStr;

	private SmoothProgressBar smoothProgressbar;
	private EditText trackNameEditText;
	private TextView dateTextView;
	private TextView timeTextView;
	private TextView totalRunTimeTextView;
	private TextView bestLapTimeTextView;
	private EditText totalLapsEditText;
	private RadioGroup trackConditionRadioGroup;
	private EditText gearRatioEditText;
	private EditText rsJettingEditText;
	private ImageButton okImageButton;
	// private Button cancelButton;

	private boolean trackIsDry = true;

	private SimpleDateFormat timeFormat;
	private SimpleDateFormat dateFormat;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hall_of_fame_event_creator);
		Log.d(TAG, "onCreate");
		EasyTracker.getInstance(this).activityStart(this);
		context = this;

		runCalendar = Calendar.getInstance();

		smoothProgressbar = (SmoothProgressBar) findViewById(R.id.smoothProgressbar);
		trackNameEditText = (EditText) findViewById(R.id.trackNameEditText);
		dateTextView = (TextView) findViewById(R.id.dateTextView);
		timeTextView = (TextView) findViewById(R.id.timeTextView);
		totalRunTimeTextView = (TextView) findViewById(R.id.totalRunTimeTextView);
		bestLapTimeTextView = (TextView) findViewById(R.id.bestLapTimeTextView);
		totalLapsEditText = (EditText) findViewById(R.id.totalLapsEditText);
		trackConditionRadioGroup = (RadioGroup) findViewById(R.id.trackConditionRadioGroup);
		gearRatioEditText = (EditText) findViewById(R.id.gearRatioEditText);
		rsJettingEditText = (EditText) findViewById(R.id.rsJettingEditText);
		okImageButton = (ImageButton) findViewById(R.id.okImageButton);

		timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());
		dateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());

		// updateTimes();

		trackConditionRadioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.dryRadioButton) {
							trackIsDry = true;
						} else {
							trackIsDry = false;
						}
					}
				});

		okImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				smoothProgressbar.setVisibility(View.VISIBLE);
				if (storeRun()) {
					finish();
				}
				smoothProgressbar.setVisibility(View.GONE);
			}
		});

		dateTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog.newInstance(
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePickerDialog dialog,
									int year, int monthOfYear, int dayOfMonth) {
								runCalendar.set(year, monthOfYear, dayOfMonth);
								updateTimes();
							}
						}, runCalendar.get(Calendar.YEAR),
						runCalendar.get(Calendar.MONTH),
						runCalendar.get(Calendar.DAY_OF_MONTH)).show(
						getFragmentManager(), "datePicker");

			}
		});

		timeTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TimePickerDialog.newInstance(
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(RadialPickerLayout view,
									int hourOfDay, int minute) {
								runCalendar
										.set(Calendar.HOUR_OF_DAY, hourOfDay);
								runCalendar.set(Calendar.MINUTE, minute);
								updateTimes();
							}
						}, runCalendar.get(Calendar.HOUR_OF_DAY),
						runCalendar.get(Calendar.MINUTE), true).show(
						getFragmentManager(), "timePicker");
			}
		});

		totalRunTimeTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getFragmentManager();
				TimeEntryFragment fragment = new TimeEntryFragment();
				fragment.setRequestCode(TOTAL_RUN_TIME_REQUEST_CODE);
				fragment.show(fragmentManager, "TimeEntryFragment");
			}
		});

		bestLapTimeTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getFragmentManager();
				TimeEntryFragment fragment = new TimeEntryFragment();
				fragment.setRequestCode(BEST_LAP_TIME_REQUEST_CODE);
				fragment.show(fragmentManager, "TimeEntryFragment");
			}
		});
	}

	private void updateTimes() {
		dateTextView.setText(dateFormat.format(runCalendar.getTime()));
		timeTextView.setText(timeFormat.format(runCalendar.getTime()));
	}

	private boolean storeRun() {
		String trackName = trackNameEditText.getText().toString();
		if (StringUtils.isNullOrEmpty(trackName)) {
			Toast.makeText(context, "Track name can not be empty",
					Toast.LENGTH_LONG).show();
			return false;
		}
		Date dateTime = runCalendar.getTime();
		String dateStr = dateTextView.getText().toString();
		if (StringUtils.isNullOrEmpty(dateStr)
				|| dateStr.equals(getString(R.string.run_date))) {
			Toast.makeText(context, "Date can not be empty", Toast.LENGTH_LONG)
					.show();
			return false;
		}
		String timeStr = timeTextView.getText().toString();
		if (StringUtils.isNullOrEmpty(timeStr)
				|| timeStr.equals(getString(R.string.run_time))) {
			Toast.makeText(context, "Time can not be empty", Toast.LENGTH_LONG)
					.show();
			return false;
		}
		long totalRunTime = totalRunTimeLong;
		String totalRunTimeStr = this.totalRunTimeStr;
		if (StringUtils.isNullOrEmpty(totalRunTimeStr)) {
			Toast.makeText(context, "Total Run Time can not be empty",
					Toast.LENGTH_LONG).show();
			return false;
		}
		int numOfLaps = -1;
		try {
			numOfLaps = Integer
					.parseInt(totalLapsEditText.getText().toString());
		} catch (Exception e) {
		}
		if (numOfLaps == -1) {
			Toast.makeText(context, "Num of Laps can not be empty",
					Toast.LENGTH_LONG).show();
			return false;
		}
		long bestLapTime = bestLapTimeLong;
		String bestLapTimeStr = this.bestLapTimeStr;
		if (StringUtils.isNullOrEmpty(bestLapTimeStr)) {
			Toast.makeText(context, "Best Lap Time can not be empty",
					Toast.LENGTH_LONG).show();
			return false;
		}
		boolean dryTrack = trackIsDry;
		float gearRatio = 0F;
		try {
			gearRatio = Float
					.parseFloat(gearRatioEditText.getText().toString());
		} catch (Exception e) {
		}
		int srJetting = -1;
		try {
			srJetting = Integer
					.parseInt(rsJettingEditText.getText().toString());
		} catch (Exception e) {
		}
		smoothProgressbar.setVisibility(View.VISIBLE);
		HallOfFameHolder holder = new HallOfFameHolder();

		holder.setData(trackName, dateTime, timeStr, dateStr, totalRunTime, totalRunTimeStr, numOfLaps,
				bestLapTime, bestLapTimeStr, dryTrack, gearRatio, srJetting, tyresType, trackWeather,
				trackTemperature, airTemperature, peakRPM, toeRight, toeLeft, camberCasterRight,
				camberCasterLeft, mFrontSpacingRight, frontSpacingLeft, rearSpacingRight, rearSpacingLeft,
				sprocketSize, rimSizeFront, rimSizeRear, tyreSizeFront, tyreSizeRear, tyrePressureFront,
				tyrePressureRear, ballastRight, ballastLeft, stiffenerBar, seatPositionType);

//		holder.setData(trackName, dateTime, timeStr, dateStr, totalRunTime, totalRunTimeStr, numOfLaps, bestLapTime, bestLapTimeStr, dryTrack, gearRatio, srJetting);
		boolean success = HallOfFameDbHandler.getInstance(context).addHallOfFameRecord(holder);		
		smoothProgressbar.setVisibility(View.GONE);
		if(success) {
			Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
		}
		return success;
//		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EasyTracker.getInstance(this).activityStop(this);
	}

	@Override
	public void setTimeEntry(String timeString, long timeLong, int requestCode) {
		if (requestCode == TOTAL_RUN_TIME_REQUEST_CODE) {
			totalRunTimeTextView.setText(timeString);
			totalRunTimeLong = timeLong;
			totalRunTimeStr = timeString;
		} else if (requestCode == BEST_LAP_TIME_REQUEST_CODE) {
			bestLapTimeTextView.setText(timeString);
			bestLapTimeLong = timeLong;
			bestLapTimeStr = timeString;
		}
	}
}
