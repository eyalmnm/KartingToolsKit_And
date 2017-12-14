package ktk.em_projects.com.ktk.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;


// http://stackoverflow.com/questions/2577221/android-how-to-create-runtime-thumbnail
// http://stackoverflow.com/questions/10191871/converting-bitmap-to-bytearray-android

public class ImageUtils {

    private static final String TAG = "ImageUtils";

    /**
     * @param bitmap
     * @return
     */
    public static String bitmap2base64String(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    /**
     * @param base64String
     * @return
     */
    public static Bitmap base64String2Bitmap(String base64String) {
        byte[] bitmapBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }

    /**
     * @param bytes
     * @return
     */
    public static Bitmap byteArray2Bitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * @param bitmap
     * @return
     */
    public static byte[] bitmap2ByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        return b;
    }

//    public static Bitmap bitmapToThumbnailCreator(Bitmap bitmap, int width, int height, int option) {
//        int calcHeight = (int) (height * calculateScale(bitmap, width, height));
//        int calcWidth = (int) (width * calculateScale(bitmap, width, height));
//        Bitmap thumbnail = Bitmap.createScaledBitmap(bitmap, calcWidth, calcHeight, false);
//        return thumbnail;
//    }
//
//    private static float calculateScale(Bitmap srcBitmap, int width, int height) {
//        if (srcBitmap.getWidth() > srcBitmap.getHeight()) {
//            return (float) ((float) height / (float) srcBitmap.getHeight());
//        } else {
//            return (float) ((float) width / (float) srcBitmap.getWidth());
//        }
//    }
//
//    public static Bitmap getBitmapFromURL(String src) {
//        try {
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    /**
     * Calculate the scale of a given image.
     *
     * @param bitmap the original bitmap to be resize
     * @param width  the required width of the created bitmap
     * @param height the required height of the created bitmap
     * @return scaled image.
     */
    public static Bitmap bitmapToThumbnailCreator(Bitmap bitmap, int width, int height, int option) {
        int calcHeight = (int) (height * calculateScale(bitmap, width, height));
        int calcWidth = (int) (width * calculateScale(bitmap, width, height));
        Bitmap thumbnail = Bitmap.createScaledBitmap(bitmap, calcWidth, calcHeight, false);
        return thumbnail;
    }

    /**
     * Calculate the scale of a given image.
     *
     * @param srcBitmap the original bitmap to be resize
     * @param width     the required width of the created bitmap
     * @param height    the required height of the created bitmap
     * @return scale of the image.
     */
    private static float calculateScale(Bitmap srcBitmap, int width, int height) {
        if (srcBitmap.getWidth() > srcBitmap.getHeight()) {
            return (float) ((float) height / (float) srcBitmap.getHeight());
        } else {
            return (float) ((float) width / (float) srcBitmap.getWidth());
        }
    }

    /**
     * Calculate the scale of a given image.
     *
     * @param bitmap the original bitmap to be resize
     * @param height the required height of the created bitmap
     * @return scaled image.
     */
    public static Bitmap bitmapToThumbnailCreator(Bitmap bitmap, int height, int option) {
        try {
            int calcHeight = (int) (height * calculateHeightScale(bitmap, height));
            int calcWidth = (int) (bitmap.getWidth() * calculateHeightScale(bitmap, height));
            Bitmap thumbnail = Bitmap.createScaledBitmap(bitmap, calcWidth, calcHeight, false);
            return thumbnail;
        } catch (Exception ex) {
            Log.e(TAG, "bitmapToThumbnailCreator", ex);
        }
        return null;
    }

    /**
     * Calculate the scale of a given image.
     *
     * @param srcBitmap the original bitmap to be resize
     * @param height    the required height of the created bitmap
     * @return scale of the image.
     */
    private static float calculateHeightScale(Bitmap srcBitmap, int height) {
        return (float) ((float) height / (float) srcBitmap.getHeight());
    }

    /**
     * Create a bitmap from a given image's URL.
     *
     * @param src the original bitmap to be circled
     * @return circled bitmap
     */
    public static Bitmap getBitmapFromURL(String url) {
        Bitmap bmp = null;
        try {
            URL ulrn = new URL(url);
            HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
            InputStream is = con.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            if (null != bmp)
                return bmp;

        } catch (Exception e) {
        }
        return bmp;
    }


//    public static Bitmap getBitmapFromURL(String url) {
//        try {
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    /**
     * Create a circle bitmap from a given "regular" bitmap image.
     *
     * @param bitmap the original bitmap to be circled
     * @param radius the circle radius
     * @return circled bitmap
     */
    public static Bitmap createCircleBitmap(Bitmap bitmap, int radius) {
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);

        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, radius, paint);

        return circleBitmap;
    }

    /**
     * Convert a bitmap to bytes array.
     *
     * @param bitmap the bitmap to be converted
     * @return bitmap data as bytes array
     */
    public static byte[] convertBitmap2ByteArray(Bitmap bitmap) {
        int bytes = bitmap.getByteCount();
        ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
        bitmap.copyPixelsToBuffer(buffer); //Move the byte data to the buffer
        byte[] array = buffer.array();
        return array;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
