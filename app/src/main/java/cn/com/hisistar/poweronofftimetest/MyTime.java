package cn.com.hisistar.poweronofftimetest;

import java.io.Serializable;

public class MyTime implements Serializable {
    private int hour;
    private int minute;
    private int attribute;
    private int daysOfWeek;

    public MyTime(int hour, int minute, int attribute, int daysOfWeek) {
        this.hour = hour;
        this.minute = minute;
        this.attribute = attribute;
        this.daysOfWeek = daysOfWeek;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    public int getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(int daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    @Override
    public String toString() {
        String hour;
        if (this.hour < 10) {
            hour = "0" + this.hour;
        } else {
            hour = this.hour + "";
        }
        String minute;
        if (this.minute < 10) {
            minute = "0" + this.minute;
        } else {
            minute = this.minute + "";
        }

        String str;
        str = " time=" + hour + ":" + minute + " daysOfWeek=" + Integer.toBinaryString(this.daysOfWeek) + " attribute=" + this.attribute;
        return str;
    }
}
