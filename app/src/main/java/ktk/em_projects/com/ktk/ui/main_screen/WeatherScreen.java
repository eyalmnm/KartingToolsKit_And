package ktk.em_projects.com.ktk.ui.main_screen;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ktk.em_projects.com.ktk.R;
import ktk.em_projects.com.ktk.config.Constants;
import ktk.em_projects.com.ktk.objects.weather.HourlyWeatherInfo;
import ktk.em_projects.com.ktk.ui.widgets.smoothprogressbar.SmoothProgressBar;
import ktk.em_projects.com.ktk.ui.widgets.viewpagerindicator.CirclePageIndicator;
import ktk.em_projects.com.ktk.utils.DistanceCalculator;
import ktk.em_projects.com.ktk.utils.JSONUtils;
import ktk.em_projects.com.ktk.utils.LocationUtils;
import ktk.em_projects.com.ktk.utils.NumberUtils;
import ktk.em_projects.com.ktk.utils.PreferncesUtil;
import ktk.em_projects.com.ktk.utils.StringUtils;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by E M on 19/01/2015.
 */

// world weather online & www.apixu.com Pwd
// Y2VYR*gD

// http://stackoverflow.com/questions/6690402/android-string-format-price
// https://guides.codepath.com/android/Implementing-a-Horizontal-ListView-Guide

// http://stackoverflow.com/questions/17960315/importing-google-play-services-lib-into-intellij-idea-12-and-13
// http://wptrafficanalyzer.in/blog/gps-and-google-map-in-android-applications-series/

// http://stackoverflow.com/questions/15425283/how-to-get-weather-at-current-location
// http://www.worldweatheronline.com/api/docs/local-city-town-weather-api.aspx
// http://api.worldweatheronline.com/free/v2/weather.ashx?key=xxxxxxxxxxxxx&q=48.85,2.35&num_of_days=2&tp=1&format=json
// Key= d92ab390772883f427af7737698a7
// http://api.worldweatheronline.com/free/v2/weather.ashx?key=d92ab390772883f427af7737698a7&q=48.85,2.35&num_of_days=2&tp=1&format=json

// http://stackoverflow.com/questions/24399294/android-asynctask-to-make-an-http-get-request

// http://androidtrainningcenter.blogspot.co.il/2012/10/viewpager-example-in-android.html
// http://viewpagerindicator.com/

// http://www.calculateme.com/Length/Meters/ToFeet.htm

// For GPS status Icon
// http://stackoverflow.com/questions/7164630/how-to-change-shape-color-dynamically

// Get Google Map
// http://stackoverflow.com/questions/31371865/replace-getmap-with-getmapasync

// Get Location Permissions
// http://stackoverflow.com/questions/33327984/call-requires-permissions-that-may-be-rejected-by-user
// http://stackoverflow.com/questions/32491960/android-check-permission-for-locationmanager


// TODO  android how to switch between terrain and sattelite in Google map fragments
// http://wptrafficanalyzer.in/blog/google-map-android-api-v2-switching-between-normal-view-satellite-view-and-terrain-view/
// http://www.google.com/search?client=ms-android-samsung&source=android-home&site=webhp&source=hp&ei=hyPBVIG-F9PbaI_ZgbgG&q=android+how+to+switch+between+terrain+and+sattelite+in+Google+map+fragments&oq=android+how+to+switch+between+terrain+and+sattelite+in+Google+map+fragments&gs_l=mobile-gws-hp.3..30i10.5790.133675.0.135868.85.60.7.18.18.1.2502.33082.2-7j17j15j5j3j2j2j3.54.0.msedr...0...1c.1.61.mobile-gws-hp..23.62.26259.3.WeGebrVFwQI&rlz=1Y1XIUG_iwIL602IL603
// TODO  android how to switch between terrain and sattelite in Google map fragments

// For problems
// Ref: http://stackoverflow.com/questions/38323120/android-cannot-resolve-method-getmap

// Stop Location Listener
// Ref: http://stackoverflow.com/questions/6894234/stop-location-listener-in-android


public class WeatherScreen extends Activity implements OnMapReadyCallback {

    private static final String TAG = "WeatherScreen";

