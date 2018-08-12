package com.example.agendacalapplication.Event;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;

import java.util.Calendar;

public class DrawableCalendarEvent extends BaseCalendarEvent {

    private int mDrawableId;

    public DrawableCalendarEvent(long id, int color, String title, String description, String location, long dateStart, long dateEnd, int allDay, String duration, int drawableId) {
        super(id, color, title, description, location, dateStart, dateEnd, allDay, duration);
        this.mDrawableId = drawableId;
    }

    public int getDrawableId() {
        return mDrawableId;
    }

    public void setDrawableId(int drawableId) {
        this.mDrawableId = drawableId;
    }

    @Override
    public CalendarEvent copy() {
        return new DrawableCalendarEvent(this);
    }

    public DrawableCalendarEvent(DrawableCalendarEvent calendarEvent) {
        super(calendarEvent);
        this.mDrawableId = calendarEvent.getDrawableId();
    }

    public DrawableCalendarEvent(long id, int color, String title, String description, String location, long dateStart, long dateEnd, int allDay, String duration) {
        super(id, color, title, description, location, dateStart, dateEnd, allDay, duration);
    }

    public DrawableCalendarEvent(String title, String description, String location, int color, Calendar startTime, Calendar endTime, boolean allDay, int drawableId) {
        super(title, description, location, color, startTime, endTime, allDay);
        this.mDrawableId = drawableId;
    }

    public DrawableCalendarEvent(BaseCalendarEvent calendarEvent) {
        super(calendarEvent);
    }

    public DrawableCalendarEvent(Calendar day, String title) {
        super(day, title);
    }


}
