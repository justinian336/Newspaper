package com.github.ayltai.newspaper.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.ayltai.newspaper.R;

public final class DateUtils {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * DateUtils.SECOND_MILLIS;
    private static final int HOUR_MILLIS   = 60 * DateUtils.MINUTE_MILLIS;
    private static final int DAY_MILLIS    = 24 * DateUtils.HOUR_MILLIS;

    private DateUtils() {
    }

    @NonNull
    public static String getTimeAgo(@NonNull final Context context, final long time) {
        final long now = System.currentTimeMillis();

        if (time > now || time <= 0) return null;

        // TODO: localize
        final long diff = now - time;

        if (diff < DateUtils.MINUTE_MILLIS) {
            return context.getString(R.string.time_ago_now);
        }

        if (diff < 2 * DateUtils.MINUTE_MILLIS) {
            return context.getString(R.string.time_ago_minute);
        }

        if (diff < 50 * DateUtils.MINUTE_MILLIS) {
            return String.format(context.getString(R.string.time_ago_minutes), String.valueOf(Math.round(diff / DateUtils.MINUTE_MILLIS)));
        }

        if (diff < 90 * DateUtils.MINUTE_MILLIS) {
            return context.getString(R.string.time_ago_hour);
        }

        if (diff < 24 * DateUtils.HOUR_MILLIS) {
            return String.format(context.getString(R.string.time_ago_hours), String.valueOf(Math.round(diff / DateUtils.HOUR_MILLIS)));
        }

        if (diff < 48 * DateUtils.HOUR_MILLIS) {
            return context.getString(R.string.time_ago_day);
        }

        return String.format(context.getString(R.string.time_ago_days), String.valueOf(Math.round(diff / DateUtils.DAY_MILLIS)));
    }
}
