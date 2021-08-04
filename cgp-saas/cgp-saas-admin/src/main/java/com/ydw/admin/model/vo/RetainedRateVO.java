package com.ydw.admin.model.vo;

public class RetainedRateVO {
    private  String dateTime;
    private  float nextdayRate;
    private float threedayRate;
    private float sevendayRate;
    private float fifteenRate;
    private float thirtyRate;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public float getNextdayRate() {
        return nextdayRate;
    }

    public void setNextdayRate(float nextdayRate) {
        this.nextdayRate = nextdayRate;
    }

    public float getThreedayRate() {
        return threedayRate;
    }

    public void setThreedayRate(float threedayRate) {
        this.threedayRate = threedayRate;
    }

    public float getSevendayRate() {
        return sevendayRate;
    }

    public void setSevendayRate(float sevendayRate) {
        this.sevendayRate = sevendayRate;
    }

    public float getFifteenRate() {
        return fifteenRate;
    }

    public void setFifteenRate(float fifteenRate) {
        this.fifteenRate = fifteenRate;
    }

    public float getThirtyRate() {
        return thirtyRate;
    }

    public void setThirtyRate(float thirtyRate) {
        this.thirtyRate = thirtyRate;
    }
}
