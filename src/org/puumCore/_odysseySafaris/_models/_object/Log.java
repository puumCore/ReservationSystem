package org.puumCore._odysseySafaris._models._object;

import com.google.gson.GsonBuilder;
import org.puumCore._odysseySafaris._custom.WatchDog;

import java.util.Arrays;
import java.util.List;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 14/03/2022
 */

public class Log {

    private final String timeStamp;
    private String action;
    private List<String> info;

    public Log() {
        this.timeStamp = new WatchDog().get_time_stamp();
    }

    public Log(String action, String... info) {
        this.timeStamp = new WatchDog().get_time_stamp();
        this.action = action;
        this.info = Arrays.asList(info.clone());
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getAction() {
        return action;
    }

    public Log setAction(String action) {
        this.action = action;
        return this;
    }

    public List<String> getInfo() {
        return info;
    }

    public Log setInfo(List<String> info) {
        this.info = info;
        return this;
    }

    public boolean isEmpty() {
        return this.toString().equals("{}");
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this, Log.class);
    }

}
