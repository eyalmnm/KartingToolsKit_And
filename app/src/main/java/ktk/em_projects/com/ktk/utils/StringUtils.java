package ktk.em_projects.com.ktk.utils;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

public class StringUtils {

    private static DecimalFormat df = new DecimalFormat("###.###");

    /**
     * Chaeck whether a given string is valid
     *
     * @param str the string to be checked
     * @return boolean indicate string invalidity.
     */
    public static boolean isNullOrEmpty(String str) {
        if (str == null)
            return true;
        str = str.trim().replace("?", "").replace("<", "").replace(">", "").
                replace("&", "").replace("\"", "").replace("\'", "").replace(";", "");
        if (str.trim().length() == 0)
            return true;
        return false;
    }

    public static boolean isReadableCharacters(String string) {
        String pattern = "^[a-zA-Z0-9:<>]*$";
        return (string.matches(pattern));
    }

    public static boolean isContainsInArray(String[] stringArray, String str) {
        if (StringUtils.isNullOrEmpty(str)) {
            return false;
        }
        if (stringArray == null || stringArray.length == 0) {
            return false;
        }
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static long[] stringToLongArr(String string) {
        String[] longsAsStrings = stringToStringArr(string);
        long[] retLongs = new long[longsAsStrings.length];
        for (int i = 0; i < longsAsStrings.length; i++) {
            retLongs[i] = Long.parseLong(longsAsStrings[i]);
        }
        return retLongs;
    }

    public static String[] stringToStringArr(String string) {
        string = string.trim().replace("[", "");
        string = string.trim().replace("]", "");
        string = string.replace(',', ';');
        return stringToStringArr(string, ";");
    }

    public static String[] stringToStringArr(String string, boolean selectable) {
        if (string.contains(",")) {
            return stringToStringArr(string, ",");
        } else if (string.contains(";")) {
            return stringToStringArr(string, ";");
        }
        return new String[]{string};
    }

    private static String[] stringToStringArr(String string, String delimiter) {
        if (StringUtils.isNullOrEmpty(string))
            return null;
        Vector<String> vector = new Vector<String>();
        StringTokenizer stringTokenizer = new StringTokenizer(string, delimiter);
        while (stringTokenizer.hasMoreElements()) {
            vector.add((String) stringTokenizer.nextElement());
        }
        if (vector.isEmpty())
            vector.add(string);
        String[] stringArr = new String[vector.size()];
        vector.copyInto(stringArr);
        return stringArr;
    }

    public static String stringArrayToString(String[] stringArray, String delimiter) {
        if (StringUtils.isNullOrEmpty(delimiter)) {
            return "";
        }
        if (stringArray == null || stringArray.length == 0) {
            return "";
        }
        if (stringArray.length == 1) {
            return stringArray[0];
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < stringArray.length; i++) {
            stringBuffer.append(stringArray[i]);
            if (i <= stringArray.length - 2) {
                stringBuffer.append(delimiter);
            }
        }
        return stringBuffer.toString();
    }

    public static String convertDoubleToFormat(double value) {
        return df.format(value);
    }

    public static boolean isMailAddress(String str) {
        if (str.contains("@")) {
            return true;
        }
        return false;
    }

    public static String[] ArrayListToStringArray(ArrayList<String> strings) {
        return (String[]) strings.toArray();
    }

    public static ArrayList<String> stringArraToArrayList(String[] strings) {
        ArrayList<String> retVal = new ArrayList<String>(strings.length);
        for (String string : strings) {
            retVal.add(string);
        }
        return retVal;
    }


    /**
     * Chaeck whether a given string starts with sub.
     *
     * @param str the original string .
     * @param sub the string to be checked.
     * @return boolean indicate string starts with sub.
     */
    @SuppressLint("DefaultLocale")
    public static boolean startWith(String str, String sub) {
        String longString = str.toLowerCase();
        String shortString = sub.toLowerCase();
        return longString.startsWith(shortString);
    }

    public static String stringArraToJSONArrayFormat(String[] value) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < value.length; i++) {
            sb.append("\"" + value[i].replace(' ', '_') + "\"");
            if (i < value.length - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    public static String HexStringToString(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {
            char a = hex.charAt(i);
            char b = hex.charAt(i + 1);
            sb.append((char) ((NumberUtils.hexToInt(a) << 4) | NumberUtils.hexToInt(b)));
        }
        return sb.toString();
    }

}
