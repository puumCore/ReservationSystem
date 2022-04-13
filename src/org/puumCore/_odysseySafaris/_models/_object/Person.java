package org.puumCore._odysseySafaris._models._object;

import com.google.gson.GsonBuilder;
import org.puumCore._odysseySafaris._custom.Assistant;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 12/04/2022
 */

public class Person {

    private Integer id;
    private Assistant.PersonType personType;
    private String name, phone;

    public Integer getId() {
        return id;
    }

    public Person setId(Integer id) {
        this.id = id;
        return this;
    }

    public Assistant.PersonType getPersonType() {
        return personType;
    }

    public Person setPersonType(Assistant.PersonType personType) {
        this.personType = personType;
        return this;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Person setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public boolean isEmpty() {
        return this.toString().equals("{}");
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this, Person.class);
    }
}
