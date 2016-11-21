package ktk.em_projects.com.ktk.ui.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ktk.em_projects.com.ktk.R;
import ktk.em_projects.com.ktk.ui.fragments.dialogs.CheckListDialog;

/**
 * Created by E M on 18/01/2015.
 */
public class DirtOvalFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dirt_oval_fragment, container, false);

        Button pushAtCornerEntryButton = (Button) view.findViewById(R.id.pushAtCornerEntryButton);
        Button pushAtCornerExitButton = (Button) view.findViewById(R.id.pushAtCornerExitButton);
        Button looseAtCornerEntryButton = (Button) view.findViewById(R.id.looseAtCornerEntryButton);
        Button looseAtCornerExitButton = (Button) view.findViewById(R.id.looseAtCornerExitButton);
        Button fourWheelDriftButton = (Button) view.findViewById(R.id.fourWheelDriftButton);

        pushAtCornerEntryButton.setOnClickListener(this);
        pushAtCornerExitButton.setOnClickListener(this);
        looseAtCornerEntryButton.setOnClickListener(this);
        looseAtCornerExitButton.setOnClickListener(this);
        fourWheelDriftButton.setOnClickListener(this);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pushAtCornerEntryButton) {
            CheckListDialog checkListDialog = new CheckListDialog(R.layout.dirt_push_at_corner_entry_dialog);
            checkListDialog.show(getChildFragmentManager(), "pushAtCornerEntryButton");
        } else if (view.getId() == R.id.pushAtCornerExitButton) {
            CheckListDialog checkListDialog = new CheckListDialog(R.layout.dirt_push_at_corner_exit_dialog);
            checkListDialog.show(getChildFragmentManager(), "pushAtCornerExitButton");
        } else if (view.getId() == R.id.looseAtCornerEntryButton) {
            CheckListDialog checkListDialog = new CheckListDialog(R.layout.dirt_loose_at_corner_entry_dialog);
            checkListDialog.show(getChildFragmentManager(), "looseAtCornerEntryButton");
        } else if (view.getId() == R.id.looseAtCornerExitButton) {
            CheckListDialog checkListDialog = new CheckListDialog(R.layout.dirt_loose_at_corner_exit_dialog);
            checkListDialog.show(getChildFragmentManager(), "looseAtCornerExitButton");
        } else if (view.getId() == R.id.fourWheelDriftButton) {
            CheckListDialog checkListDialog = new CheckListDialog(R.layout.dirt_four_wheel_drift_dialog);
            checkListDialog.show(getChildFragmentManager(), "looseAtCornerExitButton");
        }
    }

}
