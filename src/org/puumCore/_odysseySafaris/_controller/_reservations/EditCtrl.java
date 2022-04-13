package org.puumCore._odysseySafaris._controller._reservations;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import org.puumCore._odysseySafaris._custom.Assistant;
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

    }

    @FXML
    void save(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accept_only_numbers(adultsTF, childrenTF, infantsTF, resTF);

        this.myVoucher = EditCtrl.voucher;
    }
}
