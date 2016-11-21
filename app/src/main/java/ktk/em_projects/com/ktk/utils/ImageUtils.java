package ktk.em_projects.com.ktk.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


// http://stackoverflow.com/questions/2577221/android-how-to-create-runtime-thumbnail
// http://stackoverflow.com/questions/10191871/converting-bitmap-to-bytearray-android

public class ImageUtils {

    public static String bitmap2base64String(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap base64String2Bitmap(String base64String) {
        byte[] bitmapBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }

    public static Bitmap byteArray2Bitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static byte[] bitmap2ByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        return b;
    }

    public static Bitmap bitmapToThumbnailCreator(Bitmap bitmap, int width, int height, int option) {
        int calcHeight = (int) (height * calculateScale(bitmap, width, height));
        int calcWidth = (int) (width * calculateScale(bitmap, width, height));
        Bitmap thumbnail = Bitmap.createScaledBitmap(bitmap, calcWidth, calcHeight, false);
        return thumbnail;
    }

    private static float calculateScale(Bitmap srcBitmap, int width, int height) {
        if (srcBitmap.getWidth() > srcBitmap.getHeight()) {
            return (float) ((float) height / (float) srcBitmap.getHeight());
        } else {
            return (float) ((float) width / (float) srcBitmap.getWidth());
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
