package ktk.em_projects.com.ktk.objects.weather;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.widget.BaseAdapter;

import ktk.em_projects.com.ktk.config.Constants;
import ktk.em_projects.com.ktk.utils.ImageUtils;
import ktk.em_projects.com.ktk.utils.StringUtils;

/**
 * Created by eyalmuchtar on 12/15/17.
 */

public class HourlyWeatherInfo {
    private static final String TAG = "HourlyWeatherInfo";

    private float tempC;
    private float tempF;
    private String weatherIcon;
    private String time;
    private float pressure;
    private float windSpeedKmph;
    private float windSpeedMiles;
    private int windDirDegree;

    private String weatherIconUrlStr;
    private Bitmap weatherIconIcon;

    private BaseAdapter baseAdapter;
    private PagerAdapter pagerAdapter;


    public HourlyWeatherInfo(float tempC, String weatherIcon, String time, float pressure, int windSpeedKmph, int windDirDegree) {
        this.tempC = tempC;
        tempF = 9 * (this.tempC / 5) + 32;
        this.weatherIcon = weatherIcon;
        this.time = time;
        this.pressure = pressure;
        this.windSpeedKmph = windSpeedKmph;
        windSpeedMiles = (int) (this.windSpeedKmph / 1.6);
        this.windDirDegree = windDirDegree;
        if (false == StringUtils.isNullOrEmpty(weatherIcon)) {
            weatherIconUrlStr = Constants.OPEN_WEATHER_MAP.ICON_URL + this.weatherIcon + ".png";
        }
    }

    public float getTempC() {
        return tempC;
    }

    public float getTempF() {
        return tempF;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getTime() {
        return time;
    }

    public float getPressure() {
        return pressure;
    }

    public float getWindSpeedKmph() {
        return windSpeedKmph;
    }

    public float getWindSpeedMiles() {
        return windSpeedMiles;
    }

    public int getWindDirDegree() {
        return windDirDegree;
    }

    public String getWeatherIconUrlStr() {
        return weatherIconUrlStr;
    }

    public Bitmap getWeatherIconIcon() {
        return weatherIconIcon;
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
                "tempC=" + tempC +
                ", tempF=" + tempF +
                ", weatherIcon='" + weatherIcon + '\'' +
                ", time='" + time + '\'' +
                ", pressure=" + pressure +
                ", windSpeedKmph=" + windSpeedKmph +
                ", windSpeedMiles=" + windSpeedMiles +
                ", windDirDegree=" + windDirDegree +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HourlyWeatherInfo that = (HourlyWeatherInfo) o;

        if (Float.compare(that.tempC, tempC) != 0) return false;
        if (Float.compare(that.tempF, tempF) != 0) return false;
        if (Float.compare(that.pressure, pressure) != 0) return false;
        if (Float.compare(that.windSpeedKmph, windSpeedKmph) != 0) return false;
        if (Float.compare(that.windSpeedMiles, windSpeedMiles) != 0) return false;
        if (windDirDegree != that.windDirDegree) return false;
        if (!weatherIcon.equals(that.weatherIcon)) return false;
        return time.equals(that.time);
    }

    @Override
    public int hashCode() {
        int result = (tempC != +0.0f ? Float.floatToIntBits(tempC) : 0);
        result = 31 * result + (tempF != +0.0f ? Float.floatToIntBits(tempF) : 0);
        result = 31 * result + weatherIcon.hashCode();
        result = 31 * result + time.hashCode();
        result = 31 * result + (pressure != +0.0f ? Float.floatToIntBits(pressure) : 0);
        result = 31 * result + (windSpeedKmph != +0.0f ? Float.floatToIntBits(windSpeedKmph) : 0);
        result = 31 * result + (windSpeedMiles != +0.0f ? Float.floatToIntBits(windSpeedMiles) : 0);
        result = 31 * result + windDirDegree;
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
