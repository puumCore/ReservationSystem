package org.puumCore._odysseySafaris._controller._reservations;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import org.puumCore._odysseySafaris._custom.Brain;
import org.puumCore._odysseySafaris._models._object.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 24/03/2022
 */

public class AddCtrl extends Brain implements Initializable {

    @FXML
    private JFXTextField hotelNameTF;

    @FXML
    private JFXTextField branchTF;

    @FXML
    private JFXTextField nameTF;

    @FXML
    private JFXTextField phoneTF;

    @FXML
    private JFXTextField adultsTF;

    @FXML
    private JFXTextField childrenTF;

    @FXML
    private JFXTextField infantsTF;

    @FXML
    private JFXTextField resTF;

    @FXML
    private JFXRadioButton singlesRB;

    @FXML
    private JFXRadioButton doublesRB;

    @FXML
    private JFXRadioButton triplesRB;

    @FXML
    private JFXDatePicker departureDP;

    @FXML
    private JFXTimePicker departureTP;

    @FXML
    private JFXDatePicker arrivalDP;

    @FXML
    private JFXTimePicker arrivalTP;

    @FXML
    private JFXTextField daysTF;

    @FXML
    private JFXTextField nightsTF;

    @FXML
    private JFXRadioButton bbRB;

    @FXML
    private JFXRadioButton hbRB;

    @FXML
    private JFXRadioButton fbRB;

    @FXML
    private JFXRadioButton lunchRB;

    @FXML
    private JFXRadioButton dinnerRB;

    @FXML
    private JFXRadioButton xtraDirectRB;

    @FXML
    private JFXTextField remarksTF;

    @FXML
    private JFXRadioButton reserveRB;

    @FXML
    private ToggleGroup status;

    @FXML
    private JFXRadioButton wePayRB;

    @FXML
    private ToggleGroup payment;

    @FXML
    void reset(ActionEvent event) {
        clear_textFields(hotelNameTF, branchTF, nameTF, phoneTF, adultsTF, childrenTF, infantsTF, resTF, arrivalDP, arrivalTP, departureDP, departureTP, daysTF, nightsTF, remarksTF);
        for (JFXRadioButton jfxRadioButton : new JFXRadioButton[]{singlesRB, doublesRB, triplesRB, bbRB, hbRB, fbRB, lunchRB, dinnerRB, xtraDirectRB}) {
            if (jfxRadioButton.isSelected()) {
                jfxRadioButton.setSelected(false);
            }
        }
        status.getSelectedToggle().setSelected(false);
        payment.getSelectedToggle().setSelected(false);

        arrivalDP.setValue(LocalDate.now());
        arrivalTP.setValue(LocalTime.now());
        event.consume();
    }

