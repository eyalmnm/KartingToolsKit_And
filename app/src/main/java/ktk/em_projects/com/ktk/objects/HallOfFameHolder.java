package ktk.em_projects.com.ktk.objects;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by E M on 28/01/2015.
 */
//@RealmClass
public class HallOfFameHolder implements Serializable /*extends RealmObject*/ {

    private static final String TAG = "HallOfFameHolder";

    private int mId;
    private String mTrackName;
    private Date mDateTime;
    private String mTimeStr;
    private String mDateStr;
    private long mTotalRunTime;
    private String mTotalRunTimeStr;
    private int mNumOfLaps;
    private long mBestLapTime;
    private String mBestLapTimeStr;
    private boolean mDryTrack;
    private float mGearRatio;
    private int mSrJetting;
    private String mTyresType;
    private String mTrackWeather;
    private float mTrackTemperature;
    private float mAirTemperature;
    private int mPeakRPM;
    private float mToeRight;
    private float mToeLeft;
    private float mCamberCasterRight;
    private float mCamberCasterLeft;
    private float mFrontSpacingRight;
    private float mFrontSpacingLeft;
    private float mRearSpacingRight;
    private float mRearSpacingLeft;
    private int mSprocketSize;
    private float mRimSizeFront;
    private float mRimSizeRear;
    private float mTyreSizeFront;
    private float mTyreSizeRear;
    private float mTyrePressureFront;
    private float mTyrePressureRear;
    private float mBallastRight;
    private float mBallastLeft;
    private String mStiffenerBar;
    private String mSeatPositionType;

    //	@Ignore
    private int mSessionId;

    public void setData(String trackName, Date dateTime, String timeStr,
                        String dateStr, long totalRunTime, String totalRunTimeStr,
                        int numOfLaps, long bestLapTime, String bestLapTimeStr,
                        boolean dryTrack, float gearRatio, int srJetting, String tyresType,
                        String trackWeather, float trackTemperature, float airTemperature,
                        int peakRPM, float toeRight, float toeLeft, float camberCasterRight,
                        float camberCasterLeft, float mFrontSpacingRight,
                        float frontSpacingLeft, float rearSpacingRight, float rearSpacingLeft,
                        int sprocketSize, float rimSizeFront, float rimSizeRear,
                        float tyreSizeFront, float tyreSizeRear, float tyrePressureFront,
                        float tyrePressureRear, float ballastRight, float ballastLeft,
                        String stiffenerBar, String seatPositionType) {
        Log.d(TAG, "setData");
        setmTrackName(trackName);
        setmDateTime(dateTime);
        setmTimeStr(timeStr);
        setmDateStr(dateStr);
        setmTotalRunTime(totalRunTime);
        setmTotalRunTimeStr(totalRunTimeStr);
        setmNumOfLaps(numOfLaps);
        setmBestLapTime(bestLapTime);
        setmBestLapTimeStr(bestLapTimeStr);
        setmDryTrack(dryTrack);
        setmGearRatio(gearRatio);
        setmSrJetting(srJetting);
        setmTyresType(tyresType);
        setmTrackWeather(trackWeather);
        setmTrackTemperature(trackTemperature);
        setmAirTemperature(airTemperature);
        setmPeakRPM(peakRPM);
        setmToeRight(toeRight);
        setmToeLeft(toeLeft);
        setmCamberCasterRight(camberCasterRight);
        setmCamberCasterLeft(camberCasterLeft);
        setmFrontSpacingRight(mFrontSpacingRight);
        setmFrontSpacingLeft(frontSpacingLeft);
        setmRearSpacingRight(rearSpacingRight);
        setmRearSpacingLeft(rearSpacingLeft);
        setmSprocketSize(sprocketSize);
        setmRimSizeFront(rimSizeFront);
        setmRimSizeRear(rimSizeRear);
        setmTyreSizeFront(tyreSizeFront);
        setmTyreSizeRear(tyreSizeRear);
        setmTyrePressureFront(tyrePressureFront);
        setmTyrePressureRear(tyrePressureRear);
        setBallastRight(ballastRight);
        setmBallastLeft(ballastLeft);
        setmStiffenerBar(stiffenerBar);
        setmSeatPositionType(seatPositionType);
    }

