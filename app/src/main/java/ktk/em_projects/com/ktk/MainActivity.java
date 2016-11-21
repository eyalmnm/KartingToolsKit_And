package ktk.em_projects.com.ktk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.analytics.tracking.android.EasyTracker;

import ktk.em_projects.com.ktk.ui.main_screen.CheckListScreen;
import ktk.em_projects.com.ktk.ui.main_screen.DriveTrackingScreen;
import ktk.em_projects.com.ktk.ui.main_screen.FlagsScreen;
import ktk.em_projects.com.ktk.ui.main_screen.FuelOilMixScreen;
import ktk.em_projects.com.ktk.ui.main_screen.GearRatioScreen;
import ktk.em_projects.com.ktk.ui.main_screen.HallOfFameScreen;
import ktk.em_projects.com.ktk.ui.main_screen.StopwatchScreen;
import ktk.em_projects.com.ktk.ui.main_screen.WeatherScreen;

// Weather
// http://stackoverflow.com/questions/15425283/how-to-get-weather-at-current-location
// http://www.worldweatheronline.com/api/docs/local-city-town-weather-api.aspx
// http://api.worldweatheronline.com/free/v2/weather.ashx?key=xxxxxxxxxxxxx&q=48.85,2.35&num_of_days=2&tp=1&format=json
// Key= d92ab390772883f427af7737698a7
// http://api.worldweatheronline.com/free/v2/weather.ashx?key=d92ab390772883f427af7737698a7&q=48.85,2.35&num_of_days=2&tp=1&format=json

// Flags
// https://psgka.wordpress.com/getting-starting-in-karting/flag-meanings/

// Google Analytics
// https://developers.google.com/analytics/devguides/collection/android/v3/?hl=iw

// Realm DataBase
// http://realm.io/
// http://realm.io/docs/java/0.78.0/

public class MainActivity extends Activity {

	private ImageButton checkListImageButton;
	private ImageButton weatherImageButton;
	private ImageButton stopWatchImageButton;

	private ImageButton fuelImageButton;
	private ImageButton gearImageButton;
	private ImageButton flagsImageButton;

	private ImageButton hallOfFameImageButton;
	private ImageButton driveTrackingImageButton;
	private ImageButton onMapImageButton;

	private ImageButton settingsImageButton;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		context = this;

		EasyTracker.getInstance(context).activityStart(this);

		checkListImageButton = (ImageButton) findViewById(R.id.checkListImageButton);
		weatherImageButton = (ImageButton) findViewById(R.id.weatherImageButton);
		stopWatchImageButton = (ImageButton) findViewById(R.id.stopWatchImageButton);

		fuelImageButton = (ImageButton) findViewById(R.id.fuelImageButton);
		gearImageButton = (ImageButton) findViewById(R.id.gearImageButton);
		flagsImageButton = (ImageButton) findViewById(R.id.flagsImageButton);

		hallOfFameImageButton = (ImageButton) findViewById(R.id.hallOfFameImageButton);
		driveTrackingImageButton = (ImageButton) findViewById(R.id.driveTrackingImageButton);
		onMapImageButton = (ImageButton) findViewById(R.id.onMapImageButton);

		settingsImageButton = (ImageButton) findViewById(R.id.settingsImageButton);

		checkListImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context, CheckListScreen.class);
				startActivity(intent);
				overridePendingTransition(R.layout.fadein, R.layout.fadeout);
			}
		});
		weatherImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context, WeatherScreen.class);
				startActivity(intent);
				overridePendingTransition(R.layout.fadein, R.layout.fadeout);
			}
		});
		stopWatchImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context, StopwatchScreen.class);
				startActivity(intent);
				overridePendingTransition(R.layout.fadein, R.layout.fadeout);
			}
		});
		fuelImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context, FuelOilMixScreen.class);
				startActivity(intent);
				overridePendingTransition(R.layout.fadein, R.layout.fadeout);
			}
		});
		gearImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context, GearRatioScreen.class);
				startActivity(intent);
				overridePendingTransition(R.layout.fadein, R.layout.fadeout);
			}
		});
		flagsImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context, FlagsScreen.class);
				startActivity(intent);
				overridePendingTransition(R.layout.fadein, R.layout.fadeout);
			}
		});
		hallOfFameImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context, HallOfFameScreen.class); // HallOfFameEventCreator.class);
				startActivity(intent);
				overridePendingTransition(R.layout.fadein, R.layout.fadeout);
			}
		});
		driveTrackingImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context, DriveTrackingScreen.class);
				startActivity(intent);
				overridePendingTransition(R.layout.fadein, R.layout.fadeout);
			}
		});
		onMapImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO implement this method.
			}
		});
		settingsImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO implement this method.
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EasyTracker.getInstance(context).activityStop(this);
	}
}
