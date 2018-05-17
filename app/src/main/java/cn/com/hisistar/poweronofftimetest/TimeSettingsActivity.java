package cn.com.hisistar.poweronofftimetest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class TimeSettingsActivity extends AppCompatActivity
        implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "TimeSettingsActivity";
    private Window window;
    private WindowManager windowManager;
    private DisplayMetrics displayMetrics;

    private Button offHourPlusBtn;
    private TextView offHourTv;
    private int offHour = 0;
    private Button offHourMinusBtn;

    private Button offMinutePlusBtn;
    private TextView offMinuteTv;
    private int offMinute = 0;
    private Button offMinuteMinusBtn;

    private Button onHourPlusBtn;
    private TextView onHourTv;
    private int onHour = 0;
    private Button onHourMinusBtn;

    private Button onMinutePlusBtn;
    private TextView onMinuteTv;
    private int onMinute = 0;
    private Button onMinuteMinusBtn;

    private CheckBox weekAllChk;
    private CheckBox weekMondayChk;
    private CheckBox weekTuesdayChk;
    private CheckBox weekWednesdayChk;
    private CheckBox weekThursdayChk;
    private CheckBox weekFridayChk;
    private CheckBox weekSaturdayChk;
    private CheckBox weekSundayChk;


    private Button addTimeSettingsBtn;
    private Button closeTimeSettingsBtn;

    private int daysOfWeek = 0;
    private MyTime offTime;
    private MyTime onTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_settings);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setWindow();
        initViews();
    }

    public void setWindow() {
        window = this.getWindow();
        windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        displayMetrics = new DisplayMetrics();
        WindowSizeUtil windowSizeUtil = new WindowSizeUtil(window, windowManager, displayMetrics);
        if (windowSizeUtil.getScreenOrientation() == windowSizeUtil.LANDSCAPE) {
            windowSizeUtil.setWindow(0.4f, 0.5f);
        } else if (windowSizeUtil.getScreenOrientation() == windowSizeUtil.PORTRAIT) {
            windowSizeUtil.setWindow(0.5f, 0.4f);
        }
    }

    public void initViews() {

        setFinishOnTouchOutside(false);

        offHourPlusBtn = findViewById(R.id.time_settings_off_hour_plus_btn);
        offHourTv = findViewById(R.id.time_settings_off_hour_tv);
        offHourMinusBtn = findViewById(R.id.time_settings_off_hour_minus_btn);

        offMinutePlusBtn = findViewById(R.id.time_settings_off_minute_plus_btn);
        offMinuteTv = findViewById(R.id.time_settings_off_minute_tv);
        offMinuteMinusBtn = findViewById(R.id.time_settings_off_minute_minus_btn);

        onHourPlusBtn = findViewById(R.id.time_settings_on_hour_plus_btn);
        onHourTv = findViewById(R.id.time_settings_on_hour_tv);
        onHourMinusBtn = findViewById(R.id.time_settings_on_hour_minus_btn);

        onMinutePlusBtn = findViewById(R.id.time_settings_on_minute_plus_btn);
        onMinuteTv = findViewById(R.id.time_settings_on_minute_tv);
        onMinuteMinusBtn = findViewById(R.id.time_settings_on_minute_minus_btn);

        weekAllChk = findViewById(R.id.time_settings_week_all_chk);
        weekMondayChk = findViewById(R.id.time_settings_week_monday_chk);
        weekTuesdayChk = findViewById(R.id.time_settings_week_tuesday_chk);
        weekWednesdayChk = findViewById(R.id.time_settings_week_wednesday_chk);
        weekThursdayChk = findViewById(R.id.time_settings_week_thursday_chk);
        weekFridayChk = findViewById(R.id.time_settings_week_friday_chk);
        weekSaturdayChk = findViewById(R.id.time_settings_week_saturday_chk);
        weekSundayChk = findViewById(R.id.time_settings_week_sunday_chk);

        addTimeSettingsBtn = findViewById(R.id.time_settings_add_btn);
        closeTimeSettingsBtn = findViewById(R.id.time_settings_close_btn);

        offHourPlusBtn.setOnClickListener(this);
        offHourMinusBtn.setOnClickListener(this);

        offMinutePlusBtn.setOnClickListener(this);
        offMinuteMinusBtn.setOnClickListener(this);

        onHourPlusBtn.setOnClickListener(this);
        onHourMinusBtn.setOnClickListener(this);

        onMinutePlusBtn.setOnClickListener(this);
        onMinuteMinusBtn.setOnClickListener(this);

        weekAllChk.setOnCheckedChangeListener(this);
        weekMondayChk.setOnCheckedChangeListener(this);
        weekTuesdayChk.setOnCheckedChangeListener(this);
        weekWednesdayChk.setOnCheckedChangeListener(this);
        weekThursdayChk.setOnCheckedChangeListener(this);
        weekFridayChk.setOnCheckedChangeListener(this);
        weekSaturdayChk.setOnCheckedChangeListener(this);
        weekSundayChk.setOnCheckedChangeListener(this);

        addTimeSettingsBtn.setOnClickListener(this);
        closeTimeSettingsBtn.setOnClickListener(this);

        offTime = new MyTime(0, 0, 2, 0);
        onTime = new MyTime(0, 0, 1, 0);
    }

    @Override
    public void onClick(View view) {
//        Log.i(TAG, "onClick: view.getId() = " + view.toString());
        switch (view.getId()) {
            case R.id.time_settings_off_hour_plus_btn:
                offHourPlus();
                break;
            case R.id.time_settings_off_hour_minus_btn:
                offHourMinus();
                break;
            case R.id.time_settings_off_minute_plus_btn:
                offMinutePlus();
                break;
            case R.id.time_settings_off_minute_minus_btn:
                offMinuteMinus();
                break;
            case R.id.time_settings_on_hour_plus_btn:
                onHourPlus();
                break;
            case R.id.time_settings_on_hour_minus_btn:
                onHourMinus();
                break;
            case R.id.time_settings_on_minute_plus_btn:
                onMinutePlus();
                break;
            case R.id.time_settings_on_minute_minus_btn:
                onMinuteMinus();
                break;
            case R.id.time_settings_add_btn:
                addTimeSettings();
                break;
            case R.id.time_settings_close_btn:
                closeTimeSettings();
                break;
            default:
                break;
        }
    }

    private void offHourPlus() {
        offHour++;
        if (offHour > 23) {
            offHour = 0;
        }
        offTime.setHour(offHour);
        String hour;
        if (offHour < 10) {
            hour = "0" + offHour;
        } else {
            hour = offHour + "";
        }
        offHourTv.setText(hour);
    }

    private void offHourMinus() {
        offHour--;
        if (offHour < 0) {
            offHour = 23;
        }
        offTime.setHour(offHour);
        String hour;
        if (offHour < 10) {
            hour = "0" + offHour;
        } else {
            hour = offHour + "";
        }
        offHourTv.setText(hour);
    }

    private void offMinutePlus() {
        offMinute++;
        if (offMinute > 59) {
            offMinute = 0;
        }
        offTime.setMinute(offMinute);
        String minute;
        if (offMinute < 10) {
            minute = "0" + offMinute;
        } else {
            minute = offMinute + "";
        }
        offMinuteTv.setText(minute);
    }

    private void offMinuteMinus() {
        offMinute--;
        if (offMinute < 0) {
            offMinute = 59;
        }
        offTime.setMinute(offMinute);
        String minute;
        if (offMinute < 10) {
            minute = "0" + offMinute;
        } else {
            minute = offMinute + "";
        }
        offMinuteTv.setText(minute);
    }

    private void onHourPlus() {
        onHour++;
        if (onHour > 23) {
            onHour = 0;
        }
        onTime.setHour(onHour);
        String hour;
        if (onHour < 10) {
            hour = "0" + onHour;
        } else {
            hour = onHour + "";
        }
        onHourTv.setText(hour);
    }

    private void onHourMinus() {
        onHour--;
        if (onHour < 0) {
            onHour = 23;
        }
        onTime.setHour(onHour);
        String hour;
        if (onHour < 10) {
            hour = "0" + onHour;
        } else {
            hour = onHour + "";
        }
        onHourTv.setText(hour);
    }

    private void onMinutePlus() {
        onMinute++;
        if (onMinute > 59) {
            onMinute = 0;
        }
        onTime.setMinute(onMinute);
        String minute;
        if (onMinute < 10) {
            minute = "0" + onMinute;
        } else {
            minute = onMinute + "";
        }
        onMinuteTv.setText(minute);
    }

    private void onMinuteMinus() {
        onMinute--;
        if (onMinute < 0) {
            onMinute = 59;
        }
        onTime.setMinute(onMinute);
        String minute;
        if (onMinute < 10) {
            minute = "0" + onMinute;
        } else {
            minute = onMinute + "";
        }
        onMinuteTv.setText(minute);
    }

    private void addTimeSettings() {
        if (daysOfWeek == 0) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.days_of_week_not_set), Toast.LENGTH_SHORT).show();
            return;
        }
        offTime.setDaysOfWeek(daysOfWeek);
        onTime.setDaysOfWeek(daysOfWeek);
        Intent intent = new Intent();
        intent.putExtra("offTime", offTime);
        intent.putExtra("onTime", onTime);
        setResult(1, intent);
        this.finish();
    }

    private void closeTimeSettings() {
        this.finish();
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        Log.i(TAG, "onCheckedChanged: compoundButton.getId() = " + compoundButton.toString());
//        Log.i(TAG, "onCheckedChanged: b = " + b);
        if (!b) {
            weekAllChk.setChecked(b);
        }
        switch (compoundButton.getId()) {
            case R.id.time_settings_week_all_chk:
                weekAll(b);
                break;
            case R.id.time_settings_week_monday_chk:
                weekMonday(b);
                break;
            case R.id.time_settings_week_tuesday_chk:
                weekTuesday(b);
                break;
            case R.id.time_settings_week_wednesday_chk:
                weekWednesday(b);
                break;
            case R.id.time_settings_week_thursday_chk:
                weekThursday(b);
                break;
            case R.id.time_settings_week_friday_chk:
                weekFriday(b);
                break;
            case R.id.time_settings_week_saturday_chk:
                weekSaturday(b);
                break;
            case R.id.time_settings_week_sunday_chk:
                weekSunday(b);
                break;
            default:
                break;
        }
    }

    private void weekAll(boolean b) {
        if (!b) {
            if (!weekMondayChk.isChecked()
                    || !weekTuesdayChk.isChecked()
                    || !weekWednesdayChk.isChecked()
                    || !weekThursdayChk.isChecked()
                    || !weekFridayChk.isChecked()
                    || !weekSaturdayChk.isChecked()
                    || !weekSundayChk.isChecked()) {
                return;
            }
        }
        weekMondayChk.setChecked(b);
        weekTuesdayChk.setChecked(b);
        weekWednesdayChk.setChecked(b);
        weekThursdayChk.setChecked(b);
        weekFridayChk.setChecked(b);
        weekSaturdayChk.setChecked(b);
        weekSundayChk.setChecked(b);
    }

    private void weekMonday(boolean b) {
        if (b) {
            daysOfWeek |= 0b0000001;
        } else {
            daysOfWeek &= 0b1111110;
        }
//        Log.i(TAG, "weekMonday: " + Integer.toBinaryString(daysOfWeek));
    }

    private void weekTuesday(boolean b) {
        if (b) {
            daysOfWeek |= 0b0000010;
        } else {
            daysOfWeek &= 0b1111101;
        }
//        Log.i(TAG, "weekTuesday: " + Integer.toBinaryString(daysOfWeek));
    }

    private void weekWednesday(boolean b) {
        if (b) {
            daysOfWeek |= 0b0000100;
        } else {
            daysOfWeek &= 0b1111011;
        }
//        Log.i(TAG, "weekWednesday: " + Integer.toBinaryString(daysOfWeek));
    }

    private void weekThursday(boolean b) {
        if (b) {
            daysOfWeek |= 0b0001000;
        } else {
            daysOfWeek &= 0b1110111;
        }
//        Log.i(TAG, "weekThursday: " + Integer.toBinaryString(daysOfWeek));
    }

    private void weekFriday(boolean b) {
        if (b) {
            daysOfWeek |= 0b0010000;
        } else {
            daysOfWeek &= 0b1101111;
        }
//        Log.i(TAG, "weekFriday: " + Integer.toBinaryString(daysOfWeek));
    }

    private void weekSaturday(boolean b) {
        if (b) {
            daysOfWeek |= 0b0100000;
        } else {
            daysOfWeek &= 0b1011111;
        }
//        Log.i(TAG, "weekSaturday: " + Integer.toBinaryString(daysOfWeek));
    }

    private void weekSunday(boolean b) {
        if (b) {
            daysOfWeek |= 0b1000000;
        } else {
            daysOfWeek &= 0b0111111;
        }
//        Log.i(TAG, "weekSunday: " + Integer.toBinaryString(daysOfWeek));
    }

}
