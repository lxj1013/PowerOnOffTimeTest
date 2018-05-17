package cn.com.hisistar.poweronofftimetest;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixinjian on 2018/3/6.
 */

public class BasicTimeSwitchFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "BasicTimeSwitchFragment";
    private static final String TIME_STORE = "TimeStore.db";
    private static final String TABLE_NAME = "Time";
    private Button addBtn;

    private List<MyTime> mTimeList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TimeAdapter mTimeAdapter;

    private PopupWindow mPopupWindow;
    private View contentView;
    private TextView delTv;
    private TextView delAllTv;
    private TextView addNewTv;
    private TextView backTv;

    private int itemPosition;

    private TimeDatabaseHelper mTimeDatabaseHelper;
    private SQLiteDatabase mTimeDb;
    private ContentValues mContentValues;
    private Cursor mCursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.time_switch_settings_fragment, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        initViews();

        initDb();

        initTimes();

        mTimeAdapter = new TimeAdapter(mTimeList);
        mRecyclerView.setAdapter(mTimeAdapter);
        mTimeAdapter.setOnItemClickListener(new TimeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                itemPosition = position;
                Log.i(TAG, "onItemClick: position = " + position);
                View view = mLayoutManager.findViewByPosition(position);

              /*  Log.i(TAG, "onItemClick:mRecyclerView.getX() " + mRecyclerView.getX());
                Log.i(TAG, "onItemClick:mRecyclerView.getY() " + mRecyclerView.getY());
                Log.i(TAG, "onItemClick:mRecyclerView.getWidth() " + mRecyclerView.getWidth());
                Log.i(TAG, "onItemClick:mRecyclerView.getHeight() " + mRecyclerView.getHeight());
                Log.i(TAG, "onItemClick:view.getX() " + view.getX());
                Log.i(TAG, "onItemClick:view.getY() " + view.getY());
                Log.i(TAG, "onItemClick:view.getWidth() " + view.getWidth());
                Log.i(TAG, "onItemClick:view.getHeight() " + view.getHeight());
                Log.i(TAG, "onItemClick:mPopupWindow.getContentView().getWidth() " + mPopupWindow.getContentView().getMeasuredWidth());
                Log.i(TAG, "onItemClick:mPopupWindow.getContentView().getHeight() " + mPopupWindow.getContentView().getMeasuredHeight());
*/
                int rH = mRecyclerView.getHeight();
                int vTY = (int) view.getY();
                int vH = view.getHeight();
                int vBY = vTY + vH;
                int pH = mPopupWindow.getContentView().getMeasuredHeight();


               /* Log.i(TAG, "onItemClick: rH = " + rH);
                Log.i(TAG, "onItemClick: vTY = " + vTY);
                Log.i(TAG, "onItemClick: vBY = " + vBY);
                Log.i(TAG, "onItemClick: pH = " + pH);
                Log.i(TAG, "onItemClick: rH - vBY = " + (rH - vBY));
                Log.i(TAG, "onItemClick: rH - vTY = " + (rH - vTY));*/
                if ((rH - vBY) >= pH) {
                    mPopupWindow.showAsDropDown(view);
                } else if (vTY >= pH) {
                    mPopupWindow.showAsDropDown(view, 0, -pH - vH);
                } else {
                    mPopupWindow.showAsDropDown(view, 0, -(vBY - rH / 2 + pH / 2));
                }

            }
        });
    }

    private void initDb() {
        mTimeDatabaseHelper = new TimeDatabaseHelper(getActivity(), TIME_STORE, null, 1);
        mTimeDb = mTimeDatabaseHelper.getWritableDatabase();
        mContentValues = new ContentValues();
        mCursor = mTimeDb.query(TABLE_NAME, null, null, null, null, null, null);
        Log.i(TAG, "onItemClick: database = " + mTimeDb.toString());

    }

    private void initViews() {

        addBtn = getActivity().findViewById(R.id.time_switch_add_btn);

        mRecyclerView = getActivity().findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        contentView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.popup_window_layout, null);
        mPopupWindow = new PopupWindow(contentView);
        mPopupWindow.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        delTv = contentView.findViewById(R.id.pop_del_tv);
        delAllTv = contentView.findViewById(R.id.pop_del_all_tv);
        addNewTv = contentView.findViewById(R.id.pop_add_new_tv);
        backTv = contentView.findViewById(R.id.pop_back_tv);

        addBtn.setOnClickListener(this);
        delTv.setOnClickListener(this);
        delAllTv.setOnClickListener(this);
        addNewTv.setOnClickListener(this);
        backTv.setOnClickListener(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            Log.i(TAG, "onActivityResult: requestCode = " + requestCode);
            Log.i(TAG, "onActivityResult: resultCode = " + resultCode);
            Log.i(TAG, "onActivityResult: data offTime = " + data.getSerializableExtra("offTime").toString());
            Log.i(TAG, "onActivityResult: data onTime= " + data.getSerializableExtra("onTime").toString());
            MyTime offTime = (MyTime) data.getSerializableExtra("offTime");
            MyTime onTime = (MyTime) data.getSerializableExtra("onTime");

            mContentValues.put("id", mTimeAdapter.getItemCount());
            mContentValues.put("hour", offTime.getHour());
            mContentValues.put("minute", offTime.getMinute());
            mContentValues.put("attribute", offTime.getAttribute());
            mContentValues.put("daysOfWeek", offTime.getDaysOfWeek());
            mTimeDb.insert(TABLE_NAME, null, mContentValues);
            mContentValues.clear();
            mTimeAdapter.add(offTime, mTimeAdapter.getItemCount());

            mContentValues.put("id", mTimeAdapter.getItemCount());
            mContentValues.put("hour", onTime.getHour());
            mContentValues.put("minute", onTime.getMinute());
            mContentValues.put("attribute", onTime.getAttribute());
            mContentValues.put("daysOfWeek", onTime.getDaysOfWeek());
            mTimeDb.insert(TABLE_NAME, null, mContentValues);
            mContentValues.clear();
            mTimeAdapter.add(onTime, mTimeAdapter.getItemCount());

            mRecyclerView.scrollToPosition(mTimeAdapter.getItemCount() - 1);

            Log.i(TAG, "onActivityResult: getHour=" + offTime.getHour());
            Log.i(TAG, "onActivityResult: getMinute=" + offTime.getMinute());
            Log.i(TAG, "onActivityResult: getDaysOfWeek=" + offTime.getDaysOfWeek());
            Log.i(TAG, "onActivityResult: getAttribute=" + offTime.getAttribute());
            Intent offIntent = new Intent("com.soniq.cybercast.time");
            offIntent.putExtra("hour", offTime.getHour());
            offIntent.putExtra("minute", offTime.getMinute());
            offIntent.putExtra("mAttribute", offTime.getAttribute());
            offIntent.putExtra("daysOfWeek", offTime.getDaysOfWeek());
            getActivity().sendBroadcast(offIntent);

            Intent onIntent = new Intent("com.soniq.cybercast.time");
            onIntent.putExtra("hour", onTime.getHour());
            onIntent.putExtra("minute", onTime.getMinute());
            onIntent.putExtra("mAttribute", onTime.getAttribute());
            onIntent.putExtra("daysOfWeek", onTime.getDaysOfWeek());
            getActivity().sendBroadcast(onIntent);

        }

    }

    private void initTimes() {
        if (!mTimeList.isEmpty()) {
            mTimeList.clear();
        }

        if (mCursor.moveToFirst()) {
            do {
                int id = mCursor.getInt(mCursor.getColumnIndex("id"));
                int hour = mCursor.getInt(mCursor.getColumnIndex("hour"));
                int minute = mCursor.getInt(mCursor.getColumnIndex("minute"));
                int attribute = mCursor.getInt(mCursor.getColumnIndex("attribute"));
                int daysOfWeek = mCursor.getInt(mCursor.getColumnIndex("daysOfWeek"));

                mTimeList.add(new MyTime(hour, minute, attribute, daysOfWeek));
//                Log.i(TAG, "initTimes: " + "id = " + id + " hour = " + hour + "  minute = " + minute + "  attribute = " + attribute + "  daysOfWeek = " + daysOfWeek);

            } while (mCursor.moveToNext());
        }
    }

    private void startTimeSettingsActivity() {
        Intent intent = new Intent(getActivity(), TimeSettingsActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, "onClick: " + view.toString());
        switch (view.getId()) {
            case R.id.time_switch_add_btn:
//                mPopupWindow.showAsDropDown(addBtn);
                startTimeSettingsActivity();
                break;
            case R.id.pop_del_tv:
                delTimeSettings();
                break;
            case R.id.pop_del_all_tv:
                delAllTimeSettings();
                break;
            case R.id.pop_add_new_tv:
                startTimeSettingsActivity();
                break;
            case R.id.pop_back_tv:
                break;
            default:
                break;
        }
        mPopupWindow.dismiss();
    }

    private void delTimeSettings() {
        MyTime delTime = mTimeAdapter.findTimeByPosition(itemPosition);
        Log.i(TAG, "onItemClick: Time = " + delTime.toString());

        Intent delIntent = new Intent("com.soniq.cybercast.time_delete");
        delIntent.putExtra("hour", delTime.getHour());
        delIntent.putExtra("minute", delTime.getMinute());
        delIntent.putExtra("mAttribute", delTime.getAttribute());
        delIntent.putExtra("daysOfWeek", delTime.getDaysOfWeek());
        getActivity().sendBroadcast(delIntent);

        mTimeDb.delete(TABLE_NAME, "id = ?", new String[]{"" + itemPosition});
        mTimeAdapter.remove(itemPosition);
        notifyDbRangeChanged(itemPosition, mTimeAdapter.getItemCount());

    }

    private void delAllTimeSettings() {
        Intent delAllIntent = new Intent("com.soniq.cybercast.time_delete");
        delAllIntent.putExtra("delete_all", true);
//        delAllIntent.putExtra("mAttribute", 1);
        getActivity().sendBroadcast(delAllIntent);
        mTimeDb.delete(TABLE_NAME, null, null);
        mTimeAdapter.removeAll();
    }

    private void notifyDbRangeChanged(int position, int count) {
        for (int i = position; i < count; i++) {
            mContentValues.put("id", i);
            mTimeDb.update(TABLE_NAME, mContentValues, "id = ?", new String[]{"" + (i + 1)});
            mContentValues.clear();
        }
    }
}
