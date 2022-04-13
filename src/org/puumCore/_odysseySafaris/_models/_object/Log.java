package org.puumCore._odysseySafaris._models._object;

import com.google.gson.GsonBuilder;
import org.puumCore._odysseySafaris._custom.WatchDog;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 14/03/2022
 */

public class Log {

    private String timeStamp;
    private String action;
    private String info;

    public Log() {
        this.timeStamp = new WatchDog().get_time_stamp();
    }

    public Log(String action, String info) {
        this.timeStamp = new WatchDog().get_time_stamp();
        this.action = action;
        this.info = info;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isEmpty() {
        return this.toString().equals("{}");
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this, Log.class);
    }

}
