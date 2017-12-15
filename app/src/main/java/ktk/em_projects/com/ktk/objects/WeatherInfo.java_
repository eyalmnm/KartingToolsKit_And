package ktk.em_projects.com.ktk.objects;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ktk.em_projects.com.ktk.utils.JSONUtils;

/**
 * Created by E M on 21/01/2015.
 */
public class WeatherInfo {

    private static final String TAG = "WeatherInfo";

    private int mintempF;
    private int mintempC;
    private int maxtempC;
    private int maxtempF;
    private int uvIndex;
    private JSONArray hourly;
    private ArrayList<HourlyWeatherInfo> hourlyWeatherInfos = new ArrayList<HourlyWeatherInfo>();

    public WeatherInfo(JSONObject weatherInfoObject) {
        Log.d(TAG, weatherInfoObject.toString());

        mintempF = JSONUtils.getIntValue(weatherInfoObject, "mintempF");
        mintempC = JSONUtils.getIntValue(weatherInfoObject, "mintempC");
        maxtempC = JSONUtils.getIntValue(weatherInfoObject, "maxtempC");
        maxtempF = JSONUtils.getIntValue(weatherInfoObject, "maxtempF");
        uvIndex = JSONUtils.getIntValue(weatherInfoObject, "uvIndex");
        hourly = JSONUtils.getJsonArray(weatherInfoObject, "hourly");
        for (int i = 0; i < hourly.length(); i++) {
            try {
                HourlyWeatherInfo hourlyWeatherInfo = new HourlyWeatherInfo(hourly.getJSONObject(i));
                hourlyWeatherInfos.add(hourlyWeatherInfo);
            } catch (JSONException e) {
                Log.e(TAG, "WeatherInfo", e);
            }
        }
    }

    public int getMintempF() {
        return mintempF;
    }

    public int getMintempC() {
        return mintempC;
    }

    public int getMaxtempC() {
        return maxtempC;
    }

    public int getMaxtempF() {
        return maxtempF;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public ArrayList<HourlyWeatherInfo> getHourlyWeatherInfos() {
        return hourlyWeatherInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherInfo that = (WeatherInfo) o;

        if (maxtempC != that.maxtempC) return false;
        if (maxtempF != that.maxtempF) return false;
        if (mintempC != that.mintempC) return false;
        if (mintempF != that.mintempF) return false;
        if (uvIndex != that.uvIndex) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mintempF;
        result = 31 * result + mintempC;
        result = 31 * result + maxtempC;
        result = 31 * result + maxtempF;
        result = 31 * result + uvIndex;
        return result;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "mintempF=" + mintempF +
                ", mintempC=" + mintempC +
                ", maxtempC=" + maxtempC +
                ", maxtempF=" + maxtempF +
                ", uvIndex=" + uvIndex +
                '}';
    }
}
