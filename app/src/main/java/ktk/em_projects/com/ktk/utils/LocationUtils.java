package ktk.em_projects.com.ktk.utils;

import android.location.Location;

import ktk.em_projects.com.ktk.objects.LatLngAltSpd;

// http://stackoverflow.com/questions/18861728/calculating-distance-between-two-points-represented-by-lat-long-upto-15-feet-acc
public class LocationUtils {

    /**
     * Calculates the distance in km between two lat/long points
     * using the haversine formula
     */
    public static double distanceKm(double lat1, double lng1, double lat2, double lng2) {
        int r = 6371; // average radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = r * c;
        return d;
    }

    public static float convertMeterToFeet(float meters) {
        return meters * 3.28083989501312F;
    }

    public static LatLngAltSpd convertLocationToLatLngAltSpd(Location location) {
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            double spd = location.getSpeed();
            double alt = location.getAltitude();
            float acr = location.getAccuracy();
            return new LatLngAltSpd(lat, lng, spd, alt, acr);
        }
        return null;
    }

    public static float getAbeamPlus(float angle) {
        float ang = angle + 90;
        return (ang % 360);
    }

    public static float getAbeanMinus(float angle) {
        float ang = angle + 270;
        return (ang % 360);
    }

    public static double[] createAbeamLine(Location location, float heading, float radiusMtrs) {
        float degreesAsMtr = convertDegreesToMeters(location.getLatitude());
        if (degreesAsMtr <= 0) {
            degreesAsMtr = 0.001F;
        }
        degreesAsMtr = radiusMtrs / degreesAsMtr;
        double dXplus = degreesAsMtr * Math.sin(getAbeamPlus(heading));
        double dYplus = degreesAsMtr * Math.cos(getAbeamPlus(heading));
        double dXminus = degreesAsMtr * Math.sin(getAbeanMinus(heading));
        double dYminus = degreesAsMtr * Math.cos(getAbeanMinus(heading));
        double[] retLine = new double[4];
        retLine[0] = location.getLongitude() + dXminus;
        retLine[1] = location.getLatitude() + dYminus;
        retLine[2] = location.getLongitude() + dXplus;
        retLine[3] = location.getLatitude() + dYplus;
        return retLine;
    }

    public static float convertDegreesToMeters(double latitude) {
        double lat = Math.abs(latitude);
        if (lat == 0) {
            return 1.1F;
        } else if (lat > 0 && lat <= 23) {
            return 1.0F;
        } else if (lat > 23 && lat <= 45) {
            float diff = (float) (0.2 / 22);
            return (float) (1 - ((lat - 23) * diff));
        } else if (lat > 45 && lat <= 67) {
            float diff = (float) (0.35 / 22);
            return (float) (0.8 - ((lat - 45) * diff));
        } else if (lat > 67 && lat <= 90) {
            float diff = (float) (0.45 / 23);
            return (float) (0.45 - ((lat - 67) * diff));
        } else return -1;
    }
}
