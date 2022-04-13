package org.puumCore._odysseySafaris._models._table;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 12/04/2022
 */

public class Reservations extends RecursiveTreeObject<Reservations> {

    private final ObjectProperty<Integer> id;
    private final StringProperty status;
    private final StringProperty hotelName;
    private final StringProperty hotelBranch;
    private final StringProperty clientName;
    private final StringProperty clientPhone;
    private final ObjectProperty<Integer> adults;
    private final ObjectProperty<Integer> children;
    private final ObjectProperty<Integer> infants;
    private final ObjectProperty<Integer> reservations;
    private final ObjectProperty<Integer> nonReservations;
    private final StringProperty singles;
    private final StringProperty doubles;
    private final StringProperty triples;
    private final StringProperty arrival;
    private final StringProperty departure;
    private final ObjectProperty<Integer> days;
    private final ObjectProperty<Integer> nights;
    private final StringProperty b_b;
    private final StringProperty h_b;
    private final StringProperty f_b;
    private final StringProperty lunch;
    private final StringProperty dinner;
    private final StringProperty xtra_direct;
    private final ObjectProperty<Alert> remarksDisplay;
    private final StringProperty paid_by;

    public Reservations(Integer id, String status, String hotelName, String hotelBranch, String clientName, String clientPhone, Integer adults, Integer children, Integer infants, Integer reservations, Integer nonReservations, String singles, String doubles, String triples, String arrival, String departure, Integer days, Integer nights, String b_b, String h_b, String f_b, String lunch, String dinner, String xtra_direct, Alert remarksDisplay, String paid_by) {
        this.id = new SimpleObjectProperty<>(id);
        this.status = new SimpleStringProperty(status);
        this.hotelName = new SimpleStringProperty(hotelName);
        this.hotelBranch = new SimpleStringProperty(hotelBranch);
        this.clientName = new SimpleStringProperty(clientName);
        this.clientPhone = new SimpleStringProperty(clientPhone);
        this.adults = new SimpleObjectProperty<>(adults);
        this.children = new SimpleObjectProperty<>(children);
        this.infants = new SimpleObjectProperty<>(infants);
        this.reservations = new SimpleObjectProperty<>(reservations);
        this.nonReservations = new SimpleObjectProperty<>(nonReservations);
        this.singles = new SimpleStringProperty(singles);
        this.doubles = new SimpleStringProperty(doubles);
        this.triples = new SimpleStringProperty(triples);
        this.arrival = new SimpleStringProperty(arrival);
        this.departure = new SimpleStringProperty(departure);
        this.days = new SimpleObjectProperty<>(days);
        this.nights = new SimpleObjectProperty<>(nights);
        this.b_b = new SimpleStringProperty(b_b);
        this.h_b = new SimpleStringProperty(h_b);
        this.f_b = new SimpleStringProperty(f_b);
        this.lunch = new SimpleStringProperty(lunch);
        this.dinner = new SimpleStringProperty(dinner);
        this.xtra_direct = new SimpleStringProperty(xtra_direct);
        this.remarksDisplay = new SimpleObjectProperty<>(remarksDisplay);
        this.paid_by = new SimpleStringProperty(paid_by);
    }

    public int getId() {
        return id.get();
    }

    public ObjectProperty<Integer> idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getHotelName() {
        return hotelName.get();
    }

