package com.example.agendacalapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.agendacalapplication.Event.DrawableCalendarEvent;
import com.example.agendacalapplication.Event.DrawableEventRenderer;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarManager;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.calendar.CalendarView;
import com.github.tibolte.agendacalendarview.calendar.weekslist.WeeksAdapter;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.github.tibolte.agendacalendarview.models.WeekItem;
import com.github.tibolte.agendacalendarview.render.EventRenderer;
import com.github.tibolte.agendacalendarview.utils.Events;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class AgendaActivity extends AppCompatActivity implements CalendarPickerController {

    private AgendaCalendarView mAgendaCalendarView;
    private AppCompatTextView mDateChangedAcTv;
    private static final String TAG = "MY_TAG";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        mContext = AgendaActivity.this;
        initView();


        /*minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);*/
        long startDate = 0;
        long endDate = 0;

        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        try {
            startDate = getDateFormat("2018-01-01", "yyyy-MM-dd");
            endDate = getDateFormat("2018-12-01", "yyyy-MM-dd");
        } catch (ParseException e) {
            Log.d(TAG, "Date Error: " + e.getMessage());
        }

        if (startDate != 0 && endDate != 0) {
            minDate.setTime(new Date(startDate));
            maxDate.setTime(new Date(endDate));
        }

        List<CalendarEvent> eventList = new ArrayList<>();
        mockList(eventList);

        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());

    }

    private void initView() {
        mAgendaCalendarView = findViewById(R.id.agenda_calendar_view);
        mDateChangedAcTv = findViewById(R.id.date_change_text_view);

    }

    @Override
    public void onDaySelected(DayItem dayItem) {
        Log.d(TAG, "onDaySelected: " + dayItem.getDate().toString());
        String dateString = getDate(dayItem.getDate().getTime());
        mDateChangedAcTv.setText(dateString);
    }

    @Override
    public void onEventSelected(CalendarEvent event) {
        Toast.makeText(mContext, "Date: " + event.getTitle()
                        + "\n Id: " + event.getId()
                , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onScrollToDate(Calendar calendar) {
        String dateString = getDate(calendar.getTime().getTime());
        //String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        mDateChangedAcTv.setText(dateString);
        Log.d(TAG, "onScrollToDate: " + calendar.getTime().toString());
    }

    private void mockList(List<CalendarEvent> eventList) {
        try {
            Date date1 = new Date(getDateFormat("2018-08-10", "yyyy-MM-dd"));
            BaseCalendarEvent event1 = getBaseCalendarEvent(
                    date1,
                    "Assigned by user",
                    "A wonderful journey!",
                    "User Location",
                    ContextCompat.getColor(mContext, android.R.color.holo_red_light),
                    false);
            event1.setId(11);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            DayItem dayItem1 = new DayItem(date1, calendar.get(Calendar.DAY_OF_MONTH), false, month);
            dayItem1.setSelected(true);
            event1.setDayReference(dayItem1);
            eventList.add(event1);


            BaseCalendarEvent event2 = getBaseCalendarEvent(
                    new Date(getDateFormat("2018-08-10", "yyyy-MM-dd")),
                    "Assigned by admin",
                    "A wonderful journey!",
                    "Admin Location",
                    ContextCompat.getColor(mContext, R.color.snack_bar_color_success),
                    false);
            event2.setId(13);
            /*DayItem dayItem2 = event2.getDayReference();
            dayItem2.setSelected(true);*/
            eventList.add(event2);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(TAG, "Date: " + e.getMessage());
        }

        Calendar startTime2 = Calendar.getInstance();
        Calendar endTime2 = Calendar.getInstance();
        // startTime2.add(Calendar.DAY_OF_YEAR, 1);
        // endTime2.add(Calendar.DAY_OF_YEAR, 3);
        // endTime2.add(Calendar.DAY_OF_YEAR, 3);
        try {
            startTime2.setTime(new Date(getDateFormat("2018-08-09", "yyyy-MM-dd")));
            endTime2.setTime(new Date(getDateFormat("2018-08-09", "yyyy-MM-dd")));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(TAG, "Date: " + e.getMessage());
        }


        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Assigned by admin", "A beautiful small town", "Admin Location",
                ContextCompat.getColor(this, R.color.snack_bar_color_success), startTime2, endTime2, false
                , R.drawable.ic_weather);
        event3.setId(12);
        eventList.add(event3);

        // Example on how to provide your own layout
        /*Calendar startTime3 = Calendar.getInstance();
        Calendar endTime3 = Calendar.getInstance();
        startTime3.set(Calendar.HOUR_OF_DAY, 14);
        startTime3.set(Calendar.MINUTE, 0);
        endTime3.set(Calendar.HOUR_OF_DAY, 15);
        endTime3.set(Calendar.MINUTE, 0);
        BaseCalendarEvent event3 = new BaseCalendarEvent("Visit to Dalvík", "A beautiful small town", "Dalvík",
                ContextCompat.getColor(this, R.color.calendar_text_default), startTime3, endTime3, true);
        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Visit of Harpa", "", "Dalvík",
                ContextCompat.getColor(this, R.color.colorPrimary),
                startTime3, endTime3, true);
        eventList.add(event3);*/
    }

    @NonNull
    private BaseCalendarEvent getBaseCalendarEvent(@NonNull Date date, @NonNull String title, @NonNull String description, @NonNull String location, int color, boolean isAllDay) {
        Calendar time = Calendar.getInstance();

        time.setTime(date);

        return new BaseCalendarEvent(title, description, location,
                color, time, time, isAllDay);
    }

    /**
     * Get Date object in specified format
     *
     * @param dateString -> date in String format
     * @param dateFormat -> Default Date format
     * @return -> will return the Date long object
     */
    private long getDateFormat(@NonNull String dateString, @NonNull String dateFormat) throws ParseException {
        /*Use date format as according to your need! Ex. - yyyy/MM/dd HH:mm:ss */
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat/*"yyyy-MM-dd HH:mm:ss"*/, Locale.getDefault());
        //long millis = date.getTime();

        return sdf.parse(dateString).getTime();
    }

    /**
     * get Date in string format
     *
     * @param timestamp -> date timestamp
     * @return -> returns date in string format (day month year)
     */
    private String getDate(long timestamp) {
        Date dateObj = new Date(timestamp);

        String dayS = new SimpleDateFormat("dd", Locale.ENGLISH).format(dateObj);
        String monthS = new SimpleDateFormat("MM", Locale.ENGLISH).format(dateObj);
        String yearS = new SimpleDateFormat("yyyy", Locale.ENGLISH).format(dateObj);

        int day = Integer.parseInt(dayS);
        int month = Integer.parseInt(monthS);
        int year = Integer.parseInt(yearS);


        String date;
        String monthStr = null;
        switch (month) {
            case 1:
                monthStr = "January";
                break;
            case 2:
                monthStr = "February";
                break;
            case 3:
                monthStr = "March";
                break;
            case 4:
                monthStr = "April";
                break;
            case 5:
                monthStr = "May";
                break;
            case 6:
                monthStr = "June";
                break;
            case 7:
                monthStr = "July";
                break;
            case 8:
                monthStr = "August";
                break;
            case 9:
                monthStr = "September";
                break;
            case 10:
                monthStr = "October";
                break;
            case 11:
                monthStr = "November";
                break;
            case 12:
                monthStr = "December";
                break;
        }

        date = day + " " + monthStr + " " + year;

        return date;
    }
}
