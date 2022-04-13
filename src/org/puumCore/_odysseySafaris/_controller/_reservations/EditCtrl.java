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
import org.puumCore._odysseySafaris._models._object.Voucher;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 24/03/2022
 */

public class EditCtrl extends Brain implements Initializable {

    protected static Voucher voucher;
    private Voucher myVoucher;

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
    private JFXRadioButton tripplesRB;

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
    private JFXTextField apiTF;

    @FXML
    private JFXRadioButton reserveRB;

    @FXML
    private ToggleGroup status;

    @FXML
    private JFXRadioButton wePayRB;

    @FXML
    private ToggleGroup payment;

    @FXML
    void confirm_reservation(ActionEvent event) {

    }

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void reset(ActionEvent event) {
        clear_textFields(hotelNameTF, branchTF, nameTF, phoneTF, adultsTF, childrenTF, infantsTF, resTF, arrivalDP, arrivalTP, departureDP, departureTP, daysTF, nightsTF);
        for (JFXRadioButton jfxRadioButton : new JFXRadioButton[]{singlesRB, doublesRB, bbRB, hbRB, fbRB, lunchRB, dinnerRB, xtraDirectRB}) {
            if (jfxRadioButton.isSelected()) {
                jfxRadioButton.setSelected(false);
            }
        }
        status.getSelectedToggle().setSelected(false);
        payment.getSelectedToggle().setSelected(false);
        event.consume();
    }

    @FXML
    void save(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accept_only_numbers(adultsTF, childrenTF, infantsTF, resTF, daysTF, nightsTF);

        validation_of_phoneNumber(phoneTF);

        set_my_preferred_date_format(departureDP, arrivalDP);
        set_my_preferred_time_format(departureTP, arrivalTP);

        this.myVoucher = EditCtrl.voucher;
    }
}
