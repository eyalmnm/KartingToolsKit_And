package ktk.em_projects.com.ktk.utils;

/**
 * Created by E M on 23/01/2015.
 */

// http://stackoverflow.com/questions/14196987/java-round-to-nearest-multiple-of-5-either-up-or-down

public class NumberUtils {

    public static int roundToNext5(int number) {
        return 5 * (Math.round(number / 5));
    }

    public static int hexToInt(char ch) {
        if ('a' <= ch && ch <= 'f') {
            return ch - 'a' + 10;
        }
        if ('A' <= ch && ch <= 'F') {
            return ch - 'A' + 10;
        }
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        }
        throw new IllegalArgumentException(String.valueOf(ch));
    }

}
