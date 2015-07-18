package com.liaobb.evernote.common;

import android.content.Context;

import com.liaobb.evernote.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liaobb on 2015/7/13.
 */
public class NoteUtils {

    public final static int NOTE_ADD_EVENT = 0x00;
    public final static int NOTE_UPDATE_EVENT = 0x01;
    public final static int NOTE_CLEARALL_EVENT = 0x02;
    public final static int NOTE_TYPE_UPDATE_EVENT = 0x03;
    public final static int NOTE_RESTORY_DEFAULT_EVENT = 0x04;
    public final static int CHANGE_THEME_EVENT = 0x05;

    public final static int MAX_NOTE_TYPE_COUNT = 0x7;


    public final static int NOTE_LIST_CREATE_TIME_ORDER = 0x00;
    public final static int NOTE_LIST_LAST_EDITTIME_ORDER = 0x01;
    public final static int NOTE_LIST_TITLE_ORDER = 0x02;

    public static final SimpleDateFormat defaultDataFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    public static final SimpleDateFormat hourMinDataFormat = new SimpleDateFormat(" HH:mm:ss ");

    public static String changeToDefaultTimeFormat(long timeInMillis) {
        return defaultDataFormat.format(new Date(timeInMillis));
    }

    public static String changeToGraceTimeFormat(Context context, long timeInMillis) {
        long currentTime = System.currentTimeMillis();
        if (context == null)
            return "";
        Date date = new Date(timeInMillis);
        Date now = new Date(currentTime);

        if (now.getYear() == date.getYear()) {
            if (now.getMonth() == date.getMonth()) {
                if (now.getDate() == date.getDate())
                    return context.getString(R.string.today, hourMinDataFormat.format(date));
                else {
                    return context.getString(R.string.before_day, now.getDate() - date.getDate());
                }
            } else {
                return context.getString(R.string.before_month, now.getMonth() - date.getMonth());
            }
        }
        return context.getString(R.string.before_year, now.getYear() - date.getYear());
    }

}
