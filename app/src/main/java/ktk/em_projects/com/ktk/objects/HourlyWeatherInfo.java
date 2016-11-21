package ktk.em_projects.com.ktk.objects;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.widget.BaseAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ktk.em_projects.com.ktk.utils.ImageUtils;
import ktk.em_projects.com.ktk.utils.JSONUtils;
import ktk.em_projects.com.ktk.utils.StringUtils;

/**
 * Created by E M on 21/01/2015.
 */
public class HourlyWeatherInfo {

    private static final String TAG = "HourlyWeatherInfo";

    //    private int chanceofremdry;
//    private int chanceofovercast;
//    private int visibility;
//    private float FeelsLikeC;
//    private int chanceofhightemp;
//    private int chanceofrain;
//    private int chanceoffog;
//    private int WindGustKmph;
//    private int HeatIndexF;
//    private int HeatIndexC;
    private String time;
    //    private float WindChillC;
//    private int chanceofsunshine;
//    private int WindGustMiles;
//    private int chanceofsnow;
//    private float WindChillF;
    private float tempC;
    //    private int cloudcover;
    private int pressure;
    private int windspeedMiles;
    //    private int chanceofwindy;
//    private float precipMM;
    private int winddirDegree;
    private String winddir16Point;
    private int humidity;
    //    private int chanceoffrost;
//    private float FeelsLikeF;
    private int windspeedKmph;
    //    private float DewPointC;
    private float tempF;
    //    private int chanceofthunder;
//    private String weatherCode;
//    private float DewPointF;
    private JSONArray weatherIconUrl;      // value
    private JSONArray weatherDesc;         // value

    private String weatherIconUrlStr;
    private Bitmap weatherIconIcon;         // 42X42dp
    private String weatherDescStr;

    private BaseAdapter baseAdapter;
    private PagerAdapter pagerAdapter;


    public HourlyWeatherInfo(JSONObject hourlyWeatherObject) {
        Log.d(TAG, hourlyWeatherObject.toString());
        int timeInt = JSONUtils.getIntValue(hourlyWeatherObject, "time");
        String minutes = String.valueOf((timeInt % 100) < 10 ? timeInt % 100 + "0" : timeInt % 100);
        time = (timeInt / 100) + ":" + (minutes);
        tempC = JSONUtils.getFloatValue(hourlyWeatherObject, "tempC");
        tempF = JSONUtils.getFloatValue(hourlyWeatherObject, "tempF");
        pressure = JSONUtils.getIntValue(hourlyWeatherObject, "pressure");
        humidity = JSONUtils.getIntValue(hourlyWeatherObject, "humidity");
        winddirDegree = JSONUtils.getIntValue(hourlyWeatherObject, "winddirDegree");
        winddir16Point = JSONUtils.getStringValue(hourlyWeatherObject, "winddir16Point");
        windspeedKmph = JSONUtils.getIntValue(hourlyWeatherObject, "windspeedKmph");
        windspeedMiles = JSONUtils.getIntValue(hourlyWeatherObject, "windspeedMiles");
        weatherIconUrl = JSONUtils.getJsonArray(hourlyWeatherObject, "weatherIconUrl");
        weatherDesc = JSONUtils.getJsonArray(hourlyWeatherObject, "weatherDesc");
        try {
            weatherDescStr = JSONUtils.getStringValue(weatherDesc.getJSONObject(0), "value");
        } catch (JSONException e) {
            Log.e(TAG, "HourlyWeatherInfo", e);
        }
        try {
            weatherIconUrlStr = JSONUtils.getStringValue(weatherIconUrl.getJSONObject(0), "value");
            weatherIconUrlStr = weatherIconUrlStr.replace("\\", "");
        } catch (JSONException e) {
            Log.e(TAG, "HourlyWeatherInfo", e);
        }
    }

    public int getWinddirDegree() {
        return winddirDegree;
    }

