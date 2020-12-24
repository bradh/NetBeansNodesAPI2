package org.myorg.myapi;

import java.time.ZonedDateTime;

public final class Event {

    private final ZonedDateTime date = ZonedDateTime.now();
    private static int count = 0;
    private final int index;

    public Event() {
        index = count++;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return index + " - " + date.toString();
    }
    
    public String getDateAsString() {
        return date.toString();
    }
}
