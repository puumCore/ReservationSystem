package org.puumCore._odysseySafaris._object._logs;

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
        this.timeStamp = new WatchDog().time_stamp();
    }

    public Log(String action, String info) {
        this.timeStamp = new WatchDog().time_stamp();
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
}
