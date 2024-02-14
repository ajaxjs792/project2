package com.android.kaviapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.kaviapp.KaviApp;
import com.android.kaviapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;




public class Utils {

    public static void logBig(String veryLongString)
    {
        int maxLogSize = 1000;
        for(int i = 0; i <= veryLongString.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > veryLongString.length() ? veryLongString.length() : end;
            Log.v(TAG, veryLongString.substring(start, end));
        }
    }

    public static Point getDisplaySize(WindowManager windowManager) {
        try {
            if (Build.VERSION.SDK_INT > 16) {
                Display display = windowManager.getDefaultDisplay();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                display.getMetrics(displayMetrics);
                return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
            } else {
                return new Point(0, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Point(0, 0);
        }
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public MaterialDialog progressDialog;
    private Activity mActivity;

    public Utils(Activity activity) {
        this.mActivity = activity;
    }


    public static void alertBox(Context context, String msg) {
        new MaterialDialog.Builder(context)
                .content(msg)
                .backgroundColorRes(R.color.white)
                .contentColorRes(R.color.black)
                .positiveText("OK")
                .show();
    }

    public static void alertBox(Context context, int id) {
        new MaterialDialog.Builder(context)
                .content(context.getResources().getString(id))
                .backgroundColorRes(R.color.white)
                .contentColorRes(R.color.black)

                .positiveText("OK")
                .show();
    }

    public void showProgress() {
        progressDialog = new MaterialDialog.Builder(mActivity)
                .backgroundColorRes(R.color.white)
                .contentColorRes(R.color.black)
                .content("loading..")
                .progress(true, 0)
                .show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void hideProgress() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    static final String TAG = "TimeUtil";
    static final long WEEK = 24 * 60 * 60 * 7;
    static final long DAY = 24 * 60 * 60;
    static final long HOUR = 60 * 60;
    static final long MINUTE = 60;

    public static CharSequence formatTime(String time, String format) {
        long timestamp = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date date = dateFormat.parse(time);
            timestamp = date.getTime();
        } catch (ParseException e) {
            Log.w(TAG, "can't parse time!");
            return time;
        }
        final long now = System.currentTimeMillis();
        final long timeGap = (now - timestamp) / 1000;
        if (timeGap < 0) {
            return time;
        } else if (timeGap < MINUTE) {
            return DateUtils.getRelativeTimeSpanString(timestamp, now, DateUtils.SECOND_IN_MILLIS);
        } else if (timeGap < HOUR) {
            return DateUtils.getRelativeTimeSpanString(timestamp, now, DateUtils.MINUTE_IN_MILLIS);
        } else if (timeGap < DAY) {
            return DateUtils.getRelativeTimeSpanString(timestamp, now, DateUtils.HOUR_IN_MILLIS);
        } else if (timeGap < WEEK) {
            return DateUtils.getRelativeTimeSpanString(timestamp, now, DateUtils.DAY_IN_MILLIS);
        } else if (timeGap < WEEK * 4) {
            return DateUtils.getRelativeTimeSpanString(timestamp, now, DateUtils.WEEK_IN_MILLIS);
        } else if (timeGap < WEEK * 8) {
            return "1 month ago";
        } else if (timeGap < WEEK * 12) {
            return "2 months ago";
        } else if (timeGap < WEEK * 16) {
            return "3 months ago";
        } else if (timeGap < WEEK * 20) {
            return "4 months ago";
        } else if (timeGap < WEEK * 24) {
            return "5 months ago";
        }
        else if (timeGap < WEEK * 28) {
            return "6 months ago";
        }
        else if (timeGap < WEEK * 32) {
            return "7 months ago";
        }
        else if (timeGap < WEEK * 36) {
            return "8 months ago";
        }
        else if (timeGap < WEEK * 40) {
            return "9 months ago";
        }else {
            return time;
        }
    }

    public static boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) KaviApp.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    public static int[] getTimeDiff(String time1, String time2, String format) {
        int diff[] = new int[3];
        try {
            DateFormat sdf = new SimpleDateFormat(format);
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            long difference = date2.getTime() - date1.getTime();
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            hours = (hours < 0 ? -hours : hours);
            diff[0] = days;
            diff[1] = hours;
            diff[2] = min;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }
    public static String getCurrentDateTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateTime = sdf.format(new Date());
        return dateTime;
    }



}