    @FXML
    void save(ActionEvent event) {
        Hotel hotel = new Hotel();
        if (hotelNameTF.getText().trim().length() == 0 || hotelNameTF.getText() == null) {
            no_input_warning_message(hotelNameTF.getParent().getParent()).show();
            event.consume();
            return;
        }
        hotel.setName(hotelNameTF.getText().trim());
        if (branchTF.getText().trim().length() > 0 && branchTF.getText() != null) {
            hotel.setBranch(branchTF.getText().trim());
        }

        Person person = new Person();
        if (nameTF.getText().trim().length() == 0 || nameTF.getText() == null) {
            no_input_warning_message(nameTF.getParent().getParent()).show();
            event.consume();
            return;
        }
        person.setName(nameTF.getText().trim());
        if (phoneTF.getText().trim().length() == 0 || phoneTF.getText() == null) {
            no_input_warning_message(phoneTF.getParent().getParent()).show();
            event.consume();
            return;
        }
        if (!phoneNumber_is_in_correct_format(phoneTF.getText().trim())) {
            warning_message("Faulty phone!", "Please provide a valid phone number.").show();
            event.consume();
            return;
        }
        person.setPhone(phoneTF.getText().trim());

        HeadCount headCount = new HeadCount();
        if (adultsTF.getText().trim().length() > 0 && adultsTF.getText() != null) {
            headCount.setAdults(Integer.parseInt(adultsTF.getText().trim()));
        }

        if (childrenTF.getText().trim().length() > 0 && childrenTF.getText() != null) {
            headCount.setChildren(Integer.parseInt(childrenTF.getText().trim()));
        }

        if (infantsTF.getText().trim().length() > 0 && infantsTF.getText() != null) {
            headCount.setInfants(Integer.parseInt(infantsTF.getText().trim()));
        }
        if (headCount.getAdults() + headCount.getChildren() + headCount.getInfants() <= 0) {
            warning_message("Check Client count!", "Zero clients detected.").show();
            event.consume();
            return;
        }
        if (resTF.getText().trim().length() > 0 && resTF.getText() != null) {
            headCount.setRes(Integer.parseInt(resTF.getText().trim()));
        }
        if (headCount.getRes() <= 0) {
            warning_message("Check Client count!", "The client has not provided a number of reservations.").show();
            event.consume();
            return;
        }

        RoomType roomType = new RoomType();
        roomType.setSingles(singlesRB.isSelected());
        roomType.setDoubles(doublesRB.isSelected());
        roomType.setTriples(triplesRB.isSelected());

        TimeLine timeLine = new TimeLine();
        if (departureDP.getValue() == null) {
            no_input_warning_message(departureDP.getParent().getParent()).show();
            event.consume();
            return;
        }
        if (departureTP.getEditor().getText().trim().length() == 0 || departureTP.getEditor().getText() == null) {
            no_input_warning_message(departureTP.getParent().getParent()).show();
            event.consume();
            return;
        }
        timeLine.setDeparture(String.format("%s %s", dateFormatter.format(departureDP.getValue()), timeFormatter.format(departureTP.getValue())));
        if (arrivalDP.getValue() == null) {
            no_input_warning_message(arrivalDP.getParent().getParent()).show();
            event.consume();
            return;
        }
        if (arrivalTP.getEditor().getText().trim().length() == 0 || arrivalTP.getEditor().getText() == null) {
            no_input_warning_message(arrivalTP.getParent().getParent()).show();
            event.consume();
            return;
        }
        timeLine.setArrival(String.format("%s %s", dateFormatter.format(arrivalDP.getValue()), timeFormatter.format(arrivalTP.getValue())));
        if (daysTF.getText().trim().length() == 0 || daysTF.getText() == null) {
            no_input_warning_message(daysTF.getParent().getParent()).show();
            event.consume();
            return;
        }
        timeLine.setDays(Integer.parseInt(daysTF.getText().trim()));
        if (nightsTF.getText().trim().length() == 0 || nightsTF.getText() == null) {
            no_input_warning_message(nightsTF.getParent().getParent()).show();
            event.consume();
            return;
        }
        timeLine.setNights(Integer.parseInt(nightsTF.getText().trim()));

        MealPlan mealPlan = new MealPlan();
        mealPlan.setB_b(bbRB.isSelected());
        mealPlan.setH_b(hbRB.isSelected());
        mealPlan.setF_b(fbRB.isSelected());
        mealPlan.setLunch(lunchRB.isSelected());
        mealPlan.setDinner(dinnerRB.isSelected());
        mealPlan.setXtra_direct(xtraDirectRB.isSelected());

        Voucher voucher = new Voucher();
        if (remarksTF.getText().trim().length() > 0 && remarksTF.getText() != null) {
            voucher.setRemarks(remarksTF.getText().trim());
        }
        voucher.setStatus((status.getSelectedToggle().equals(reserveRB)) ? VoucherStatus.RESERVE.getStatus() : VoucherStatus.AMEND.getStatus());
        voucher.setPaidBy(payment.getSelectedToggle().equals(wePayRB) ? "Odssey Safaris" : "Client");

        voucher.setHotel(hotel);
        voucher.setClient(person);
        voucher.setHeadCount(headCount);
        voucher.setRoomType(roomType);
        voucher.setTimeLine(timeLine);
        voucher.setMealPlan(mealPlan);

        Boolean createdVoucher = create_voucher(voucher);
        if (createdVoucher == null) {
            error_message("Failed to connect!", "Could not connect to the datasource.").show();
        } else if (createdVoucher) {
            success_notification(String.format("Voucher %d to %s %s has been created, refresh table to see changes.", voucher.getId(), voucher.getHotel().getName(), (voucher.getHotel().getBranch() == null ? "" : voucher.getHotel().getBranch()))).show();
            reset(event);
        } else {
            error_message("Failed to save!", "The details about the voucher could not be saved.").show();
        }
        event.consume();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accept_only_numbers(adultsTF, childrenTF, infantsTF, resTF, daysTF, nightsTF);

        validation_of_phoneNumber(phoneTF);
        validation_of_time(arrivalTP);
        validation_of_time(departureTP);

        set_my_preferred_date_format(departureDP, arrivalDP);
        set_my_preferred_time_format(departureTP, arrivalTP);

        arrivalDP.setValue(LocalDate.now());
        arrivalTP.setValue(LocalTime.now());
    }

}
