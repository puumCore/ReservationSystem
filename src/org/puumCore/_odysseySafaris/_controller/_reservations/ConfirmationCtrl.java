package org.puumCore._odysseySafaris._controller._reservations;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.puumCore._odysseySafaris._custom.Brain;
import org.puumCore._odysseySafaris._models._object.Person;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 24/03/2022
 */

public class ConfirmationCtrl extends Brain implements Initializable {

    protected static int voucherId;
    private int myVoucherId;

    @FXML
    private JFXTextField nameTF;

    @FXML
    private JFXTextField phoneTF;

    @FXML
    void reset(ActionEvent event) {
        clear_textFields(nameTF, phoneTF);
        event.consume();
    }

    @FXML
    void save(ActionEvent event) {
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

        Boolean voucherConfirmation = update_voucher_confirmation(this.myVoucherId, person);
        if (voucherConfirmation == null) {
            error_message("Failed to connect!", "Could not connect to the datasource.").show();
        } else if (voucherConfirmation) {
            success_notification(String.format("Voucher has been confirmed by %s.", person.getName())).show();
            reset(event);
        } else {
            error_message("Failed to save!", "The voucher could not be updated on whom has confirmed it.").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validation_of_phoneNumber(phoneTF);

        this.myVoucherId = ConfirmationCtrl.voucherId;
    }
}
