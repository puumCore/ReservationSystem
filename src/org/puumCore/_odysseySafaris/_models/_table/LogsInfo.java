package org.puumCore._odysseySafaris._models._table;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 14/04/2022
 */

public class LogsInfo extends RecursiveTreeObject<LogsInfo> {

    private final StringProperty timeStamp;
    private final ObjectProperty<Integer> id;
    private final StringProperty activity;

    public LogsInfo(String timeStamp, Integer id, String activity) {
        this.timeStamp = new SimpleStringProperty(timeStamp);
        this.id = new SimpleObjectProperty<>(id);
        this.activity = new SimpleStringProperty(activity);
    }

    public String getTimeStamp() {
        return timeStamp.get();
    }

    public StringProperty timeStampProperty() {
        return timeStamp;
    }

    public Integer getId() {
        return id.get();
    }

    public ObjectProperty<Integer> idProperty() {
        return id;
    }

    public String getActivity() {
        return activity.get();
    }

    public StringProperty activityProperty() {
        return activity;
    }
}