    public StringProperty hotelNameProperty() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName.set(hotelName);
    }

    public String getHotelBranch() {
        return hotelBranch.get();
    }

    public StringProperty hotelBranchProperty() {
        return hotelBranch;
    }

    public void setHotelBranch(String hotelBranch) {
        this.hotelBranch.set(hotelBranch);
    }

    public String getClientName() {
        return clientName.get();
    }

    public StringProperty clientNameProperty() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName.set(clientName);
    }

    public String getClientPhone() {
        return clientPhone.get();
    }

    public StringProperty clientPhoneProperty() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone.set(clientPhone);
    }

    public int getAdults() {
        return adults.get();
    }

    public ObjectProperty<Integer> adultsProperty() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults.set(adults);
    }

    public ObjectProperty<Integer> childrenProperty() {
        return children;
    }

    public void setChildren(int children) {
        this.children.set(children);
    }

    public int getInfants() {
        return infants.get();
    }

    public ObjectProperty<Integer> infantsProperty() {
        return infants;
    }

    public void setInfants(int infants) {
        this.infants.set(infants);
    }

    public int getReservations() {
        return reservations.get();
    }

    public ObjectProperty<Integer> reservationsProperty() {
        return reservations;
    }

    public void setReservations(int reservations) {
        this.reservations.set(reservations);
    }

    public int getNonReservations() {
        return nonReservations.get();
    }

    public ObjectProperty<Integer> nonReservationsProperty() {
        return nonReservations;
    }

    public void setNonReservations(int nonReservations) {
        this.nonReservations.set(nonReservations);
    }

    public String getSingles() {
        return singles.get();
    }

    public StringProperty singlesProperty() {
        return singles;
    }

    public void setSingles(String singles) {
        this.singles.set(singles);
    }

    public String getDoubles() {
        return doubles.get();
    }

    public StringProperty doublesProperty() {
        return doubles;
    }

    public void setDoubles(String doubles) {
        this.doubles.set(doubles);
    }

    public String getTriples() {
        return triples.get();
    }

    public StringProperty triplesProperty() {
        return triples;
    }

    public void setTriples(String triples) {
        this.triples.set(triples);
    }

    public String getArrival() {
        return arrival.get();
    }

    public StringProperty arrivalProperty() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival.set(arrival);
    }

    public String getDeparture() {
        return departure.get();
    }

    public StringProperty departureProperty() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure.set(departure);
    }

    public int getDays() {
        return days.get();
    }

    public ObjectProperty<Integer> daysProperty() {
        return days;
    }

    public void setDays(int days) {
        this.days.set(days);
    }

    public int getNights() {
        return nights.get();
    }

    public ObjectProperty<Integer> nightsProperty() {
        return nights;
    }

    public void setNights(int nights) {
        this.nights.set(nights);
    }

    public String getB_b() {
        return b_b.get();
    }

    public StringProperty b_bProperty() {
        return b_b;
    }

    public void setB_b(String b_b) {
        this.b_b.set(b_b);
    }

    public String getH_b() {
        return h_b.get();
    }

    public StringProperty h_bProperty() {
        return h_b;
    }

    public void setH_b(String h_b) {
        this.h_b.set(h_b);
    }

    public String getF_b() {
        return f_b.get();
    }

    public StringProperty f_bProperty() {
        return f_b;
    }

    public void setF_b(String f_b) {
        this.f_b.set(f_b);
    }

    public String getLunch() {
        return lunch.get();
    }

    public StringProperty lunchProperty() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch.set(lunch);
    }

    public String getDinner() {
        return dinner.get();
    }

    public StringProperty dinnerProperty() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner.set(dinner);
    }

    public String getXtra_direct() {
        return xtra_direct.get();
    }

    public StringProperty xtra_directProperty() {
        return xtra_direct;
    }

    public void setXtra_direct(String xtra_direct) {
        this.xtra_direct.set(xtra_direct);
    }

    public Alert getRemarksDisplay() {
        return remarksDisplay.get();
    }

    public ObjectProperty<Alert> remarksDisplayProperty() {
        return remarksDisplay;
    }

    public void setRemarksDisplay(Alert remarksDisplay) {
        this.remarksDisplay.set(remarksDisplay);
    }

    public String getPaid_by() {
        return paid_by.get();
    }

    public StringProperty paid_byProperty() {
        return paid_by;
    }

    public void setPaid_by(String paid_by) {
        this.paid_by.set(paid_by);
    }

}
