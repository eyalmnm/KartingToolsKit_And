package ktk.em_projects.com.ktk.storage;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// http://stackoverflow.com/questions/6791852/android-sqliteopenhelper-oncreate-method-is-not-called-why
public class HallOfFameDbHelper extends SQLiteOpenHelper {
	
	private static final String TAG = "HallOfFameDbHelper";

	public HallOfFameDbHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		Log.d(TAG, "HallOfFameDbHelper");
	}

	public HallOfFameDbHelper(Context context) {
		super(context, HallOfFameDbConstatnts.DATABASE_NAME, null, HallOfFameDbConstatnts.DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate");
		String createHofTable = "CREATE TABLE if not exists " + HallOfFameDbConstatnts.TABLE_NAME + 
				"(" + HallOfFameDbConstatnts.HOF_ID + " INTEGER PRIMARY KEY autoincrement," +
				HallOfFameDbConstatnts.HOF_TRACK_NAME + " TEXT," +
				HallOfFameDbConstatnts.HOF_DATE_TIME + " INTEGER," +
				HallOfFameDbConstatnts.HOF_TIME_STR + " TAXT," +
				HallOfFameDbConstatnts.HOF_DATE_STR + " TEXT," +
				HallOfFameDbConstatnts.HOF_TOTAL_RUN_TIME + " INTEGER," +
				HallOfFameDbConstatnts.HOF_TOTAL_RUN_TIME_STR + " TEXT," +
				HallOfFameDbConstatnts.HOF_NUM_OF_LAPS + " INTEGER," +
				HallOfFameDbConstatnts.HOF_BEST_LAP_TIME + " INTEGER," +
				HallOfFameDbConstatnts.HOF_BEST_LAP_TIME_STR + " TEXT," +
				HallOfFameDbConstatnts.HOF_DRY_TRACK + " INTEGER," +
				HallOfFameDbConstatnts.HOF_GEAR_RATIO + " REAL," +
				HallOfFameDbConstatnts.HOF_SR_JETTING + " INTEGER)";
		try {
			db.execSQL(createHofTable);
		} catch (SQLException e) {
			Log.e(TAG, "onCreate", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion+ " to "+ newVersion+ ", which will destroy all old date");
		db.execSQL("DROP TABLE IF EXISTS "+ HallOfFameDbConstatnts.TABLE_NAME);
		onCreate(db);
	}

}
