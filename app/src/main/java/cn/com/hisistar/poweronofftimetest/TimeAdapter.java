package cn.com.hisistar.poweronofftimetest;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    private static final String TAG = "TimeAdapter";

    private Context context = MyApplication.getContext();

    private List<MyTime> mTimeList;

    private OnItemClickListener mItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView timeTv;
        private TextView weekTv;
        private TextView statusTv;

        public ViewHolder(View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.time_tv);
            weekTv = itemView.findViewById(R.id.week_tv);
            statusTv = itemView.findViewById(R.id.status_tv);
        }
    }

    public TimeAdapter(List<MyTime> timeList) {
        mTimeList = timeList;
    }

    public MyTime findTimeByPosition(int position) {
        return mTimeList.get(position);
    }

    public void add(MyTime time, int position) {
        mTimeList.add(position, time);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void remove(int position) {
        mTimeList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void removeAll() {
        int itemCount = getItemCount();
        Log.i(TAG, "removeAll: " + itemCount);
        for (int i = 0; i < itemCount; i++) {
            Log.i(TAG, "removeAll: " + i);
            remove(0);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MyTime time = mTimeList.get(position);
        String timeStr = getTime(time.getHour(), time.getMinute());
        holder.timeTv.setText(timeStr);
        String weekStr = hex2Weeks(time.getDaysOfWeek());
        holder.weekTv.setText(weekStr);
        String status = getStatus(time.getAttribute());
        holder.statusTv.setText(status);
        int color = getColor(time.getHour(), time.getMinute(), time.getAttribute(), time.getDaysOfWeek());
        holder.timeTv.setTextColor(color);
        holder.weekTv.setTextColor(color);
        holder.statusTv.setTextColor(color);
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mTimeList.size();
    }

    private String getTime(int hour, int minute) {
        String time = "";
        if ((hour < 0) || (hour > 23) || (minute < 0) || (minute > 59)) {
            return "error";
        }
        if (hour < 10) {
            time = "0" + hour + ":";
        } else {
            time = hour + ":";
        }

        if (minute < 10) {
            time += "0" + minute;
        } else {
            time += minute;
        }
        return time;
    }

    private String getStatus(int attribute) {
        String status = "";
        if (attribute == 1) {
            status = context.getResources().getString(R.string.PowerOn);
        } else if (attribute == 2) {
            status = context.getResources().getString(R.string.PowerOff);
        } else {
            status = "error";
        }
        return status;
    }

    private int getColor(int hour, int minute, int attribute, int week) {
        int color;
        if ((hour < 0) || (hour > 23) || (minute < 0) || (minute > 59)) {
            return Color.YELLOW;
        }
        if ((week > 0x7f) || (week < 0x01)) {
            return Color.YELLOW;
        }

        if (attribute == 1) {
            color = Color.GREEN;
        } else if (attribute == 2) {
            color = Color.RED;
        } else {
            color = Color.YELLOW;
        }

        return color;
    }


    private String hex2Weeks(int week) {
        String weeks = "";

        if ((week > 0x7f) || (week < 0x01)) {
            return "error";
        }

        if ((week & 0b0000001) == 0b0000001) {
            weeks += context.getResources().getString(R.string.Monday);
        }
        if ((week & 0b0000010) == 0b0000010) {
            weeks += context.getResources().getString(R.string.Tuesday);
        }
        if ((week & 0b0000100) == 0b0000100) {
            weeks += context.getResources().getString(R.string.Wednesday);
        }
        if ((week & 0b0001000) == 0b0001000) {
            weeks += context.getResources().getString(R.string.Thursday);
        }
        if ((week & 0b0010000) == 0b0010000) {
            weeks += context.getResources().getString(R.string.Friday);
        }
        if ((week & 0b0100000) == 0b0100000) {
            weeks += context.getResources().getString(R.string.Saturday);
        }
        if ((week & 0b1000000) == 0b1000000) {
            weeks += context.getResources().getString(R.string.Sunday);
        }
        if ((week & 0b1111111) == 0b1111111) {
            weeks = context.getResources().getString(R.string.EveryDay);
        }
        return weeks;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

}
