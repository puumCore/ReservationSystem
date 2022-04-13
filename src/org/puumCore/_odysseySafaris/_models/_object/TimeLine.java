package org.puumCore._odysseySafaris._models._object;

import com.google.gson.GsonBuilder;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 12/04/2022
 */

public class TimeLine {

    private Integer id;
    private String arrival, departure;
    private Integer days, nights;

    public Integer getId() {
        return id;
    }

    public TimeLine setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getArrival() {
        return arrival;
    }

    public TimeLine setArrival(String arrival) {
        this.arrival = arrival;
        return this;
    }

    public String getDeparture() {
        return departure;
    }

    public TimeLine setDeparture(String departure) {
        this.departure = departure;
        return this;
    }

    public Integer getDays() {
        return days;
    }

    public TimeLine setDays(Integer days) {
        this.days = days;
        return this;
    }

    public Integer getNights() {
        return nights;
    }

    public TimeLine setNights(Integer nights) {
        this.nights = nights;
        return this;
    }

    public boolean isEmpty() {
        return this.toString().equals("{}");
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this, TimeLine.class);
    }

}
