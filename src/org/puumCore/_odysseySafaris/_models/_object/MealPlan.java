package org.puumCore._odysseySafaris._models._object;

import com.google.gson.GsonBuilder;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 12/04/2022
 */

public class MealPlan {

    private Integer id;
    private Boolean b_b, h_b, f_b, lunch, dinner, xtra_direct;

    public Integer getId() {
        return id;
    }

    public MealPlan setId(Integer id) {
        this.id = id;
        return this;
    }

    public Boolean getB_b() {
        return b_b;
    }

    public MealPlan setB_b(Boolean b_b) {
        this.b_b = b_b;
        return this;
    }

    public Boolean getH_b() {
        return h_b;
    }

    public MealPlan setH_b(Boolean h_b) {
        this.h_b = h_b;
        return this;
    }

    public Boolean getF_b() {
        return f_b;
    }

    public MealPlan setF_b(Boolean f_b) {
        this.f_b = f_b;
        return this;
    }

    public Boolean getLunch() {
        return lunch;
    }

    public MealPlan setLunch(Boolean lunch) {
        this.lunch = lunch;
        return this;
    }

    public Boolean getDinner() {
        return dinner;
    }

    public MealPlan setDinner(Boolean dinner) {
        this.dinner = dinner;
        return this;
    }

    public Boolean getXtra_direct() {
        return xtra_direct;
    }

    public MealPlan setXtra_direct(Boolean xtra_direct) {
        this.xtra_direct = xtra_direct;
        return this;
    }

    public boolean isEmpty() {
        return this.toString().equals("{}");
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this, MealPlan.class);
    }

}
