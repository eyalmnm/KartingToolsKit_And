package ktk.em_projects.com.ktk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

// http://www.jiahaoliuliu.com/2013/08/correcly-saving-double-in-shared.html

public class PreferncesUtil {

    private static final String PREF_NAME = "kart_tools_kit";
    private static final String LAST_UPD_KEY = "last_upd_key";
    private static final String LATITUDE_KEY = "latitude_key";
    private static final String LONGITUDE_KEY = "longitude_key";
    private static final String WEATHER_KEY = "weather_key";


    public static long getLastUpdate(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREF_NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getLong(LAST_UPD_KEY, 0);
    }

    public static void setLastUpdate(Context context, long lastUpdate) {
        Editor editor = context.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE).edit();
        editor.putLong(LAST_UPD_KEY, lastUpdate);
        editor.commit();
    }

    public static double getLatitude(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREF_NAME, Activity.MODE_PRIVATE);
        return Double.longBitsToDouble(sharedPreferences.getLong(LATITUDE_KEY, 0));
    }

    public static void setLatitude(Context context, double latitude) {
        Editor editor = context.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE).edit();
        editor.putLong(LATITUDE_KEY, Double.doubleToRawLongBits(latitude));
        editor.commit();
    }

    public static double getLongitude(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREF_NAME, Activity.MODE_PRIVATE);
        return Double.longBitsToDouble(sharedPreferences.getLong(LONGITUDE_KEY, 0));
    }

    public static void setLongitude(Context context, double longitude) {
        Editor editor = context.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE).edit();
        editor.putLong(LONGITUDE_KEY, Double.doubleToRawLongBits(longitude));
        editor.commit();
    }

    public static String getWeather(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREF_NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(WEATHER_KEY, "");
    }

    public static void setWeather(Context context, String weather) {
        Editor editor = context.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE).edit();
        editor.putString(WEATHER_KEY, weather);
        editor.commit();
    }

}
