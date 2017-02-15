package ktk.em_projects.com.ktk.ui.fragments.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import ktk.em_projects.com.ktk.R;
import ktk.em_projects.com.ktk.objects.HallOfFameHolder;

public class HallOfFameDetailDialog extends DialogFragment {

    private HallOfFameHolder hallOfFameHolder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hallOfFameHolder = (HallOfFameHolder) getArguments().getSerializable("data");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // if (hallOfFameHolder == null) dismiss(); // TODO

        View view = inflater.inflate(R.layout.hall_of_fame_event_details, container, false);

        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);

        TextView trackNameTextView = (TextView) view.findViewById(R.id.trackNameTextView);
        TextView dateTextView = (TextView) view.findViewById(R.id.dateTextView);
        TextView timeTextView = (TextView) view.findViewById(R.id.timeTextView);
        TextView totalRunTimeTextView = (TextView) view.findViewById(R.id.totalRunTimeTextView);
        TextView bestLapTimeTextView = (TextView) view.findViewById(R.id.bestLapTimeTextView);
        TextView totalLapsEditText = (TextView) view.findViewById(R.id.totalLapsEditText);
        RadioButton dryRadioButton = (RadioButton) view.findViewById(R.id.dryRadioButton);
        RadioButton wetRadioButton = (RadioButton) view.findViewById(R.id.wetRadioButton);
        TextView gearRatioEditText = (TextView) view.findViewById(R.id.gearRatioEditText);
        TextView rsJettingEditText = (TextView) view.findViewById(R.id.rsJettingEditText);

        trackNameTextView.setText(hallOfFameHolder.getmTrackName());
        dateTextView.setText(hallOfFameHolder.getmDateStr());
        timeTextView.setText(hallOfFameHolder.getmTimeStr());
        totalRunTimeTextView.setText(hallOfFameHolder.getmTotalRunTimeStr());
        bestLapTimeTextView.setText(hallOfFameHolder.getmBestLapTimeStr());
        totalLapsEditText.setText(String.valueOf(hallOfFameHolder.getmNumOfLaps()));
        dryRadioButton.setChecked(hallOfFameHolder.ismDryTrack());
        wetRadioButton.setChecked(!hallOfFameHolder.ismDryTrack());
        gearRatioEditText.setText(String.valueOf(hallOfFameHolder.getmGearRatio()));
        rsJettingEditText.setText(String.valueOf(hallOfFameHolder.getmSrJetting()));

        return view;
    }
}