    /* GPS Constant Permission */
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    private static final int PERMISSION_REQUEST_CODE = 200;

    /* Position */
    private static final int MINIMUM_TIME = 10000;  // 10s
    private static final int MINIMUM_DISTANCE = 50; // 50m
    private static final long HOURS_IN_MILIS = 3600 * 1000;

    private SmoothProgressBar smoothProgressbar;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private TextView altitudeTextView;

    private Button terrainButton;
    private Button satteliteButton;
    private Button normalButton;

    private ViewPager weatherViewPager;
    private WeatherListAdapter weatherItemsAdapter;
    private ArrayList<HourlyWeatherInfo> hourluWeatherItemsArrayList = new ArrayList<HourlyWeatherInfo>();
    private CirclePageIndicator indicator;


    private MapFragment fm;
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private String provider;
    private double lng = 0;
    private double lat = 0;
    private double alt = 0;
    private String addressStr;
    private LocationListener locationListener;
    private long lastConnectionTimeMillis;

    private Context context;

    public static int getJetValue(int pressure, float tempC) {
        int thePressure = NumberUtils.roundToNext5(pressure);
        // int[][] myArray = new int[rows][cols];
        int index = (thePressure - 930) / 5;
        try {
            return Constants.ROTAX_FR125_MAX_JET_TUNING_CHART.VALUES[index][(int) tempC];
        } catch (Throwable tr) {
            Log.e(TAG, "getJetValue pressure: " + pressure + " thePressure: " + thePressure + " index: " + index + " tempC: " + tempC, tr);
        }
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_screen);
        Log.d(TAG, "onCreate");

        context = this;

        initLocationListener();

        smoothProgressbar = (SmoothProgressBar) findViewById(R.id.smoothProgressbar);

        fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        // Getting reference to google map
        // http://stackoverflow.com/questions/31371865/replace-getmap-with-getmapasync
//        googleMap = fm.getMap();
        fm.getMapAsync(this);

        latitudeTextView = (TextView) findViewById(R.id.latitudeTextView);
        longitudeTextView = (TextView) findViewById(R.id.longitudeTextView);
        altitudeTextView = (TextView) findViewById(R.id.altitudeTextView);

        terrainButton = (Button) findViewById(R.id.terrainButton);
        satteliteButton = (Button) findViewById(R.id.satteliteButton);
        normalButton = (Button) findViewById(R.id.normalButton);

        weatherItemsAdapter = new WeatherListAdapter();
        weatherViewPager = (ViewPager) findViewById(R.id.weatherViewPager);
        weatherViewPager.setAdapter(weatherItemsAdapter);

        //Bind the title indicator to the adapter
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(weatherViewPager);
        indicator.setSnap(true);

        terrainButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }
        });
        satteliteButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });
        normalButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
            if (!checkPermissions()) {
                requestPermission();
            } else {
                initLocationComponents();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkPermissions() {
        int locationCoarse = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int locationFine = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);

        return locationCoarse == PackageManager.PERMISSION_GRANTED && locationFine == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_COARSE_LOCATION,
                ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean corseLocationRes = grantResults[8] == PackageManager.PERMISSION_GRANTED;
                    boolean fineLocationRes = grantResults[9] == PackageManager.PERMISSION_GRANTED;
                    if (corseLocationRes && fineLocationRes) {
                        initLocationComponents();
                    } else {
                        Toast.makeText(context, "Permission Denied, You cannot access the application.", Toast.LENGTH_LONG).show();
                        if (!hasPermissions(context, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)) {
                            Toast.makeText(context, "You need to allow access to all the permissions", Toast.LENGTH_LONG).show();
                            requestPermission();
                        }
                    }
                }
        }
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void initLocationComponents() {
        initLocation();
        initCurrentLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setMyLocation(lat, lng);
    }


    @SuppressLint("MissingPermission")
    private void initCurrentLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        Location locations = getLastKnownLocation(); // locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {
            lng = locations.getLongitude();
            lat = locations.getLatitude();
            alt = locations.getAltitude();
        }

        if (false == StringUtils.isNullOrEmpty(provider)) {
            locationManager.requestLocationUpdates(provider, MINIMUM_TIME, MINIMUM_DISTANCE, locationListener);
            try {
                if (locationListener != null) {
                    locationManager.requestLocationUpdates(provider, 400l, 1f,
                            locationListener);
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER, 10 * 1000, (float) 10.0,
                            locationListener);
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 90 * 1000, (float) 10.0,
                            locationListener);
                }
            } catch (SecurityException e) {
                dialogGPS(this); // lets the user know there is a problem with the gps
            }
        } else {
            dialogGPS(this); // lets the user know there is a problem with the gps
        }

        initWeather(new LatLng(lat, lng));
        setMyLocation(lat, lng);
    }

    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    private void setMyLocation(double latitude, double longitude) {
        if (googleMap == null)
            return;

        smoothProgressbar.setVisibility(View.VISIBLE);

        LatLng position = new LatLng(latitude, longitude);

        // Convert the new location to real address.
        new ReverseGeoCodingTask(context).execute(position);

        // Clean the old marker
        googleMap.clear();

        // Instantiating MarkerOptions class
        MarkerOptions options = new MarkerOptions();

        // Setting position for the MarkerOptions
        options.position(position);

        // Setting title for the MarkerOptions
        options.title("Me");
        options.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED));

        // Setting snippet for the MarkerOptions
        if (!StringUtils.isNullOrEmpty(addressStr)) {
            options.snippet(addressStr);
        }

        // Adding Marker on the Google Map
        googleMap.addMarker(options);

        // Creating CameraUpdate object for position
        CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(position);

        // Creating CameraUpdate object for zoom
        CameraUpdate updateZoom = CameraUpdateFactory.zoomBy(3);

        // Updating the camera position to the user input latitude and longitude
        googleMap.moveCamera(updatePosition);

        // Applying zoom to the marker position
        googleMap.animateCamera(updateZoom);

        smoothProgressbar.setVisibility(View.GONE);
    }

    private void initLocationListener() {
        locationListener = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                String[] statuses = {"OUT_OF_SERVICE", " TEMPORARILY_UNAVAILABLE", "AVAILABLE"};
                Toast.makeText(context, "onStatusChanged status: " + statuses[status], Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(context, "onProviderEnabled", Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(context, "onProviderDisabled", Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                double alt = location.getAltitude();
                setMyLocation(lat, lng);
                double distance = DistanceCalculator.distance(WeatherScreen.this.lat, WeatherScreen.this.lng, lat, lng, "K");
                long timeDiff = System.currentTimeMillis() - lastConnectionTimeMillis;
                if (10 < distance && HOURS_IN_MILIS < timeDiff) {
                    WeatherScreen.this.alt = alt;
                    WeatherScreen.this.lat = lat;
                    WeatherScreen.this.lng = lng;
                    latitudeTextView.setText("Lat: " + StringUtils.convertDoubleToFormat(lat));
                    longitudeTextView.setText("Lng: " + StringUtils.convertDoubleToFormat(lng));
                    altitudeTextView.setText("Alt: " + (int) alt + "m/" + ((int) LocationUtils.convertMeterToFeet((float) alt)) + "ft");
                    setMyLocation(lat, lng);
                    Log.d(TAG, "onLocationChanged lat: " + lat + " lng: " + lng + " alt: " + alt);
                    initWeather(new LatLng(lat, lng));
                }
            }
        };
    }

    private void initWeather(LatLng latLng) {
        long lastUpdate = PreferncesUtil.getLastUpdate(this);
        double lastLat = PreferncesUtil.getLatitude(this);
        double lastLng = PreferncesUtil.getLongitude(this);
        final String lastWeather = PreferncesUtil.getWeather(this);
        if (StringUtils.isNullOrEmpty(lastWeather)) {
            updateWeather(latLng);
        } else if ((System.currentTimeMillis() - lastUpdate) > Constants.HOUR_MILLIS) {
            updateWeather(latLng);
        } else if (LocationUtils.distanceKm(lastLat, lastLng, latLng.latitude, latLng.longitude) > 5) {
            updateWeather(latLng);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        parseWeather(lastWeather);
                    } catch (JSONException e) {
                        Log.e(TAG, "initWeather", e);
                    }
                }
            });
        }
    }

    private void parseWeather(String allWeatherJsonStr) throws JSONException {
        if (false == StringUtils.isNullOrEmpty(allWeatherJsonStr)) {
            hourluWeatherItemsArrayList.clear();
            JSONObject rootJsonObject = new JSONObject(allWeatherJsonStr);

            // Get the number of hourly weather object
            int counter = JSONUtils.getIntValue(rootJsonObject, "cnt");
            if (0 < counter) {
                JSONArray listJsonArray = JSONUtils.getJsonArray(rootJsonObject, "list");
                for (int i = 0; i < counter; i++) {
                    JSONObject hourlyJsonObject = (JSONObject) listJsonArray.get(i);
                    parseHourlyWeather(hourlyJsonObject);
                }
            }
            if (null != weatherItemsAdapter) {
                weatherItemsAdapter.notifyDataSetChanged();
            }
        }
    }

    private void parseHourlyWeather(JSONObject rootObject) throws JSONException {
        float tempK;
        int humidity;
        float pressure;
        String weatherIcon;
        String description;
        float windSpeedMPSec;
        int windDirDegree;
        String timeUtcStr;

        int windSpeedKmph;

        JSONObject mainJsonObject = JSONUtils.getJSONObjectValue(rootObject, "main");
        tempK = JSONUtils.getFloatValue(mainJsonObject, "temp");
        pressure = JSONUtils.getFloatValue(mainJsonObject, "pressure");
        humidity = JSONUtils.getIntValue(mainJsonObject, "humidity");

        JSONArray weatherJsonArray = JSONUtils.getJsonArray(rootObject, "weather");
        JSONObject weatherItem = (JSONObject) weatherJsonArray.get(0);
        description = JSONUtils.getStringValue(weatherItem, "description");
        weatherIcon = JSONUtils.getStringValue(weatherItem, "icon");

        JSONObject windJsonObject = JSONUtils.getJSONObjectValue(rootObject, "wind");
        windSpeedMPSec = JSONUtils.getFloatValue(windJsonObject, "speed");
        windDirDegree = JSONUtils.getIntValue(windJsonObject, "deg");

        timeUtcStr = JSONUtils.getStringValue(rootObject, "dt_txt");

        // Conventions
        windSpeedKmph = (int) (windSpeedMPSec * 3600 / 1000);
        float tempC = tempK - 272.15F;

        hourluWeatherItemsArrayList.add(new HourlyWeatherInfo(tempC, weatherIcon, timeUtcStr, pressure, windSpeedKmph, windDirDegree, humidity, description));
        if (null != weatherItemsAdapter) {
            weatherItemsAdapter.notifyDataSetChanged();
        }
    }


    private void updateWeather(LatLng latLng) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                smoothProgressbar.setVisibility(View.VISIBLE);
            }
        });

        new WeatherAsyncTask().execute(formatWeatherRequest(latLng));
    }

    // http://api.worldweatheronline.com/free/v2/weather.ashx?key=d92ab390772883f427af7737698a7&q=48.85,2.35&num_of_days=2&tp=1&format=json
    // Forecast weather - api.openweathermap.org/data/2.5/forecast?lat=35&lon=139
    // Current weather - api.openweathermap.org/data/2.5/weather?lat=35&lon=139
    private String formatWeatherRequest(LatLng latLng) {
        StringBuilder requestBuilder = new StringBuilder(Constants.OPEN_WEATHER_MAP.OPEN_WEATHER_URL);
        requestBuilder.append("?");
        requestBuilder.append("lat=" + latLng.latitude);
        requestBuilder.append("&lon=" + latLng.longitude);
        requestBuilder.append("&cnt=8"); // 24 hours
        requestBuilder.append("&APPID=" + Constants.OPEN_WEATHER_MAP.APP_ID);
        String request = requestBuilder.toString();
        Log.d(TAG, "formatWeatherRequest: " + request);
        return request;
    }

    private void initLocation() {
        try {
            ((LocationManager) getSystemService(Context.LOCATION_SERVICE))
                    .requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0l,
                            0f, locationListener);
        } catch (SecurityException e) {
            dialogGPS(this); // lets the user know there is a problem with the gps
        }
    }

    private void dialogGPS(Context context) {
        AlertDialog gpsDialog = new AlertDialog.Builder(context).create();
        gpsDialog.setTitle("GPS");
        gpsDialog.setMessage("GPS problem occurs. Please turn on your GPS.");
        gpsDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
//        gpsDialog.show();
    }

    // Ref: http://stackoverflow.com/questions/6894234/stop-location-listener-in-android
    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
        locationListener = null;
        locationManager = null;
    }

    private class ReverseGeoCodingTask extends AsyncTask<LatLng, Void, String> {
        Context mContext;

        public ReverseGeoCodingTask(Context context) {
            super();
            mContext = context;
        }

        // Finding address using reverse geocoding
        @Override
        protected String doInBackground(LatLng... params) {
            Geocoder geocoder = new Geocoder(mContext);
            double latitude = params[0].latitude;
            double longitude = params[0].longitude;
            // latLng = new LatLng(latitude, longitude);

            List<Address> addresses = null;
            String addressText = "";

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);

                addressText = String.format(
                        "%s, %s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address
                                .getAddressLine(0) : "", address.getLocality(),
                        address.getCountryName());
            }

            return addressText;
        }

        @Override
        protected void onPostExecute(String addressText) {
            // Setting the title for the marker.
            // This will be displayed on taping the marker
            // markerOptions.title(addressText);
            addressStr = addressText;

            // Placing a marker on the touched position
            // googleMap.addMarker(markerOptions);
        }
    }


    class WeatherAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    lastConnectionTimeMillis = System.currentTimeMillis();
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    return data;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        protected void onPostExecute(String result) {
            smoothProgressbar.setVisibility(View.GONE);
            PreferncesUtil.setLastUpdate(context, System.currentTimeMillis());
            PreferncesUtil.setLatitude(context, lat);
            PreferncesUtil.setLongitude(context, lng);
            PreferncesUtil.setWeather(context, result);
            try {
                parseWeather(result);
            } catch (JSONException e) {
                Log.e(TAG, "WeatherAsyncTask onPostExecute", e);
            }
        }
    }

    private class WeatherListAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return hourluWeatherItemsArrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(View collection, int position) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.hour_weather_info_item, null);

            TextView cTempTextView = (TextView) view.findViewById(R.id.cTempTextView);
            TextView fTempTextView = (TextView) view.findViewById(R.id.fTempTextView);
            ImageView weatherIconImageView = (ImageView) view.findViewById(R.id.weatherIconImageView);
            TextView weatherDescription = (TextView) view.findViewById(R.id.weatherDescription);
            TextView timeTextView = (TextView) view.findViewById(R.id.timeTextView);
            TextView pressureTextView = (TextView) view.findViewById(R.id.pressureTextView);
            TextView windSpeedTextView = (TextView) view.findViewById(R.id.windSpeedTextView);
            TextView windDirTextView = (TextView) view.findViewById(R.id.windDirTextView);
            TextView jetTextView = (TextView) view.findViewById(R.id.jetTextView);

            HourlyWeatherInfo hourInfo = hourluWeatherItemsArrayList.get(position);
            cTempTextView.setText(String.valueOf((int) hourInfo.getTempC()));
            fTempTextView.setText(String.valueOf((int) hourInfo.getTempF()));
            Picasso.with(context)
                    .load(hourInfo.getWeatherIconUrlStr())
                    .into(weatherIconImageView);
            timeTextView.setText(hourInfo.getTime());
            weatherDescription.setText(String.valueOf(hourInfo.getDescription()));
            pressureTextView.setText(String.valueOf((int) hourInfo.getPressure()) + "mb");
            windSpeedTextView.setText(String.valueOf(hourInfo.getWindSpeedKmph()) + "Kph/" + String.valueOf(hourInfo.getWindSpeedMiles()) + "Mph");
            windDirTextView.setText(String.valueOf(hourInfo.getWindDirDegree()));
            int jetValue = getJetValue((int) hourInfo.getPressure(), hourInfo.getTempC());
            String jatValueStr = jetValue == 0 ? "Middle" : jetValue + "";
            jetTextView.setText(jatValueStr);
            ((ViewPager) collection).addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

}
