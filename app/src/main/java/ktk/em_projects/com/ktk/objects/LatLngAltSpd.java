package ktk.em_projects.com.ktk.objects;

/**
 * Created by eyalmuchtar on 16/02/2016.
 */
public class LatLngAltSpd {

    private double mLatitude;
    private double mLongitude;
    private double mSpeed;
    private double mAltitude;
    private double mAccuracy;

    public LatLngAltSpd(double latitude, double longitude, double speed, double altitude, double accuracy) {
        mLatitude = latitude;
        mLongitude = longitude;
        mSpeed = speed;
        mAltitude = altitude;
        mAccuracy = accuracy;
    }

    public double getAccuracy() {
        return mAccuracy;
    }

    public double getAltitude() {
        return mAltitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public double getSpeed() {
        return mSpeed;
    }

    @Override
    public String toString() {
        return "LatLngAltSpd{" +
                "mAccuracy=" + mAccuracy +
                ", mLatitude=" + mLatitude +
                ", mLongitude=" + mLongitude +
                ", mSpeed=" + mSpeed +
                ", mAltitude=" + mAltitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LatLngAltSpd that = (LatLngAltSpd) o;

        if (Double.compare(that.mLatitude, mLatitude) != 0) return false;
        if (Double.compare(that.mLongitude, mLongitude) != 0) return false;
        if (Double.compare(that.mSpeed, mSpeed) != 0) return false;
        if (Double.compare(that.mAltitude, mAltitude) != 0) return false;
        return Double.compare(that.mAccuracy, mAccuracy) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(mLatitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mLongitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mSpeed);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mAltitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mAccuracy);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
