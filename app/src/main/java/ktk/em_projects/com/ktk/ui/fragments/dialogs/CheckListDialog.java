package ktk.em_projects.com.ktk.ui.fragments.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import ktk.em_projects.com.ktk.R;

// Passing data to fragment
// http://stackoverflow.com/questions/17436298/how-to-pass-a-variable-from-activity-to-fragment-and-pass-it-back

/**
 * Created by E M on 17/01/2015.
 */
public class CheckListDialog extends DialogFragment {

    private Button okButton;

    public CheckListDialog() {
    }

//    public CheckListDialog(int layoutId) {
//        this.layoutId = layoutId;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        int layoutId = args.getInt("layoutId");
        View view = inflater.inflate(layoutId, container, false);

        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);

        okButton = (Button) view.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }
}
