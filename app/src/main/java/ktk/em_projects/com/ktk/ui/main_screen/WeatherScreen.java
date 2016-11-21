package ktk.em_projects.com.ktk.ui.main_screen;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

import ktk.em_projects.com.ktk.config.Constants;
import ktk.em_projects.com.ktk.objects.HourlyWeatherInfo;
import ktk.em_projects.com.ktk.objects.WeatherInfo;
import ktk.em_projects.com.ktk.ui.widgets.smoothprogressbar.SmoothProgressBar;
import ktk.em_projects.com.ktk.ui.widgets.viewpagerindicator.CirclePageIndicator;
import ktk.em_projects.com.ktk.utils.JSONUtils;
import ktk.em_projects.com.ktk.utils.LocationUtils;
import ktk.em_projects.com.ktk.utils.NumberUtils;
import ktk.em_projects.com.ktk.utils.PreferncesUtil;
import ktk.em_projects.com.ktk.utils.StringUtils;

/**
 * Created by E M on 19/01/2015.
 */

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


// TODO  android how to switch between terrain and sattelite in Google map fragments
// http://wptrafficanalyzer.in/blog/google-map-android-api-v2-switching-between-normal-view-satellite-view-and-terrain-view/
// http://www.google.com/search?client=ms-android-samsung&source=android-home&site=webhp&source=hp&ei=hyPBVIG-F9PbaI_ZgbgG&q=android+how+to+switch+between+terrain+and+sattelite+in+Google+map+fragments&oq=android+how+to+switch+between+terrain+and+sattelite+in+Google+map+fragments&gs_l=mobile-gws-hp.3..30i10.5790.133675.0.135868.85.60.7.18.18.1.2502.33082.2-7j17j15j5j3j2j2j3.54.0.msedr...0...1c.1.61.mobile-gws-hp..23.62.26259.3.WeGebrVFwQI&rlz=1Y1XIUG_iwIL602IL603
// TODO  android how to switch between terrain and sattelite in Google map fragments


public class WeatherScreen extends Activity {

    private static final String TAG = "WeatherScreen";

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

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_screen);
        Log.d(TAG, "onCreate");

        context = this;

        EasyTracker.getInstance(this).activityStart(this);

        initLocationListener();

        smoothProgressbar = (SmoothProgressBar) findViewById(R.id.smoothProgressbar);

        initLocationProvider();
        fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        // Getting reference to google map
        // http://stackoverflow.com/questions/31371865/replace-getmap-with-getmapasync
        googleMap = fm.getMap();

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

        initLocation();
    }

    @Override
    public void onStart() {
        super.onStart();
        initLocationProvider();
    }

    @Override
    public void onResume() {
        super.onResume();
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
//        smoothProgressbar.setVisibility(View.GONE);
    }

    private void initLocationProvider() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (provider == null)
            return;
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            alt = location.getAltitude();
            setMyLocation(location.getLatitude(), location.getLongitude());
        }
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
        CameraUpdate updateZoom = CameraUpdateFactory.zoomBy(6);

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
                lat = location.getLatitude();
                lng = location.getLongitude();
                alt = location.getAltitude();
                latitudeTextView.setText("Lat: " + StringUtils.convertDoubleToFormat(lat));
                longitudeTextView.setText("Lng: " + StringUtils.convertDoubleToFormat(lng));
                altitudeTextView.setText("Alt: " + (int) alt + "m/" + ((int) LocationUtils.convertMeterToFeet((float) alt)) + "ft");
                setMyLocation(lat, lng);
                Log.d(TAG, "onLocationChanged lat: " + lat + " lng: " + lng + " alt: " + alt);
                initWeather(new LatLng(lat, lng));
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
                    parseData(lastWeather);
                }
            });
        }
    }

    private void parseData(String weatherData) {
        try {
            JSONObject allWeatherObject = new JSONObject(weatherData);
            JSONObject dataJsonObject = JSONUtils.getJSONObjectValue(allWeatherObject, "data");
            JSONArray weatherInfoArray = JSONUtils.getJsonArray(dataJsonObject, "weather");
            ArrayList<WeatherInfo> tempWeatherInfo = new ArrayList<WeatherInfo>();
            for (int i = 0; i < weatherInfoArray.length(); i++) {
                tempWeatherInfo.add(new WeatherInfo(weatherInfoArray.getJSONObject(i)));
            }

            hourluWeatherItemsArrayList.clear();
            hourluWeatherItemsArrayList.addAll(tempWeatherInfo.get(0).getHourlyWeatherInfos());
            weatherItemsAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Log.e(TAG, "initWeather", e);
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
    private String formatWeatherRequest(LatLng latLng) {
        StringBuilder stringBuilder = new StringBuilder(Constants.WORLD_WEATHER_ONLINE.URL);
        stringBuilder.append("?key=" + Constants.WORLD_WEATHER_ONLINE.KEY);
        stringBuilder.append("&q=" + latLng.latitude + "," + latLng.longitude);
        stringBuilder.append("&num_of_days=" + 1);
        stringBuilder.append("&tp=" + 1);
        stringBuilder.append("&format=" + "json");
        String request = stringBuilder.toString();
        Log.d(TAG, "formatWeatherRequest: " + request);
        return request;
    }

    private void initLocation() {
        ((LocationManager) getSystemService(Context.LOCATION_SERVICE))
                .requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0l,
                        0f, locationListener);
    }

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
    protected void onDestroy() {
        super.onDestroy();
        locationListener = null;
        locationManager = null;
        EasyTracker.getInstance(this).activityStart(this);
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
                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
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
            parseData(result);
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
            TextView timeTextView = (TextView) view.findViewById(R.id.timeTextView);
            TextView pressureTextView = (TextView) view.findViewById(R.id.pressureTextView);
            TextView windSpeedTextView = (TextView) view.findViewById(R.id.windSpeedTextView);
            TextView windDirTextView = (TextView) view.findViewById(R.id.windDirTextView);
            TextView jetTextView = (TextView) view.findViewById(R.id.jetTextView);

            HourlyWeatherInfo hourInfo = hourluWeatherItemsArrayList.get(position);
            cTempTextView.setText(String.valueOf(hourInfo.getTempC()));
            fTempTextView.setText(String.valueOf(hourInfo.getTempF()));
            if (hourInfo.getWeatherIconIcon() != null) {
                weatherIconImageView.setImageBitmap(hourInfo.getWeatherIconIcon());
            } else {
                hourInfo.loadImage(this);
            }
            timeTextView.setText(hourInfo.getTime());
            pressureTextView.setText(hourInfo.getPressure() + "mb");
            windSpeedTextView.setText(hourInfo.getWindspeedKmph() + "Kph/" + hourInfo.getWindspeedMiles() + "Mph");
            windDirTextView.setText(hourInfo.getWinddirDegree() + "/" + hourInfo.getWinddir16Point());
            int jetValue = getJetValue(hourInfo.getPressure(), hourInfo.getTempC());
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
