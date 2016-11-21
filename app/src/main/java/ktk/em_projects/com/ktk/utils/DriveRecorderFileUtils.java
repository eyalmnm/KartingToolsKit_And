package ktk.em_projects.com.ktk.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Ref: https://code.google.com/p/android-file-explorer/

// Ref: http://stackoverflow.com/questions/12421814/how-can-i-read-a-text-file-in-android
// Ref: http://stackoverflow.com/questions/5987012/append-to-file-android

public class DriveRecorderFileUtils {

	private static final String TAG = "DriveRecorderFileUtils";
	
	private static final String DIR_NAME = "/records/";
	
	
	public static File crateNewFile() {
		Log.d(TAG, "crateNewFile");
		File root = new File(Environment.getExternalStorageDirectory(), DIR_NAME);
		if (!root.exists()) {
			root.mkdir();
		}
		String fileName = generateFileName(System.currentTimeMillis());
		return new File(root, fileName);
	}
	
	public static String generateFileName(long currentTimeMillis) {
		Log.d(TAG, "generateFileName");
		String timeFormatStr = "yyyy-MM-dd_HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormatStr, Locale.US);
		return sdf.format(new Date(currentTimeMillis));
	}
	
	public static String[] getFiles() {
		Log.d(TAG, "getFiles");
		File root = new File(Environment.getExternalStorageDirectory(), DIR_NAME);
		if(root.isDirectory()) {
			return root.list();
		}
		return null;
	}
	
	public static File getFile(String fileName) {
		Log.d(TAG, "getFile");
		return new File(DIR_NAME, fileName);
	}
	
	public static String readFromFile(String fileName) throws IOException {
		return readFromFile(new File(DIR_NAME, fileName));
	}
	
	public static String readFromFile(File file) throws IOException {
		Log.d(TAG, "readFromFile");
		if (file == null) return null;
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line); //.append('\n');
		}
		reader.close();
		return sb.toString();
	}
}