    public String getWinddir16Point() {
        return winddir16Point;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getWindspeedMiles() {
        return windspeedMiles;
    }

    public int getWindspeedKmph() {
        return windspeedKmph;
    }

    public String getTime() {
        return time;
    }

    public float getTempC() {
        return tempC;
    }

    public int getPressure() {
        return pressure;
    }

    public float getTempF() {
        return tempF;
    }

    public Bitmap getWeatherIconIcon() {
        return weatherIconIcon;
    }

    public String getWeatherDescStr() {
        return weatherDescStr;
    }

    public void loadImage(BaseAdapter baseAdapter) {
        this.baseAdapter = baseAdapter;
        if (!StringUtils.isNullOrEmpty(weatherIconUrlStr) && weatherIconIcon == null) {
            new ImageLoaderTask().execute(weatherIconUrlStr);
        }
    }

    public void loadImage(PagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
        if (!StringUtils.isNullOrEmpty(weatherIconUrlStr) && weatherIconIcon == null) {
            new ImageLoaderTask().execute(weatherIconUrlStr);
        }
    }

    @Override
    public String toString() {
        return "HourlyWeatherInfo{" +
                "time='" + time + '\'' +
                ", tempC=" + tempC +
                ", pressure=" + pressure +
                ", windspeedMiles=" + windspeedMiles +
                ", winddirDegree=" + winddirDegree +
                ", winddir16Point='" + winddir16Point + '\'' +
                ", humidity=" + humidity +
                ", windspeedKmph=" + windspeedKmph +
                ", tempF=" + tempF +
                ", weatherIconUrlStr='" + weatherIconUrlStr + '\'' +
                ", weatherDescStr='" + weatherDescStr + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HourlyWeatherInfo that = (HourlyWeatherInfo) o;

        if (humidity != that.humidity) return false;
        if (pressure != that.pressure) return false;
        if (Float.compare(that.tempC, tempC) != 0) return false;
        if (Float.compare(that.tempF, tempF) != 0) return false;
        if (winddirDegree != that.winddirDegree) return false;
        if (windspeedKmph != that.windspeedKmph) return false;
        if (windspeedMiles != that.windspeedMiles) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (weatherDescStr != null ? !weatherDescStr.equals(that.weatherDescStr) : that.weatherDescStr != null)
            return false;
        if (weatherIconUrlStr != null ? !weatherIconUrlStr.equals(that.weatherIconUrlStr) : that.weatherIconUrlStr != null)
            return false;
        if (winddir16Point != null ? !winddir16Point.equals(that.winddir16Point) : that.winddir16Point != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = time != null ? time.hashCode() : 0;
        result = 31 * result + (tempC != +0.0f ? Float.floatToIntBits(tempC) : 0);
        result = 31 * result + pressure;
        result = 31 * result + windspeedMiles;
        result = 31 * result + winddirDegree;
        result = 31 * result + (winddir16Point != null ? winddir16Point.hashCode() : 0);
        result = 31 * result + humidity;
        result = 31 * result + windspeedKmph;
        result = 31 * result + (tempF != +0.0f ? Float.floatToIntBits(tempF) : 0);
        result = 31 * result + (weatherIconUrlStr != null ? weatherIconUrlStr.hashCode() : 0);
        result = 31 * result + (weatherDescStr != null ? weatherDescStr.hashCode() : 0);
        return result;
    }

    private class ImageLoaderTask extends AsyncTask<String, String, Bitmap> {

        @Override
        protected void onPostExecute(Bitmap retBitmap) {
            if (retBitmap != null) {
                Log.i("ImageLoadTask", "Successfully loaded " + weatherIconUrlStr + " image");
                weatherIconIcon = retBitmap;
                if (baseAdapter != null) {
                    // WHEN IMAGE IS LOADED NOTIFY THE ADAPTER
                    baseAdapter.notifyDataSetChanged();
                }
                if (pagerAdapter != null) {
                    // WHEN IMAGE IS LOADED NOTIFY THE ADAPTER
                    pagerAdapter.notifyDataSetChanged();
                }
            } else {
                Log.e("ImageLoadTask", "Failed to load " + weatherIconUrlStr + " image");
            }
        }

        @Override
        protected void onPreExecute() {
            Log.i("ImageLoadTask", "Loading image...");
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.d("ImageLoadTask", "onProgressUpdate: values[0]=" + values[0]);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Log.i("ImageLoadTask", "Attempting to load image URL: " + strings[0]);
            try {
                Bitmap b = ImageUtils.getBitmapFromURL(strings[0]);
                return b;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
