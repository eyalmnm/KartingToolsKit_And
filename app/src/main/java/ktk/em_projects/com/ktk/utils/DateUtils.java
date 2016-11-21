package ktk.em_projects.com.ktk.utils;

import android.annotation.SuppressLint;

import java.util.Calendar;

/**
 * Created by eyalmuchtar on 22/02/2016.
 */
public class DateUtils {

    private static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    /**
     * Given the time elapsed in tenths of seconds, returns the string
     * representation of that time.
     *
     * @param now, the current time in tenths of seconds
     * @return String with the current time in the format MM:SS.T or
     * HH:MM:SS.T, depending on elapsed time.
     */
    public static String formatElapsedTime(long now) {
        long hours = 0, minutes = 0, seconds = 0, tenths = 0;
        StringBuilder sb = new StringBuilder();

        if (now < 1000) {
            tenths = now / 100;
        } else if (now < 60000) {
            seconds = now / 1000;
            now -= seconds * 1000;
            tenths = (now / 100);
        } else if (now < 3600000) {
            hours = now / 3600000;
            now -= hours * 3600000;
            minutes = now / 60000;
            now -= minutes * 60000;
            seconds = now / 1000;
            now -= seconds * 1000;
            tenths = (now / 100);
        }

        if (hours > 0) {
            sb.append(hours).append(":")
                    .append(formatDigits(minutes)).append(":")
                    .append(formatDigits(seconds)).append(".")
                    .append(tenths);
        } else {
            sb.append(formatDigits(minutes)).append(":")
                    .append(formatDigits(seconds)).append(".")
                    .append(tenths);
        }

        return sb.toString();
    }

    @SuppressLint("UseValueOf")
    public static String formatDigits(long num) {
        return (num < 10) ? "0" + num : new Long(num).toString();
    }

    public static Calendar getTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    public static Calendar getNextKeepAliveTime(int nextAlarmInDayHours, int nextAlarmInDayMinutes) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        int hours = now.get(Calendar.HOUR_OF_DAY);
        int minutes = now.get(Calendar.MINUTE);
        if (hours > nextAlarmInDayHours && minutes > nextAlarmInDayMinutes) {
            now = getTomorrowDate();
        }
        now.set(Calendar.HOUR_OF_DAY, nextAlarmInDayHours);
        now.set(Calendar.MINUTE, nextAlarmInDayMinutes);
        return now;
    }

    public static String FormatDate(long currentTimeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(addToDoubleDigit(day));
        stringBuffer.append(getMonth(month));
        stringBuffer.append(year).append(" ");
        stringBuffer.append(addToDoubleDigit(hours)).append(":");
        stringBuffer.append(addToDoubleDigit(minutes)).append(":");
        stringBuffer.append(addToDoubleDigit(seconds));
        return stringBuffer.toString();
    }

    private static String addToDoubleDigit(int day) {
        if (day > 9) {
            return String.valueOf(day);
        } else {
            return "0" + day;
        }
    }

    private static Object getMonth(int month) {
        return months[month];
    }
}
