package org.puumCore._odysseySafaris._models._object;

import com.google.gson.GsonBuilder;
import org.puumCore._odysseySafaris._custom.Assistant;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 12/04/2022
 */

public class Voucher {

    private Integer id;
    private Hotel hotel;
    private Person client;
    private String status;
    private HeadCount headCount;
    private RoomType roomType;
    private TimeLine timeLine;
    private MealPlan mealPlan;
    private String paidBy;
    private String remarks;
    private Person confirmPerson;

    public Integer getId() {
        return id;
    }

    public Voucher setId(Integer id) {
        this.id = id;
        return this;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Voucher setHotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public Person getClient() {
        return client;
    }

    public String getStatus() {
        return status;
    }

    public Voucher setStatus(String status) {
        this.status = status;
        return this;
    }

    public HeadCount getHeadCount() {
        return headCount;
    }

    public Voucher setHeadCount(HeadCount headCount) {
        this.headCount = headCount;
        return this;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public Voucher setRoomType(RoomType roomType) {
        this.roomType = roomType;
        return this;
    }

    public TimeLine getTimeLine() {
        return timeLine;
    }

    public Voucher setTimeLine(TimeLine timeLine) {
        this.timeLine = timeLine;
        return this;
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }

    public Voucher setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
        return this;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public Voucher setPaidBy(String paidBy) {
        this.paidBy = paidBy;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public Voucher setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Person getConfirmPerson() {
        return confirmPerson;
    }

    public Voucher setClient(Person client) {
        this.client = client.setPersonType(Assistant.PersonType.CLIENT);
        return this;
    }

    public Voucher setConfirmPerson(Person confirmPerson) {
        this.confirmPerson = confirmPerson.setPersonType(Assistant.PersonType.CONFIRM);
        return this;
    }

    public boolean isEmpty() {
        return this.toString().equals("{}");
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this, Voucher.class);
    }
}
