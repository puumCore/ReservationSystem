package org.puumCore._odysseySafaris._models._object;

import com.google.gson.GsonBuilder;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 12/04/2022
 */

public class HeadCount {

    private Integer id, adults, children, infants, res;

    public Integer getId() {
        return id;
    }

    public HeadCount setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getAdults() {
        return adults;
    }

    public HeadCount setAdults(Integer adults) {
        this.adults = adults;
        return this;
    }

    public Integer getChildren() {
        return children;
    }

    public HeadCount setChildren(Integer children) {
        this.children = children;
        return this;
    }

    public Integer getInfants() {
        return infants;
    }

    public HeadCount setInfants(Integer infants) {
        this.infants = infants;
        return this;
    }

    public Integer getRes() {
        return res;
    }

    public HeadCount setRes(Integer res) {
        this.res = res;
        return this;
    }

    public Integer getNonRes() {
        return ((this.getAdults() + this.getChildren() + this.getInfants()) - this.getRes());
    }

    public boolean isEmpty() {
        return this.toString().equals("{}");
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this, HeadCount.class);
    }

}
