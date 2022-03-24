package org.puumCore._odysseySafaris._controller._reservations;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 24/03/2022
 */

public class EditCtrl {

    @FXML
    private JFXTextField nameTF;

    @FXML
    private JFXTextField phoneTF;

    @FXML
    private JFXTextField emailTF;

    @FXML
    private JFXTextField adultsTF;

    @FXML
    private JFXTextField childrenTF;

    @FXML
    private JFXTextField resTF;

    @FXML
    private JFXTextField nonResTF;

    @FXML
    private JFXTextField remarksTF;

    @FXML
    private JFXRadioButton bbRB;

    @FXML
    private JFXRadioButton hbRB;

    @FXML
    private JFXRadioButton fbRB;

    @FXML
    private JFXRadioButton lubchRB;

    @FXML
    private JFXRadioButton dinnerRB;

    @FXML
    private JFXRadioButton paidByUsRB;

    @FXML
    private ToggleGroup payment;

    @FXML
    private JFXRadioButton reserveRB;

    @FXML
    private ToggleGroup status;

    @FXML
    private JFXRadioButton amendRB;

    @FXML
    void add_units(ActionEvent event) {

    }

    @FXML
    void confirm_reservation(ActionEvent event) {

    }

    @FXML
    void reset(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

    }

}
