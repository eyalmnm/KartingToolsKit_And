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
public class SprintFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sprint_fragment, container, false);

        Button pushAtCornerEntryButton = (Button) view.findViewById(R.id.pushAtCornerEntryButton);
        Button pushAtCornerExitButton = (Button) view.findViewById(R.id.pushAtCornerExitButton);
        Button looseAtCornerEntryButton = (Button) view.findViewById(R.id.looseAtCornerEntryButton);
        Button looseAtCornerExitButton = (Button) view.findViewById(R.id.looseAtCornerExitButton);

        pushAtCornerEntryButton.setOnClickListener(this);
        pushAtCornerExitButton.setOnClickListener(this);
        looseAtCornerEntryButton.setOnClickListener(this);
        looseAtCornerExitButton.setOnClickListener(this);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View view) {
        Bundle args = new Bundle();
        boolean isCheckListButton = false;
        if (view.getId() == R.id.pushAtCornerEntryButton) {
            args.putInt("layoutId", R.layout.sprint_push_at_corner_entry_dialog);
            isCheckListButton = true;
        } else if (view.getId() == R.id.pushAtCornerExitButton) {
            args.putInt("layoutId", R.layout.sprint_push_at_corner_exit_dialog);
            isCheckListButton = true;
        } else if (view.getId() == R.id.looseAtCornerEntryButton) {
            args.putInt("layoutId", R.layout.sprint_loose_at_corner_entry_dialog);
            isCheckListButton = true;
        } else if (view.getId() == R.id.looseAtCornerExitButton) {
            args.putInt("layoutId", R.layout.sprint_loose_at_corner_exit_dialog);
            isCheckListButton = true;
        }
        if (true == isCheckListButton) {
            CheckListDialog checkListDialog = new CheckListDialog();
            checkListDialog.setArguments(args);
            checkListDialog.show(getChildFragmentManager(), "CheckListDialog");
        }
    }

}
