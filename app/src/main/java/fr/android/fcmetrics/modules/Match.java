package fr.android.fcmetrics.modules;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Match
{
    public static ArrayList<Match> eventsList = new ArrayList<>();

    public static ArrayList<Match> eventsForDate(Date date)
    {
        ArrayList<Match> events = new ArrayList<>();

        for(Match event : eventsList)
        {
            if(event.getDate().getDate() == date.getDate() && event.getDate().getMonth() == date.getMonth() && event.getDate().getYear() == date.getYear())
                events.add(event);
        }

        return events;
    }

    public static Match getMatch(String uuid)
    {
        for(Match event : eventsList)
        {
            if(event.getUuid().equals(uuid))
                return event;
        }

        return null;
    }

    private String uuid;
    private String name;
    private Date date;
    private Double latitude, longitude;

    public Match(String name, Date date, double latitude, double longitude)
    {
        this.uuid = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}