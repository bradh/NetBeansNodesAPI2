package org.myorg.myapi;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class Event {

    private ZonedDateTime date = ZonedDateTime.now();
    private static int count = 0;
    private final int index;
    private List listeners = Collections.synchronizedList(new LinkedList());

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        listeners.add(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        listeners.remove(pcl);
    }

    private void fire(String propertyName, Object old, Object nue) {
        // Passing 0 below on purpose, so you only synchronize for one atomic call:
        PropertyChangeListener[] pcls = (PropertyChangeListener[]) listeners.toArray(new PropertyChangeListener[0]);
        for (PropertyChangeListener pcl : pcls) {
            pcl.propertyChange(new PropertyChangeEvent(this, propertyName, old, nue));
        }
    }

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

    public void setDateFromString(String dateAsString) {
        String oldDate = getDateAsString();
        date = ZonedDateTime.parse(dateAsString);
        fire("date", oldDate, date);
    }
}
