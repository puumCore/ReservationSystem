package org.puumCore._odysseySafaris._models._object;

import com.google.gson.GsonBuilder;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 12/04/2022
 */

public class RoomType {

    private Integer id;
    private Boolean singles, doubles, triples;

    public Integer getId() {
        return id;
    }

    public RoomType setId(Integer id) {
        this.id = id;
        return this;
    }

    public Boolean getSingles() {
        return singles;
    }

    public RoomType setSingles(Boolean singles) {
        this.singles = singles;
        return this;
    }

    public Boolean getDoubles() {
        return doubles;
    }

    public RoomType setDoubles(Boolean doubles) {
        this.doubles = doubles;
        return this;
    }

    public Boolean getTriples() {
        return triples;
    }

    public RoomType setTriples(Boolean triples) {
        this.triples = triples;
        return this;
    }

    public boolean isEmpty() {
        return this.toString().equals("{}");
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this, RoomType.class);
    }

}
