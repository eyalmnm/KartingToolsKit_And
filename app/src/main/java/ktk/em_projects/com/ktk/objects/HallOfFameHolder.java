package ktk.em_projects.com.ktk.objects;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

import ktk.em_projects.com.ktk.utils.JSONUtils;

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

    public String getmTyresType() {
        return mTyresType;
    }

    public void setmTyresType(String mTyresType) {
        this.mTyresType = mTyresType;
    }

    public String getmTrackWeather() {
        return mTrackWeather;
    }

    public void setmTrackWeather(String mTrackWeather) {
        this.mTrackWeather = mTrackWeather;
    }

    public float getmTrackTemperature() {
        return mTrackTemperature;
    }

    public void setmTrackTemperature(float mTrackTemperature) {
        this.mTrackTemperature = mTrackTemperature;
    }

    public float getmAirTemperature() {
        return mAirTemperature;
    }

    public void setmAirTemperature(float mAirTemperature) {
        this.mAirTemperature = mAirTemperature;
    }

    public int getmPeakRPM() {
        return mPeakRPM;
    }

    public void setmPeakRPM(int mPeakRPM) {
        this.mPeakRPM = mPeakRPM;
    }

    public float getmToeRight() {
        return mToeRight;
    }

    public void setmToeRight(float mToeRight) {
        this.mToeRight = mToeRight;
    }

    public float getmToeLeft() {
        return mToeLeft;
    }

    public void setmToeLeft(float mToeLeft) {
        this.mToeLeft = mToeLeft;
    }

    public float getmCamberCasterRight() {
        return mCamberCasterRight;
    }

    public void setmCamberCasterRight(float mCamberCasterRight) {
        this.mCamberCasterRight = mCamberCasterRight;
    }

    public float getmCamberCasterLeft() {
        return mCamberCasterLeft;
    }

    public void setmCamberCasterLeft(float mCamberCasterLeft) {
        this.mCamberCasterLeft = mCamberCasterLeft;
    }

    public float getmFrontSpacingRight() {
        return mFrontSpacingRight;
    }

    public void setmFrontSpacingRight(float mFrontSpacingRight) {
        this.mFrontSpacingRight = mFrontSpacingRight;
    }

    public float getmFrontSpacingLeft() {
        return mFrontSpacingLeft;
    }

    public void setmFrontSpacingLeft(float mFrontSpacingLeft) {
        this.mFrontSpacingLeft = mFrontSpacingLeft;
    }

    public float getmRearSpacingRight() {
        return mRearSpacingRight;
    }

    public void setmRearSpacingRight(float mRearSpacingRight) {
        this.mRearSpacingRight = mRearSpacingRight;
    }

    public float getmRearSpacingLeft() {
        return mRearSpacingLeft;
    }

    public void setmRearSpacingLeft(float mRearSpacingLeft) {
        this.mRearSpacingLeft = mRearSpacingLeft;
    }

    public int getmSprocketSize() {
        return mSprocketSize;
    }

    public void setmSprocketSize(int mSprocketSize) {
        this.mSprocketSize = mSprocketSize;
    }

    public float getmRimSizeFront() {
        return mRimSizeFront;
    }

    public void setmRimSizeFront(float mRimSizeFront) {
        this.mRimSizeFront = mRimSizeFront;
    }

    public float getmRimSizeRear() {
        return mRimSizeRear;
    }

    public void setmRimSizeRear(float mRimSizeRear) {
        this.mRimSizeRear = mRimSizeRear;
    }

    public float getmTyreSizeFront() {
        return mTyreSizeFront;
    }

    public void setmTyreSizeFront(float mTyreSizeFront) {
        this.mTyreSizeFront = mTyreSizeFront;
    }

    public float getmTyreSizeRear() {
        return mTyreSizeRear;
    }

    public void setmTyreSizeRear(float mTyreSizeRear) {
        this.mTyreSizeRear = mTyreSizeRear;
    }

    public float getmTyrePressureFront() {
        return mTyrePressureFront;
    }

    public void setmTyrePressureFront(float mTyrePressureFront) {
        this.mTyrePressureFront = mTyrePressureFront;
    }

    public float getmTyrePressureRear() {
        return mTyrePressureRear;
    }

    public void setmTyrePressureRear(float mTyrePressureRear) {
        this.mTyrePressureRear = mTyrePressureRear;
    }

    public void setBallastRight(float mBallastRight) {
        this.mBallastRight = mBallastRight;
    }

    public float getmBallastRight() {
        return mBallastRight;
    }

    public float getmBallastLeft() {
        return mBallastLeft;
    }

    public void setmBallastLeft(float mBallastLeft) {
        this.mBallastLeft = mBallastLeft;
    }

    public String getmStiffenerBar() {
        return mStiffenerBar;
    }

    public void setmStiffenerBar(String mStiffenerBar) {
        this.mStiffenerBar = mStiffenerBar;
    }

    public String getmSeatPositionType() {
        return mSeatPositionType;
    }

    public void setmSeatPositionType(String mSeatPositionType) {
        this.mSeatPositionType = mSeatPositionType;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", mId);
        jsonObject.put("trackName", mTrackName);
        jsonObject.put("dateTime", mDateTime.getTime());
        jsonObject.put("timeStr", mTimeStr);
        jsonObject.put("dateStr", mDateStr);
        jsonObject.put("totalRunTime", mTotalRunTime);
        jsonObject.put("totalRunTimeStr", mTotalRunTimeStr);
        jsonObject.put("numOfLaps", mNumOfLaps);
        jsonObject.put("bestLapTime", mBestLapTime);
        jsonObject.put("bestLapTimeStr", mBestLapTimeStr);
        jsonObject.put("dryTrack", mDryTrack);
        jsonObject.put("gearRatio", mGearRatio);
        jsonObject.put("srJetting", mSrJetting);
        jsonObject.put("tyresType", mTyresType);
        jsonObject.put("trackWeather", mTrackWeather);
        jsonObject.put("trackTemperature", mTrackTemperature);
        jsonObject.put("airTemperature", mAirTemperature);
        jsonObject.put("peakRPM", mPeakRPM);
        jsonObject.put("toeRight", mToeRight);
        jsonObject.put("toeLeft", mToeLeft);
        jsonObject.put("camberCasterRight", mCamberCasterRight);
        jsonObject.put("camberCasterLeft", mCamberCasterLeft);
        jsonObject.put("frontSpacingRight", mFrontSpacingRight);
        jsonObject.put("frontSpacingLeft", mFrontSpacingLeft);
        jsonObject.put("rearSpacingRight", mRearSpacingRight);
        jsonObject.put("rearSpacingLeft", mRearSpacingLeft);
        jsonObject.put("sprocketSize", mSprocketSize);
        jsonObject.put("rimSizeFront", mRimSizeFront);
        jsonObject.put("rimSizeRear", mRimSizeRear);
        jsonObject.put("tyreSizeFront", mTyreSizeFront);
        jsonObject.put("tyreSizeRear", mTyreSizeRear);
        jsonObject.put("tyrePressureFront", mTyrePressureFront);
        jsonObject.put("tyrePressureRear", mTyrePressureRear);
        jsonObject.put("ballastRight", mBallastRight);
        jsonObject.put("ballastLeft", mBallastLeft);
        jsonObject.put("stiffenerBar", mStiffenerBar);
        jsonObject.put("seatPositionType", mSeatPositionType);
        return jsonObject;
    }

    public void setData(JSONObject json) {
        mId = JSONUtils.getIntValue(json, "id");
        mTrackName = JSONUtils.getStringValue(json, "trackName");
        mDateTime = new Date(JSONUtils.getLongValue(json, "dateTime"));
        mTimeStr = JSONUtils.getStringValue(json, "timeStr");
        mDateStr = JSONUtils.getStringValue(json, "dateStr");
        mTotalRunTime = JSONUtils.getLongValue(json, "totalRunTime");
        mTotalRunTimeStr = JSONUtils.getStringValue(json, "totalRunTimeStr");
        mNumOfLaps = JSONUtils.getIntValue(json, "numOfLaps");
        mBestLapTime = JSONUtils.getLongValue(json, "bestLapTime");
        mBestLapTimeStr = JSONUtils.getStringValue(json, "bestLapTimeStr");
        mDryTrack = JSONUtils.getBooleanValue(json, "dryTrack");
        mGearRatio = JSONUtils.getFloatValue(json, "gearRatio");
        mSrJetting = JSONUtils.getIntValue(json, "srJetting");
        mTyresType = JSONUtils.getStringValue(json, "tyresType");
        mTrackWeather = JSONUtils.getStringValue(json, "trackWeather");
        mTrackTemperature = JSONUtils.getFloatValue(json, "trackTemperature");
        mAirTemperature = JSONUtils.getFloatValue(json, "airTemperature");
        mPeakRPM = JSONUtils.getIntValue(json, "peakRPM");
        mToeRight = JSONUtils.getFloatValue(json, "toeRight");
        mToeLeft = JSONUtils.getFloatValue(json, "toeLeft");
        mCamberCasterRight = JSONUtils.getFloatValue(json, "camberCasterRight");
        mCamberCasterLeft = JSONUtils.getFloatValue(json, "camberCasterLeft");
        mFrontSpacingRight = JSONUtils.getFloatValue(json, "frontSpacingRight");
        mFrontSpacingLeft = JSONUtils.getFloatValue(json, "frontSpacingLeft");
        mRearSpacingRight = JSONUtils.getFloatValue(json, "rearSpacingRight");
        mRearSpacingLeft = JSONUtils.getFloatValue(json, "rearSpacingLeft");
        mSprocketSize = JSONUtils.getIntValue(json, "sprocketSize");
        mRimSizeFront = JSONUtils.getFloatValue(json, "rimSizeFront");
        mRimSizeRear = JSONUtils.getFloatValue(json, "rimSizeRear");
        mTyreSizeFront = JSONUtils.getFloatValue(json, "tyreSizeFront");
        mTyreSizeRear = JSONUtils.getFloatValue(json, "tyreSizeRear");
        mTyrePressureFront = JSONUtils.getFloatValue(json, "tyrePressureFront");
        mTyrePressureRear = JSONUtils.getFloatValue(json, "tyrePressureRear");
        mBallastRight = JSONUtils.getFloatValue(json, "ballastRight");
        mBallastLeft = JSONUtils.getFloatValue(json, "ballastLeft");
        mStiffenerBar = JSONUtils.getStringValue(json, "stiffenerBar");
        mSeatPositionType = JSONUtils.getStringValue(json, "seatPositionType");
    }

    // DO NOT Delete the next 2 methods
    public int getmSessionId() {
        return mSessionId;
    }

    public void setmSessionId(int sessionId) {
        mSessionId = sessionId;
    }
}
