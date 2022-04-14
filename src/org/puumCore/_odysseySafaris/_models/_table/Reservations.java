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
    private final ObjectProperty<Alert> remarksDisplay;
    private final StringProperty paid_by;

    public Reservations(Integer id, String status, String hotelName, String hotelBranch, String clientName, String clientPhone, Integer adults, Integer children, Integer infants, Integer reservations, Integer nonReservations, String singles, String doubles, String triples, String arrival, String departure, Integer days, Integer nights, Alert remarksDisplay, String paid_by) {
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
        this.remarksDisplay = new SimpleObjectProperty<>(remarksDisplay);
        this.paid_by = new SimpleStringProperty(paid_by);
    }

    public Integer getId() {
        return id.get();
    }

    public ObjectProperty<Integer> idProperty() {
        return id;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getHotelName() {
        return hotelName.get();
    }

    public StringProperty hotelNameProperty() {
        return hotelName;
    }

    public String getHotelBranch() {
        return hotelBranch.get();
    }

    public StringProperty hotelBranchProperty() {
        return hotelBranch;
    }

    public String getClientName() {
        return clientName.get();
    }

    public StringProperty clientNameProperty() {
        return clientName;
    }

    public String getClientPhone() {
        return clientPhone.get();
    }

    public StringProperty clientPhoneProperty() {
        return clientPhone;
    }

    public Integer getAdults() {
        return adults.get();
    }

    public ObjectProperty<Integer> adultsProperty() {
        return adults;
    }

    public ObjectProperty<Integer> childrenProperty() {
        return children;
    }

    public Integer getInfants() {
        return infants.get();
    }

    public ObjectProperty<Integer> infantsProperty() {
        return infants;
    }

    public Integer getReservations() {
        return reservations.get();
    }

    public ObjectProperty<Integer> reservationsProperty() {
        return reservations;
    }

    public Integer getNonReservations() {
        return nonReservations.get();
    }

    public ObjectProperty<Integer> nonReservationsProperty() {
        return nonReservations;
    }

    public String getSingles() {
        return singles.get();
    }

    public StringProperty singlesProperty() {
        return singles;
    }

    public String getDoubles() {
        return doubles.get();
    }

    public StringProperty doublesProperty() {
        return doubles;
    }

    public String getTriples() {
        return triples.get();
    }

    public StringProperty triplesProperty() {
        return triples;
    }

    public String getArrival() {
        return arrival.get();
    }

    public StringProperty arrivalProperty() {
        return arrival;
    }

    public String getDeparture() {
        return departure.get();
    }

    public StringProperty departureProperty() {
        return departure;
    }

    public Integer getDays() {
        return days.get();
    }

    public ObjectProperty<Integer> daysProperty() {
        return days;
    }

    public Integer getNights() {
        return nights.get();
    }

    public ObjectProperty<Integer> nightsProperty() {
        return nights;
    }

    public Alert getRemarksDisplay() {
        return remarksDisplay.get();
    }

    public ObjectProperty<Alert> remarksDisplayProperty() {
        return remarksDisplay;
    }

    public String getPaid_by() {
        return paid_by.get();
    }

    public StringProperty paid_byProperty() {
        return paid_by;
    }
}
