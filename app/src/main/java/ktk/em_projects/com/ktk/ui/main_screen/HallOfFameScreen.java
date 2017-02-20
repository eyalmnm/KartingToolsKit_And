package ktk.em_projects.com.ktk.ui.main_screen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;

import ktk.em_projects.com.ktk.R;
import ktk.em_projects.com.ktk.objects.HallOfFameHolder;
import ktk.em_projects.com.ktk.storage.HallOfFameDbConstatnts;
import ktk.em_projects.com.ktk.storage.HallOfFameDbHandler;
import ktk.em_projects.com.ktk.storage.HallOfFameDbHelper;
import ktk.em_projects.com.ktk.ui.fragments.dialogs.HallOfFameDetailDialog;
import ktk.em_projects.com.ktk.ui.main_screen.hall_of_fame.HallOfFameEventCreator;

// http://www.opgenorth.net/blog/2011/09/06/using-autocompletetextview-and-simplecursoradapter-2/
// http://www.mysamplecode.com/2011/10/android-autocompletetextview.html
// http://www.mekya.com/2013/09/17/how-to-use-autocompletetextview-and-simplecursoradapter-together-in-android/
// http://stackoverflow.com/questions/5376860/how-to-get-string-from-selected-item-of-simplecursoradapter

public class HallOfFameScreen extends Activity {

    private static final String TAG = "HallOfFameScreen";

    private AutoCompleteTextView trackNameAutoCompleteTextView;
    private ListView hofListView;
    private ImageView emptyListImageView;
    private Button addButton;

    private ArrayList<HallOfFameHolder> holders = new ArrayList<HallOfFameHolder>();
    private HallOfFameAdapter hofAdapter;

    private ArrayList<String> tracksNames = new ArrayList<String>();
    private HallOfFameDbHelper dbHelper;
    @SuppressWarnings("rawtypes")
    private ArrayAdapter adapter;

    private Context context;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hall_of_fame_screen);
        Log.d(TAG, "onCreate");
        context = this;

        dbHelper = new HallOfFameDbHelper(context);

        trackNameAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.trackNameAutoCompleteTextView);
        hofListView = (ListView) findViewById(R.id.hofListView);
        emptyListImageView = (ImageView) findViewById(R.id.emptyListImageView);
        addButton = (Button) findViewById(R.id.addButton);

        hofAdapter = new HallOfFameAdapter();
        hofListView.setAdapter(hofAdapter);
        hofListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HallOfFameDetailDialog dialog = new HallOfFameDetailDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", holders.get(position));
                dialog.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                dialog.show(manager, "HallOfFameDetailDialog");
            }
        });
        addButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,
                        HallOfFameEventCreator.class);
                startActivity(intent);
            }
        });

//		String[] tracksNamesTemp = new String[]{"USA", "UK", "NZ", "AUS", "CND"};
        adapter = new ArrayAdapter(this, R.layout.simple_list_item_1, tracksNames); // tracksNamesTemp);
        trackNameAutoCompleteTextView.setAdapter(adapter);
        trackNameAutoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String trackName = tracksNames.get(position);
                repopulatList(trackName);
            }
        });

