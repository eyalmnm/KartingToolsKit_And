package ktk.em_projects.com.ktk.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ktk.em_projects.com.ktk.objects.HallOfFameHolder;

public class HallOfFameDbHandler {

    private static final String TAG = "HallOfFameDbHandler";
    private static HallOfFameDbHandler instance;
    private HallOfFameDbHelper dbHelper;

    private HallOfFameDbHandler(Context context) {
        Log.d(TAG, "HallOfFameDbHandler");
        dbHelper = new HallOfFameDbHelper(context, HallOfFameDbConstatnts.DATABASE_NAME, null, HallOfFameDbConstatnts.DATABASE_VERSION);
        dbHelper.getWritableDatabase();
    }

    public static HallOfFameDbHandler getInstance(Context context) {
        if (instance == null) {
            instance = new HallOfFameDbHandler(context);
        }
        return instance;
    }

    public Cursor getAllHallOfFameRecords() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(HallOfFameDbConstatnts.TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    public Cursor getHallOfFameByTrack(String trackName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(HallOfFameDbConstatnts.TABLE_NAME, null, HallOfFameDbConstatnts.HOF_TRACK_NAME + "=?", new String[]{trackName}, null, null, null);
        return cursor;
    }

    public Cursor getHallOfFameByBestLapTime(String trackName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(HallOfFameDbConstatnts.TABLE_NAME, null, HallOfFameDbConstatnts.HOF_TRACK_NAME + "=?", new String[]{trackName}, null, null, HallOfFameDbConstatnts.HOF_BEST_LAP_TIME);
        return cursor;
    }

    public boolean addHallOfFameRecord(HallOfFameHolder holder) {
        boolean createSuccessful = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HallOfFameDbConstatnts.HOF_TRACK_NAME, holder.getmTrackName());
        values.put(HallOfFameDbConstatnts.HOF_DATE_TIME, holder.getmDateTime().getTime());
        values.put(HallOfFameDbConstatnts.HOF_DATE_STR, holder.getmDateStr());
        values.put(HallOfFameDbConstatnts.HOF_TIME_STR, holder.getmTimeStr());
        values.put(HallOfFameDbConstatnts.HOF_TOTAL_RUN_TIME, holder.getmTotalRunTime());
        values.put(HallOfFameDbConstatnts.HOF_TOTAL_RUN_TIME_STR, holder.getmTotalRunTimeStr());
        values.put(HallOfFameDbConstatnts.HOF_BEST_LAP_TIME, holder.getmBestLapTime());
        values.put(HallOfFameDbConstatnts.HOF_BEST_LAP_TIME_STR, holder.getmBestLapTimeStr());
        values.put(HallOfFameDbConstatnts.HOF_NUM_OF_LAPS, holder.getmNumOfLaps());
        values.put(HallOfFameDbConstatnts.HOF_TYRES_TYPE, holder.getmTyresType());
        values.put(HallOfFameDbConstatnts.HOF_TRACK_WEATHER, holder.getmTrackWeather());
        values.put(HallOfFameDbConstatnts.HOF_DRY_TRACK, holder.ismDryTrack() ? HallOfFameDbConstatnts.HOF_TRUE : HallOfFameDbConstatnts.HOF_FALSE);
        values.put(HallOfFameDbConstatnts.HOF_GEAR_RATIO, holder.getmGearRatio());
        values.put(HallOfFameDbConstatnts.HOF_TRACK_TEMP, holder.getmTrackTemperature());
        values.put(HallOfFameDbConstatnts.HOF_AIR_TEMP, holder.getmAirTemperature());
        values.put(HallOfFameDbConstatnts.HOF_PEAK_RPM, holder.getmPeakRPM());
        values.put(HallOfFameDbConstatnts.HOF_SR_JETTING, holder.getmSrJetting());
        // Kart Setup
        values.put(HallOfFameDbConstatnts.HOF_TOE_RIGHT, holder.getmToeRight());
        values.put(HallOfFameDbConstatnts.HOF_TOE_LEFT, holder.getmToeLeft());
        values.put(HallOfFameDbConstatnts.HOF_CAMBER_CASTER_RIGHT, holder.getmCamberCasterRight());
        values.put(HallOfFameDbConstatnts.HOF_CAMBER_CASTER_LEFT, holder.getmCamberCasterLeft());
        values.put(HallOfFameDbConstatnts.HOF_FRONT_SPACING_RIGHT, holder.getmFrontSpacingRight());
        values.put(HallOfFameDbConstatnts.HOF_FRONT_SPACING_LEFT, holder.getmFrontSpacingLeft());
        values.put(HallOfFameDbConstatnts.HOF_REAR_SPACING_RIGHT, holder.getmRearSpacingRight());
        values.put(HallOfFameDbConstatnts.HOF_REAR_SPACING_LEFT, holder.getmRearSpacingLeft());
        values.put(HallOfFameDbConstatnts.HOF_SPROCKET_SIZE, holder.getmSprocketSize());
        values.put(HallOfFameDbConstatnts.HOF_RIM_SIZE_FRONT, holder.getmRimSizeFront());
        values.put(HallOfFameDbConstatnts.HOF_RIM_SIZE_REAR, holder.getmRimSizeRear());
        values.put(HallOfFameDbConstatnts.HOF_TYRE_SIZE_FRONT, holder.getmTyreSizeFront());
        values.put(HallOfFameDbConstatnts.HOF_TYRE_SIZE_REAR, holder.getmTyreSizeRear());
        values.put(HallOfFameDbConstatnts.HOF_TYRE_PRESSURE_FRONT, holder.getmTyrePressureFront());
        values.put(HallOfFameDbConstatnts.HOF_TYRE_PRESSURE_REAR, holder.getmTyrePressureRear());
        values.put(HallOfFameDbConstatnts.HOF_BALLAST_RIGHT, holder.getmBallastRight());
        values.put(HallOfFameDbConstatnts.HOF_BALLAST_LEFT, holder.getmBallastLeft());
        values.put(HallOfFameDbConstatnts.HOF_STIFFENER_BAR, holder.getmStiffenerBar());
        values.put(HallOfFameDbConstatnts.HOF_SEAT_POSITION_TYPE, holder.getmSeatPositionType());
        createSuccessful = db.insert(HallOfFameDbConstatnts.TABLE_NAME, null, values) > 0;
        db.close();
        return createSuccessful;
    }

    public Cursor getHallFoFameAllTracks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(HallOfFameDbConstatnts.TABLE_NAME, new String[]{HallOfFameDbConstatnts.TABLE_NAME}, null, null, null, null, null);
        return cursor;
    }
}
