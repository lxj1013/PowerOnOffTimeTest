package cn.com.hisistar.poweronofftimetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TimeDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "TimeDatabaseHelper";

    public static final String CREATE_TIME = "create table Time ("
            + "id integer primary key, "
            + "hour integer, "
            + "minute integer, "
            + "attribute integer, "
            + "daysOfWeek integer)";
    private Context mContext;

    public TimeDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TIME);
        Log.i(TAG, "onCreate: " + "Create succeeded!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
