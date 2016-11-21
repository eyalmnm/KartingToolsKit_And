package ktk.em_projects.com.ktk.config;

/**
 * Created by E M on 18/01/2015.
 */
public class Constants {

    public static final long HOUR_MILLIS = 1000 * 3600;

    public static class GOOGLE_ANALYTICS {
        public static final String KEY = "UA-58964964-1";
    }

    public static class WORLD_WEATHER_ONLINE {
        public static final String URL = "http://api.worldweatheronline.com/free/v2/weather.ashx";
        public static final String KEY = "d92ab390772883f427af7737698a7";
    }

    public static class ROTAX_FR125_MAX_JET_TUNING_CHART {
        // int[][] myArray = new int[rows][cols];
        public static final int[][] VALUES = {
                {168, 165, 165, 165, 165, 165, 165, 165, 162, 162, 162, 162, 162, 165, 165, 160, 160, 160, 160, 160, 158, 158, 158, 158, 158, 158, 155, 155, 155, 155, 155, 155, 155, 153, 153, 153, 153, 153, 153, 153, 153},   // 930
                {168, 168, 165, 165, 165, 165, 165, 165, 165, 162, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 158, 158, 158, 158, 158, 158, 155, 155, 155, 155, 155, 155, 155, 153, 153, 153, 153, 153, 153, 153},   // 935
                {168, 168, 168, 168, 165, 165, 165, 165, 165, 165, 000, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 160, 160, 158, 158, 158, 158, 158, 158, 158, 155, 155, 155, 155, 155, 155, 155, 153, 153, 153},   // 940
                {168, 168, 168, 168, 168, 000, 165, 165, 165, 165, 165, 165, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 160, 160, 158, 158, 158, 158, 158, 158, 158, 155, 155, 155, 155, 155, 155, 155, 153, 153},   // 945
                {168, 168, 168, 168, 168, 168, 168, 165, 165, 165, 165, 165, 165, 162, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 160, 158, 158, 158, 158, 158, 158, 158, 155, 155, 155, 155, 155, 155, 155, 153},   // 950
                {170, 170, 168, 168, 168, 168, 168, 168, 165, 165, 165, 165, 165, 165, 000, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 160, 158, 158, 158, 158, 158, 158, 158, 155, 155, 155, 155, 155, 155, 155},   // 955
                {170, 170, 170, 168, 168, 168, 168, 168, 168, 000, 165, 165, 165, 165, 165, 165, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 160, 158, 158, 158, 158, 158, 158, 158, 155, 155, 155, 155, 155, 155},   // 960
                {170, 170, 170, 170, 000, 168, 168, 168, 168, 168, 168, 165, 165, 165, 165, 165, 165, 162, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 158, 158, 158, 158, 158, 158, 158, 155, 155, 155, 155, 155},   // 965
                {170, 170, 170, 170, 170, 170, 168, 168, 168, 168, 168, 168, 165, 165, 165, 165, 165, 165, 000, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 160, 158, 158, 158, 158, 158, 158, 155, 155, 155, 155},   // 970
                {172, 170, 170, 170, 170, 170, 170, 168, 168, 168, 168, 168, 168, 000, 165, 165, 165, 165, 165, 165, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 160, 158, 158, 158, 158, 158, 158, 155, 155, 155},   // 975
                {172, 172, 172, 170, 170, 170, 170, 170, 000, 168, 168, 168, 168, 168, 168, 165, 165, 165, 165, 165, 165, 162, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 158, 158, 158, 158, 158, 158, 155, 155},   // 980
                {172, 172, 172, 172, 170, 170, 170, 170, 170, 170, 168, 168, 168, 168, 168, 168, 165, 165, 165, 165, 165, 165, 000, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 158, 158, 158, 158, 158, 158, 155},   // 985
                {172, 172, 172, 172, 172, 170, 170, 170, 170, 170, 170, 168, 168, 168, 168, 168, 168, 000, 165, 165, 165, 165, 165, 165, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 160, 158, 158, 158, 158, 158},   // 990
                {172, 172, 172, 172, 172, 172, 170, 170, 170, 170, 170, 170, 168, 168, 168, 168, 168, 168, 168, 165, 165, 165, 165, 165, 165, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 160, 158, 158, 158, 158},   // 995
                {175, 172, 172, 172, 172, 172, 172, 170, 170, 170, 170, 170, 170, 000, 168, 168, 168, 168, 168, 168, 165, 165, 165, 165, 165, 165, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 160, 158, 158, 158},   // 1000
                {175, 175, 172, 172, 172, 172, 172, 172, 170, 170, 170, 170, 170, 170, 170, 168, 168, 168, 168, 168, 168, 000, 165, 165, 165, 165, 165, 000, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 160, 158, 158},   // 1005
                {175, 175, 175, 172, 172, 172, 172, 172, 172, 172, 170, 170, 170, 170, 170, 170, 168, 168, 168, 168, 168, 168, 000, 165, 165, 165, 165, 165, 000, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160, 158},   // 1010
                {175, 175, 175, 175, 175, 172, 172, 172, 172, 172, 172, 172, 170, 170, 170, 170, 170, 000, 168, 168, 168, 168, 168, 168, 165, 165, 165, 165, 165, 165, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160, 160},   // 1015
                {175, 175, 175, 175, 175, 175, 172, 172, 172, 172, 172, 172, 172, 170, 170, 170, 170, 170, 170, 168, 168, 168, 168, 168, 168, 165, 165, 165, 165, 165, 165, 162, 162, 162, 162, 162, 162, 160, 160, 160, 160},   // 1020
                {178, 175, 175, 175, 175, 175, 175, 172, 172, 172, 172, 172, 172, 172, 170, 170, 170, 170, 170, 170, 168, 168, 168, 168, 168, 168, 165, 165, 165, 165, 165, 165, 162, 162, 162, 162, 162, 162, 160, 160, 160},   // 1025
                {178, 178, 175, 175, 175, 175, 175, 175, 172, 172, 172, 172, 172, 172, 172, 170, 170, 170, 170, 170, 170, 168, 168, 168, 168, 168, 168, 000, 165, 165, 165, 165, 165, 000, 162, 162, 162, 162, 162, 160, 160},   // 1030
                {178, 178, 178, 175, 175, 175, 175, 175, 175, 175, 172, 172, 172, 172, 172, 172, 172, 170, 170, 170, 170, 170, 000, 168, 168, 168, 168, 168, 000, 165, 165, 165, 165, 165, 000, 162, 162, 162, 162, 162, 160},   // 1035
                {178, 178, 178, 178, 175, 175, 175, 175, 175, 175, 175, 172, 172, 172, 172, 172, 172, 172, 170, 170, 170, 170, 170, 168, 168, 168, 168, 168, 168, 168, 165, 165, 165, 165, 165, 000, 162, 162, 162, 162, 162}    // 1040
        };
    }
    
    public static class INTENT_DATA_NAME {
    	public static final String TEMPERATURE_CELSIUS = "temperature_celsius";
    	public static final String PRESSURE_MB = "pressure_mb";
    	public static final String ROTATION = "rotation";
    	public static final String ACCELERATION = "acceleration";
    	public static final String GYROSCOPE = "gyroscope";
    	public static final String POSITION = "position";
    	public static final String WORKING_STATE = "working_state";
        public static final String FILE_NAME = "file_name";
    }

    public static class JSON_NAME {
        public static final String TEMPERATURE_CELSIUS = "temperature_celsius";
        public static final String PRESSURE_MB = "pressure_mb";
        public static final String ROTATION = "rotation";
        public static final String ACCELERATION = "acceleration";
        public static final String GYROSCOPE = "gyroscope";
        public static final String POSITION_LAT = "position_lat";
        public static final String POSITION_LNG = "position_lng";
        public static final String POSITION_SPD = "position_spd";
        public static final String POSITION_ALT = "position_alt";
        public static final String POSITION_ACCURACY = "position_accuracy";
        public static final String TIME_STAMP = "time_stamp";
    }
}
