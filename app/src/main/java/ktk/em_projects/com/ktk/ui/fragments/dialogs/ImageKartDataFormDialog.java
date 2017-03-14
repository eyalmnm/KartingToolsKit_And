package ktk.em_projects.com.ktk.ui.fragments.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import ktk.em_projects.com.ktk.R;
import ktk.em_projects.com.ktk.utils.ImageUtils;

/**
 * Created by eyalmuchtar on 02/03/2017.
 */

// Ref: https://developer.android.com/training/gestures/scale.html
// Ref: http://stackoverflow.com/questions/3866499/two-directional-scroll-view
// Ref: http://web.archive.org/web/20131020193237/http://blog.gorges.us/2010/06/android-two-dimensional-scrollview


public class ImageKartDataFormDialog extends DialogFragment {

    private static final String TAG = "ImageKartDataFormDialog";

    // UI Components
    private ImageView dataMapImageView;
    private DataMapClickListener listener;

    @Override
    public void onAttach(Context context) {
        try {
            listener = (DataMapClickListener) context;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach", e);
            throw new ClassCastException(e.getMessage());
        }
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Handle dialog size
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Dialog myDialog = this.getDialog();
        myDialog.getWindow().setLayout(width, height);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Remove dialog title
        Dialog myDialog = this.getDialog();
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.image_kart_data_form_dialog_layout, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        dataMapImageView = (ImageView) view.findViewById(R.id.dataMapImageView);
        dataMapImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_BUTTON_RELEASE == event.getAction()) {
                    if (null != listener) {
                        int x = (int) event.getX();   // PIXELS
                        int y = (int) event.getY();   // PIXELS
                        listener.onMapClick(ImageUtils.pxToDp(x), ImageUtils.pxToDp(y));
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public interface DataMapClickListener {
        public void onMapClick(int xDpi, int yDpi);
    }
}