    public void setData(int id, String trackName, Date dateTime,
                        String timeStr, String dateStr, long totalRunTime,
                        String totalRunTimeStr, int numOfLaps, long bestLapTime,
                        String bestLapTimeStr, boolean dryTrack, float gearRatio,
                        int srJetting, String tyresType, String trackWeather,
                        float trackTemperature, float airTemperature, int peakRPM,
                        float toeRight, float toeLeft, float camberCasterRight,
                        float camberCasterLeft, float mFrontSpacingRight,
                        float frontSpacingLeft, float rearSpacingRight, float rearSpacingLeft,
                        int sprocketSize, float rimSizeFront, float rimSizeRear,
                        float tyreSizeFront, float tyreSizeRear, float tyrePressureFront,
                        float tyrePressureRear, float ballastRight, float ballastLeft,
                        String stiffenerBar, String seatPositionType) {
        Log.d(TAG, "setData");
        setmTrackName(trackName);
        setmDateTime(dateTime);
        setmTimeStr(timeStr);
        setmDateStr(dateStr);
        setmTotalRunTime(totalRunTime);
        setmTotalRunTimeStr(totalRunTimeStr);
        setmNumOfLaps(numOfLaps);
        setmBestLapTime(bestLapTime);
        setmBestLapTimeStr(bestLapTimeStr);
        setmDryTrack(dryTrack);
        setmGearRatio(gearRatio);
        setmSrJetting(srJetting);
        setmId(id);
        setmTyresType(tyresType);
        setmTrackWeather(trackWeather);
        setmTrackTemperature(trackTemperature);
        setmAirTemperature(airTemperature);
        setmPeakRPM(peakRPM);
        setmToeRight(toeRight);
        setmToeLeft(toeLeft);
        setmCamberCasterRight(camberCasterRight);
        setmCamberCasterLeft(camberCasterLeft);
        setmFrontSpacingRight(mFrontSpacingRight);
        setmFrontSpacingLeft(frontSpacingLeft);
        setmRearSpacingRight(rearSpacingRight);
        setmRearSpacingLeft(rearSpacingLeft);
        setmSprocketSize(sprocketSize);
        setmRimSizeFront(rimSizeFront);
        setmRimSizeRear(rimSizeRear);
        setmTyreSizeFront(tyreSizeFront);
        setmTyreSizeRear(tyreSizeRear);
        setmTyrePressureFront(tyrePressureFront);
        setmTyrePressureRear(tyrePressureRear);
        setBallastRight(ballastRight);
        setmBallastLeft(ballastLeft);
        setmStiffenerBar(stiffenerBar);
        setmSeatPositionType(seatPositionType);
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTrackName() {
        return mTrackName;
    }

    public void setmTrackName(String mTrackName) {
        this.mTrackName = mTrackName;
    }

    public Date getmDateTime() {
        return mDateTime;
    }

    public void setmDateTime(Date mDateTime) {
        this.mDateTime = mDateTime;
    }

    public String getmTimeStr() {
        return mTimeStr;
    }

    public void setmTimeStr(String mTimeStr) {
        this.mTimeStr = mTimeStr;
    }

    public String getmDateStr() {
        return mDateStr;
    }

    public void setmDateStr(String mDateStr) {
        this.mDateStr = mDateStr;
    }

    public long getmTotalRunTime() {
        return mTotalRunTime;
    }

    public void setmTotalRunTime(long mTotalRunTime) {
        this.mTotalRunTime = mTotalRunTime;
    }

    public String getmTotalRunTimeStr() {
        return mTotalRunTimeStr;
    }

    public void setmTotalRunTimeStr(String mTotalRunTimeStr) {
        this.mTotalRunTimeStr = mTotalRunTimeStr;
    }

    public int getmNumOfLaps() {
        return mNumOfLaps;
    }

    public void setmNumOfLaps(int mNumOfLaps) {
        this.mNumOfLaps = mNumOfLaps;
    }

    public long getmBestLapTime() {
        return mBestLapTime;
    }

    public void setmBestLapTime(long mBestLapTime) {
        this.mBestLapTime = mBestLapTime;
    }

    public String getmBestLapTimeStr() {
        return mBestLapTimeStr;
    }

    public void setmBestLapTimeStr(String mBestLapTimeStr) {
        this.mBestLapTimeStr = mBestLapTimeStr;
    }

    public boolean ismDryTrack() {
        return mDryTrack;
    }

    public void setmDryTrack(boolean mDryTrack) {
        this.mDryTrack = mDryTrack;
    }

    public float getmGearRatio() {
        return mGearRatio;
    }

    public void setmGearRatio(float mGearRatio) {
        this.mGearRatio = mGearRatio;
    }

    public int getmSrJetting() {
        return mSrJetting;
    }

    public void setmSrJetting(int mSrJetting) {
        this.mSrJetting = mSrJetting;
    }

    public void setmTyresType(String mTyresType) {
        this.mTyresType = mTyresType;
    }

    public String getmTyresType() {
        return mTyresType;
    }

    public void setmTrackWeather(String mTrackWeather) {
        this.mTrackWeather = mTrackWeather;
    }

    public String getmTrackWeather() {
        return mTrackWeather;
    }

    public void setmTrackTemperature(float mTrackTemperature) {
        this.mTrackTemperature = mTrackTemperature;
    }

    public float getmTrackTemperature() {
        return mTrackTemperature;
    }

    public void setmAirTemperature(float mAirTemperature) {
        this.mAirTemperature = mAirTemperature;
    }

    public float getmAirTemperature() {
        return mAirTemperature;
    }

    public void setmPeakRPM(int mPeakRPM) {
        this.mPeakRPM = mPeakRPM;
    }

    public int getmPeakRPM() {
        return mPeakRPM;
    }

    public void setmToeRight(float mToeRight) {
        this.mToeRight = mToeRight;
    }

    public float getmToeRight() {
        return mToeRight;
    }

    public void setmToeLeft(float mToeLeft) {
        this.mToeLeft = mToeLeft;
    }

    public float getmToeLeft() {
        return mToeLeft;
    }

    public void setmCamberCasterRight(float mCamberCasterRight) {
        this.mCamberCasterRight = mCamberCasterRight;
    }

    public float getmCamberCasterRight() {
        return mCamberCasterRight;
    }

    public void setmCamberCasterLeft(float mCamberCasterLeft) {
        this.mCamberCasterLeft = mCamberCasterLeft;
    }

    public float getmCamberCasterLeft() {
        return mCamberCasterLeft;
    }

    public void setmFrontSpacingRight(float mFrontSpacingRight) {
        this.mFrontSpacingRight = mFrontSpacingRight;
    }

    public float getmFrontSpacingRight() {
        return mFrontSpacingRight;
    }

    public void setmFrontSpacingLeft(float mFrontSpacingLeft) {
        this.mFrontSpacingLeft = mFrontSpacingLeft;
    }

    public float getmFrontSpacingLeft() {
        return mFrontSpacingLeft;
    }

    public void setmRearSpacingRight(float mRearSpacingRight) {
        this.mRearSpacingRight = mRearSpacingRight;
    }

    public float getmRearSpacingRight() {
        return mRearSpacingRight;
    }

    public void setmRearSpacingLeft(float mRearSpacingLeft) {
        this.mRearSpacingLeft = mRearSpacingLeft;
    }

    public float getmRearSpacingLeft() {
        return mRearSpacingLeft;
    }

    public void setmSprocketSize(int mSprocketSize) {
        this.mSprocketSize = mSprocketSize;
    }

    public int getmSprocketSize() {
        return mSprocketSize;
    }

    public void setmRimSizeFront(float mRimSizeFront) {
        this.mRimSizeFront = mRimSizeFront;
    }

    public float getmRimSizeFront() {
        return mRimSizeFront;
    }

    public void setmRimSizeRear(float mRimSizeRear) {
        this.mRimSizeRear = mRimSizeRear;
    }

    public float getmRimSizeRear() {
        return mRimSizeRear;
    }

    public void setmTyreSizeFront(float mTyreSizeFront) {
        this.mTyreSizeFront = mTyreSizeFront;
    }

    public float getmTyreSizeFront() {
        return mTyreSizeFront;
    }

    public void setmTyreSizeRear(float mTyreSizeRear) {
        this.mTyreSizeRear = mTyreSizeRear;
    }

    public float getmTyreSizeRear() {
        return mTyreSizeRear;
    }

    public void setmTyrePressureFront(float mTyrePressureFront) {
        this.mTyrePressureFront = mTyrePressureFront;
    }

    public float getmTyrePressureFront() {
        return mTyrePressureFront;
    }

    public void setmTyrePressureRear(float mTyrePressureRear) {
        this.mTyrePressureRear = mTyrePressureRear;
    }

    public float getmTyrePressureRear() {
        return mTyrePressureRear;
    }

    public void setBallastRight(float mBallastRight) {
        this.mBallastRight = mBallastRight;
    }

    public float getmBallastRight() {
        return mBallastRight;
    }

    public void setmBallastLeft(float mBallastLeft) {
        this.mBallastLeft = mBallastLeft;
    }

    public float getmBallastLeft() {
        return mBallastLeft;
    }

    public void setmStiffenerBar(String mStiffenerBar) {
        this.mStiffenerBar = mStiffenerBar;
    }

    public String getmStiffenerBar() {
        return mStiffenerBar;
    }

    public void setmSeatPositionType(String mSeatPositionType) {
        this.mSeatPositionType = mSeatPositionType;
    }

    public String getmSeatPositionType() {
        return mSeatPositionType;
    }


    // DO NOT Delete the next 2 methods
    public int getmSessionId() {
        return mSessionId;
    }

    public void setmSessionId(int sessionId) {
        mSessionId = sessionId;
    }
}
