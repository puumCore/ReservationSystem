package org.puumCore._odysseySafaris._models._object;

import com.google.gson.GsonBuilder;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 12/04/2022
 */

public class Hotel {

    private Integer id;
    private String name, branch;

    public Integer getId() {
        return id;
    }

    public Hotel setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Hotel setName(String name) {
        this.name = name;
        return this;
    }

    public String getBranch() {
        return branch;
    }

    public Hotel setBranch(String branch) {
        this.branch = branch;
        return this;
    }

    public boolean isEmpty() {
        return this.toString().equals("{}");
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this, Hotel.class);
    }

}