//		trackNameAutoCompleteTextView
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					@Override
//					public void onItemSelected(AdapterView<?> parent,
//							View view, int position, long id) {
//						String trackName = tracksNames.get(position);
//						repopulatList(trackName);
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> parent) {
//					}
//				});
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = getAllHallOfFames();
        holders = initHoldersList(cursor);
        if (holders == null || holders.size() == 0) {
            hofListView.setVisibility(View.GONE);
            emptyListImageView.setVisibility(View.VISIBLE);
        } else {
            hofListView.setVisibility(View.VISIBLE);
            emptyListImageView.setVisibility(View.GONE);
        }
        hofAdapter.notifyDataSetChanged();
        initTracksNamesArray();
        adapter.notifyDataSetChanged();
    }

    private void initTracksNamesArray() {
        StringBuilder sb = new StringBuilder("SELECT distinct "
                + HallOfFameDbConstatnts.HOF_TRACK_NAME
                + " FROM "
                + HallOfFameDbConstatnts.TABLE_NAME);
        sb.append(";");
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sb.toString(), null);
        tracksNames.clear();
        if (cursor.moveToFirst()) {
            do {
                String trackName = cursor.getString(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_TRACK_NAME));
                Log.d(TAG, "Track Name: " + trackName);
                tracksNames.add(trackName);
            } while (cursor.moveToNext());
        }
    }


    private void repopulatList(String trackName) {
        Cursor cursor = getByTrackName(trackName);
        holders = initHoldersList(cursor);
        hofAdapter.notifyDataSetChanged();
        if (holders == null || holders.size() == 0) {
            hofListView.setVisibility(View.GONE);
            emptyListImageView.setVisibility(View.VISIBLE);
        } else {
            hofListView.setVisibility(View.VISIBLE);
            emptyListImageView.setVisibility(View.GONE);
        }
    }

    private Cursor getAllHallOfFames() {
        return HallOfFameDbHandler.getInstance(context)
                .getAllHallOfFameRecords();
    }

    private Cursor getByTrackName(String trackName) {
        return HallOfFameDbHandler.getInstance(context).getHallOfFameByTrack(
                trackName);
    }

    private ArrayList<HallOfFameHolder> initHoldersList(Cursor cursor) {
        ArrayList<HallOfFameHolder> arrayList = new ArrayList<HallOfFameHolder>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor
                        .getColumnIndex(HallOfFameDbConstatnts.HOF_ID));
                String trackName = cursor.getString(cursor
                        .getColumnIndex(HallOfFameDbConstatnts.HOF_TRACK_NAME));
                Date dateTime = new Date(cursor.getLong(cursor
                        .getColumnIndex(HallOfFameDbConstatnts.HOF_DATE_TIME)));
                String timeStr = cursor.getString(cursor
                        .getColumnIndex(HallOfFameDbConstatnts.HOF_TIME_STR));
                String dateStr = cursor.getString(cursor
                        .getColumnIndex(HallOfFameDbConstatnts.HOF_DATE_STR));
                long totalRunTime = cursor
                        .getLong(cursor
                                .getColumnIndex(HallOfFameDbConstatnts.HOF_TOTAL_RUN_TIME));
                String totalRunTimeStr = cursor
                        .getString(cursor
                                .getColumnIndex(HallOfFameDbConstatnts.HOF_TOTAL_RUN_TIME_STR));
                int numOfLaps = cursor
                        .getInt(cursor
                                .getColumnIndex(HallOfFameDbConstatnts.HOF_NUM_OF_LAPS));
                long bestLapTime = cursor
                        .getLong(cursor
                                .getColumnIndex(HallOfFameDbConstatnts.HOF_BEST_LAP_TIME));
                String bestLapTimeStr = cursor
                        .getString(cursor
                                .getColumnIndex(HallOfFameDbConstatnts.HOF_BEST_LAP_TIME_STR));
                boolean dryTrack = HallOfFameDbConstatnts.HOF_TRUE == cursor
                        .getInt(cursor
                                .getColumnIndex(HallOfFameDbConstatnts.HOF_DRY_TRACK));
                float gearRatio = cursor.getFloat(cursor
                        .getColumnIndex(HallOfFameDbConstatnts.HOF_GEAR_RATIO));
                int srJetting = cursor.getInt(cursor
                        .getColumnIndex(HallOfFameDbConstatnts.HOF_SR_JETTING));
                String tyresType = cursor.getString(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_TYRES_TYPE));
                String trackWeather = cursor.getString(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_TRACK_WEATHER));
                float trackTemperature = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_TRACK_TEMP));
                float airTemperature = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_AIR_TEMP));
                int peakRPM = cursor.getInt(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_PEAK_RPM));
                float toeRight = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_TOE_RIGHT));
                float toeLeft = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_TOE_LEFT));
                float camberCasterRight = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_CAMBER_CASTER_RIGHT));
                float camberCasterLeft = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_CAMBER_CASTER_LEFT));
                float mFrontSpacingRight = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_FRONT_SPACING_RIGHT));
                float frontSpacingLeft = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_FRONT_SPACING_LEFT));
                float rearSpacingRight = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_REAR_SPACING_RIGHT));
                float rearSpacingLeft = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_REAR_SPACING_LEFT));
                int sprocketSize = cursor.getInt(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_SPROCKET_SIZE));
                float rimSizeFront = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_RIM_SIZE_FRONT));
                float rimSizeRear = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_RIM_SIZE_REAR));
                float tyreSizeFront = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_TYRE_SIZE_FRONT));
                float tyreSizeRear = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_TYRE_SIZE_REAR));
                float tyrePressureFront = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_TYRE_PRESSURE_FRONT));
                float tyrePressureRear = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_TYRE_PRESSURE_REAR));
                float ballastRight = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_BALLAST_RIGHT));
                float ballastLeft = cursor.getFloat(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_BALLAST_LEFT));
                String stiffenerBar = cursor.getString(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_STIFFENER_BAR));
                String seatPositionType = cursor.getString(cursor.getColumnIndex(HallOfFameDbConstatnts.HOF_SEAT_POSITION_TYPE));

                HallOfFameHolder holder = new HallOfFameHolder();
                holder.setData(id, trackName, dateTime, timeStr, dateStr,
                        totalRunTime, totalRunTimeStr, numOfLaps, bestLapTime,
                        bestLapTimeStr, dryTrack, gearRatio, srJetting, tyresType, trackWeather,
                        trackTemperature, airTemperature, peakRPM, toeRight, toeLeft, camberCasterRight,
                        camberCasterLeft, mFrontSpacingRight, frontSpacingLeft, rearSpacingRight,
                        rearSpacingLeft, sprocketSize, rimSizeFront, rimSizeRear, tyreSizeFront,
                        tyreSizeRear, tyrePressureFront, tyrePressureRear, ballastRight, ballastLeft,
                        stiffenerBar, seatPositionType);
                arrayList.add(holder);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    private class HallOfFameAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return holders.size();
        }

        @Override
        public Object getItem(int position) {
            return holders.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater;
                inflater = LayoutInflater.from(context);
                view = inflater.inflate(R.layout.hall_of_fame_list_item, null);
            }
            if (position % 2 == 0) {
                view.setBackgroundColor(Color.rgb(255, 255, 255));
            } else {
                view.setBackgroundColor(Color.rgb(231, 249, 255));
            }
            TextView trackNameTextView = (TextView) view
                    .findViewById(R.id.trackNameTextView);
            TextView dateTextView = (TextView) view
                    .findViewById(R.id.dateTextView);
            TextView timeTextView = (TextView) view
                    .findViewById(R.id.timeTextView);
            TextView bestLapTextView = (TextView) view
                    .findViewById(R.id.bestLapTextView);

            HallOfFameHolder holder = holders.get(position);
            trackNameTextView.setText(holder.getmTrackName());
            dateTextView.setText(holder.getmDateStr());
            timeTextView.setText(holder.getmTimeStr());
            bestLapTextView.setText(holder.getmBestLapTimeStr());
            ;
            return view;
        }
    }
}