package ktk.em_projects.com.ktk.ui.main_screen;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.analytics.tracking.android.EasyTracker;

import ktk.em_projects.com.ktk.R;
import ktk.em_projects.com.ktk.ui.fragments.dialogs.CheckListDialog;

/**
 * Created by E M on 20/01/2015.
 */
public class CheckListScreen extends Activity implements View.OnClickListener {

    private static final String TAG = "CheckListScreen";

    // Main Layouts
    private View pavedOvalButtonsContainer;
    private View dirtOvalButtonsContainer;
    private View sprintButtonsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_list_screen);
        Log.d(TAG, "onCreate");

        EasyTracker.getInstance(this).activityStart(this);

        Button pavedPushAtCornerEntryButton = (Button) findViewById(R.id.pavedPushAtCornerEntryButton);
        Button pavedPushAtCornerExitButton = (Button) findViewById(R.id.pavedPushAtCornerExitButton);
        Button pavedLooseAtCornerEntryButton = (Button) findViewById(R.id.pavedLooseAtCornerEntryButton);
        Button pavedLooseAtCornerExitButton = (Button) findViewById(R.id.pavedLooseAtCornerExitButton);

        Button dirtPushAtCornerEntryButton = (Button) findViewById(R.id.dirtPushAtCornerEntryButton);
        Button dirtPushAtCornerExitButton = (Button) findViewById(R.id.dirtPushAtCornerExitButton);
        Button dirtLooseAtCornerEntryButton = (Button) findViewById(R.id.dirtLooseAtCornerEntryButton);
        Button dirtLooseAtCornerExitButton = (Button) findViewById(R.id.dirtLooseAtCornerExitButton);
        Button dirtFourWheelDriftButton = (Button) findViewById(R.id.dirtFourWheelDriftButton);

        Button sprintPushAtCornerEntryButton = (Button) findViewById(R.id.sprintPushAtCornerEntryButton);
        Button sprintPushAtCornerExitButton = (Button) findViewById(R.id.sprintPushAtCornerExitButton);
        Button sprintLooseAtCornerEntryButton = (Button) findViewById(R.id.sprintLooseAtCornerEntryButton);
        Button sprintLooseAtCornerExitButton = (Button) findViewById(R.id.sprintLooseAtCornerExitButton);

        pavedPushAtCornerEntryButton.setOnClickListener(this);
        pavedPushAtCornerExitButton.setOnClickListener(this);
        pavedLooseAtCornerEntryButton.setOnClickListener(this);
        pavedLooseAtCornerExitButton.setOnClickListener(this);

        dirtPushAtCornerEntryButton.setOnClickListener(this);
        dirtPushAtCornerExitButton.setOnClickListener(this);
        dirtLooseAtCornerEntryButton.setOnClickListener(this);
        dirtLooseAtCornerExitButton.setOnClickListener(this);
        dirtFourWheelDriftButton.setOnClickListener(this);

        sprintPushAtCornerEntryButton.setOnClickListener(this);
        sprintPushAtCornerExitButton.setOnClickListener(this);
        sprintLooseAtCornerEntryButton.setOnClickListener(this);
        sprintLooseAtCornerExitButton.setOnClickListener(this);

        pavedOvalButtonsContainer = findViewById(R.id.pavedOvalButtonsContainer);
        dirtOvalButtonsContainer = findViewById(R.id.dirtOvalButtonsContainer);
        sprintButtonsContainer = findViewById(R.id.sprintButtonsContainer);

        Button pavedOvalButton = (Button) findViewById(R.id.pavedOvalButton);
        Button dirtOvalButton = (Button) findViewById(R.id.dirtOvalButton);
        Button sprintButton = (Button) findViewById(R.id.sprintButton);

        pavedOvalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retractAll();
                pavedOvalButtonsContainer.setVisibility(View.VISIBLE);
            }
        });
        dirtOvalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retractAll();
                dirtOvalButtonsContainer.setVisibility(View.VISIBLE);
            }
        });
        sprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retractAll();
                sprintButtonsContainer.setVisibility(View.VISIBLE);
            }
        });
        retractAll();
    }

    private void retractAll() {
        pavedOvalButtonsContainer.setVisibility(View.GONE);
        dirtOvalButtonsContainer.setVisibility(View.GONE);
        sprintButtonsContainer.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        Bundle args = new Bundle();
        boolean isCheckListButton = false;
        if (v.getId() == R.id.pavedPushAtCornerEntryButton) {
            isCheckListButton = true;
            args.putInt("layoutId", R.layout.paved_push_at_corner_entry_dialog);
        } else if (v.getId() == R.id.pavedPushAtCornerExitButton) {
            isCheckListButton = true;
            args.putInt("layoutId", R.layout.paved_push_at_corner_exit_dialog);
        } else if (v.getId() == R.id.pavedLooseAtCornerEntryButton) {
            isCheckListButton = true;
            args.putInt("layoutId", R.layout.paved_loose_at_corner_entry_dialog);
        } else if (v.getId() == R.id.pavedLooseAtCornerExitButton) {
            isCheckListButton = true;
            args.putInt("layoutId", R.layout.paved_loose_at_corner_exit_dialog);
        } else if (v.getId() == R.id.dirtPushAtCornerEntryButton) {
            isCheckListButton = true;
            args.putInt("layoutId", R.layout.dirt_push_at_corner_entry_dialog);
        } else if (v.getId() == R.id.dirtPushAtCornerExitButton) {
            isCheckListButton = true;
            args.putInt("layoutId", R.layout.dirt_push_at_corner_exit_dialog);
        } else if (v.getId() == R.id.dirtLooseAtCornerEntryButton) {
            isCheckListButton = true;
            args.putInt("layoutId", R.layout.dirt_loose_at_corner_entry_dialog);
        } else if (v.getId() == R.id.dirtLooseAtCornerExitButton) {
            isCheckListButton = true;
            args.putInt("layoutId", R.layout.dirt_loose_at_corner_exit_dialog);
        } else if (v.getId() == R.id.dirtFourWheelDriftButton) {
            isCheckListButton = true;
            args.putInt("layoutId", R.layout.dirt_four_wheel_drift_dialog);
        } else if (v.getId() == R.id.sprintPushAtCornerEntryButton) {
            isCheckListButton = true;
            args.putInt("layoutId", R.layout.sprint_push_at_corner_entry_dialog);
        } else if (v.getId() == R.id.sprintPushAtCornerExitButton) {
            isCheckListButton = true;
            args.putInt("layoutId", R.layout.sprint_push_at_corner_exit_dialog);
        } else if (v.getId() == R.id.sprintLooseAtCornerEntryButton) {
            isCheckListButton = true;
            args.putInt("layoutId", R.layout.sprint_loose_at_corner_entry_dialog);
        } else if (v.getId() == R.id.sprintLooseAtCornerExitButton) {
            isCheckListButton = true;
            args.putInt("layoutId", R.layout.sprint_loose_at_corner_exit_dialog);
        }
        if (true == isCheckListButton) {
            CheckListDialog checkListDialog = new CheckListDialog();
            checkListDialog.setArguments(args);
            checkListDialog.show(getFragmentManager(), "CheckListDialog");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyTracker.getInstance(this).activityStop(this);
    }
}
