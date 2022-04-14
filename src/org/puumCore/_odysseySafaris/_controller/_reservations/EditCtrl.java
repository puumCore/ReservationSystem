package org.puumCore._odysseySafaris._controller._reservations;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import org.puumCore._odysseySafaris._custom.Brain;
import org.puumCore._odysseySafaris._models._object.*;

import java.io.IOException;
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

public class EditCtrl extends Brain implements Initializable {

    protected static Voucher voucher;
    private Voucher myVoucher;

    @FXML
    private JFXTextField branchTF;

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
    private JFXTextField remarksTF;

    @FXML
    private JFXRadioButton reserveRB;

    @FXML
    private ToggleGroup status;

    @FXML
    private JFXRadioButton amendRB;

    @FXML
    private JFXRadioButton wePayRB;

    @FXML
    private ToggleGroup payment;

    @FXML
    private JFXRadioButton directClientRB;

    @FXML
    void confirm_reservation(ActionEvent event) {
        if (this.myVoucher == null) {
            warning_message("Failed to save!", "The voucher may already be deleted or some internal error.").show();
            event.consume();
            return;
        }
        try {
            ConfirmationCtrl.voucherId = this.myVoucher.getId();
            Node node = FXMLLoader.load(getClass().getResource("/_ui_ux/_fxml/_reservations/confirmation.fxml"));
            JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
            jfxDialogLayout.setBody(node);
            JFXDialog jfxDialog = new JFXDialog(get_parent_for_popup_dialogue(wePayRB, "workSpacePane"), jfxDialogLayout, JFXDialog.DialogTransition.BOTTOM);
            jfxDialog.setOnDialogClosed(event1 -> jfxDialogLayout.getBody().forEach(node1 -> Platform.runLater(() -> {
                StackPane.clearConstraints(node1);
                jfxDialogLayout.getChildren().remove(node1);
            })));
            jfxDialog.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Thread(write_stack_trace(e)).start();
            Platform.runLater(() -> programmer_error(e).show());
        }
        event.consume();
    }

    @FXML
    void delete(ActionEvent event) {
        if (this.myVoucher == null) {
            warning_message("Failed to save!", "The voucher may already be deleted or some internal error.").show();
            event.consume();
            return;
        }
        if (i_want_to("delete this voucher")) {
            Boolean voucherIsDeleted = delete_voucher_data(this.myVoucher);
            if (voucherIsDeleted == null) {
                error_message("Failed to connect!", "Could not connect to the datasource.").show();
            } else if (voucherIsDeleted) {
                success_notification("The Voucher has been deleted.").show();
                reset(event);
            } else {
                error_message("Failed to save!", "The voucher could not be deleted.").show();
            }
        }
        event.consume();
    }

    @FXML
    void reset(ActionEvent event) {
        clear_textFields(branchTF, adultsTF, childrenTF, infantsTF, resTF, arrivalDP, arrivalTP, departureDP, departureTP, daysTF, nightsTF);
        for (JFXRadioButton jfxRadioButton : new JFXRadioButton[]{singlesRB, doublesRB}) {
            if (jfxRadioButton.isSelected()) {
                jfxRadioButton.setSelected(false);
            }
        }
        if (status.getSelectedToggle() != null)
            status.getSelectedToggle().setSelected(false);
        if (payment.getSelectedToggle() != null)
            payment.getSelectedToggle().setSelected(false);

        show_instance_info();
        event.consume();
    }

    @FXML
    void save(ActionEvent event) {
        if (this.myVoucher == null) {
            warning_message("Failed to save!", "The voucher may already be deleted or some internal error.").show();
            event.consume();
            return;
        }
        if (i_want_to("update details of this voucher")) {
            Voucher voucher = new Voucher();

            Hotel hotel = new Hotel();
            if (branchTF.getText().trim().length() > 0 && branchTF.getText() != null) {
                String param = branchTF.getText().trim();
                if (!this.myVoucher.getHotel().getBranch().equals(param)) {
                    hotel.setBranch(param);
                }
            }
            if (!hotel.isEmpty()) {
                hotel.setId(this.myVoucher.getHotel().getId());
                voucher.setHotel(hotel);
            }

            HeadCount headCount = new HeadCount();
            if (adultsTF.getText().trim().length() > 0 && adultsTF.getText() != null) {
                Integer param = Integer.parseInt(adultsTF.getText().trim());
                if (!this.myVoucher.getHeadCount().getAdults().equals(param)) {
                    headCount.setAdults(param);
                }
            }
            if (childrenTF.getText().trim().length() > 0 && childrenTF.getText() != null) {
                Integer param = Integer.parseInt(childrenTF.getText().trim());
                if (!this.myVoucher.getHeadCount().getChildren().equals(param)) {
                    headCount.setChildren(param);
                }
            }
            if (infantsTF.getText().trim().length() > 0 && infantsTF.getText() != null) {
                Integer param = Integer.parseInt(infantsTF.getText().trim());
                if (!this.myVoucher.getHeadCount().getInfants().equals(param)) {
                    headCount.setInfants(param);
                }
            }
            if (resTF.getText().trim().length() > 0 && resTF.getText() != null) {
                Integer param = Integer.parseInt(resTF.getText().trim());
                if (!this.myVoucher.getHeadCount().getRes().equals(param)) {
                    headCount.setRes(param);
                }
            }
            if (!headCount.isEmpty()) {
                headCount.setId(this.myVoucher.getHeadCount().getId());
                voucher.setHeadCount(headCount);
            }

            TimeLine timeLine = new TimeLine();
            if (arrivalDP.getValue() != null || arrivalTP.getValue() != null) {
                if (arrivalDP.getEditor().getText().trim().length() == 0 || arrivalDP.getEditor().getText() == null) {
                    no_input_warning_message(arrivalDP.getParent().getParent()).show();
                    event.consume();
                    return;
                }
                if (arrivalTP.getEditor().getText().trim().length() == 0 || arrivalTP.getEditor().getText() == null) {
                    no_input_warning_message(arrivalTP.getParent().getParent()).show();
                    event.consume();
                    return;
                }
                String param = String.format("%s %s", arrivalDP.getValue().format(dateFormatter), arrivalTP.getValue().format(timeFormatter));
                if (!this.myVoucher.getTimeLine().getArrival().equals(param)) {
                    timeLine.setArrival(param);
                }
            }
            if (departureDP.getValue() != null || departureTP.getValue() != null) {
                if (departureDP.getEditor().getText().trim().length() == 0 || departureDP.getEditor().getText() == null) {
                    no_input_warning_message(departureDP.getParent().getParent()).show();
                    event.consume();
                    return;
                }
                if (departureTP.getEditor().getText().trim().length() == 0 || departureTP.getEditor().getText() == null) {
                    no_input_warning_message(departureTP.getParent().getParent()).show();
                    event.consume();
                    return;
                }
                String param = String.format("%s %s", departureDP.getValue().format(dateFormatter), departureTP.getValue().format(timeFormatter));
                if (!this.myVoucher.getTimeLine().getDeparture().equals(param)) {
                    timeLine.setDeparture(param);
                }
            }
            if (daysTF.getText().trim().length() > 0 && daysTF.getText() != null) {
                Integer param = Integer.parseInt(daysTF.getText().trim());
                if (!this.myVoucher.getTimeLine().getDays().equals(param)) {
                    timeLine.setDays(param);
                }
            }
            if (nightsTF.getText().trim().length() > 0 && nightsTF.getText() != null) {
                Integer param = Integer.parseInt(nightsTF.getText().trim());
                if (!this.myVoucher.getTimeLine().getNights().equals(param)) {
                    timeLine.setNights(param);
                }
            }
            if (!timeLine.isEmpty()) {
                timeLine.setId(this.myVoucher.getTimeLine().getId());
                voucher.setTimeLine(timeLine);
            }

            RoomType roomType = new RoomType();
            if (this.myVoucher.getRoomType().getSingles() != singlesRB.isSelected()) {
                roomType.setSingles(singlesRB.isSelected());
            }
            if (this.myVoucher.getRoomType().getDoubles() != doublesRB.isSelected()) {
                roomType.setDoubles(doublesRB.isSelected());
            }
            if (this.myVoucher.getRoomType().getTriples() != triplesRB.isSelected()) {
                roomType.setTriples(triplesRB.isSelected());
            }
            if (!roomType.isEmpty()) {
                roomType.setId(this.myVoucher.getRoomType().getId());
                voucher.setRoomType(roomType);
            }

            String selected_status = (status.getSelectedToggle().equals(reserveRB)) ? VoucherStatus.RESERVE.getStatus() : (status.getSelectedToggle().equals(amendRB)) ? VoucherStatus.AMEND.getStatus() : VoucherStatus.CANCEL.getStatus();
            if (!this.myVoucher.getStatus().equals(selected_status)) {
                voucher.setStatus(selected_status);
            }

            if (remarksTF.getText().trim().length() > 0 && remarksTF.getText() != null) {
                String param = remarksTF.getText().trim();
                if (!this.myVoucher.getRemarks().equals(param)) {
                    voucher.setRemarks(param);
                }
            }
            String pay = (payment.getSelectedToggle().equals(wePayRB) ? "Odssey Safaris" : "Client");
            if (!this.myVoucher.getPaidBy().equals(pay)) {
                voucher.setPaidBy(pay);
            }

            if (voucher.isEmpty()) {
                warning_message("No changes detected!", "Please edit at-least one attribute to continue").show();
            } else {
                voucher.setId(this.myVoucher.getId());
                create_log(voucher.getId(), new Log("Update Attempt", "Trying to update a voucher"));
                Boolean updatedVoucher = update_voucher(voucher);
                if (updatedVoucher == null) {
                    error_message("Failed to connect!", "Could not connect to the datasource.").show();
                } else if (updatedVoucher) {
                    create_log(voucher.getId(), new Log("Voucher update", "Updated voucher info"));
                    this.myVoucher = null;
                    success_notification("The Voucher has been updated.").show();
                    reset(event);
                } else {
                    create_log(voucher.getId(), new Log("Voucher update", "Could not update voucher info"));
                    error_message("Failed to save!", "The voucher could not be updated.").show();
                }
            }
        }
        event.consume();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accept_only_numbers(adultsTF, childrenTF, infantsTF, resTF, daysTF, nightsTF);

        set_my_preferred_date_format(departureDP, arrivalDP);
        set_my_preferred_time_format(departureTP, arrivalTP);

        validation_of_time(arrivalTP);
        validation_of_time(departureTP);

        this.myVoucher = EditCtrl.voucher;

        show_instance_info();
    }

    private void show_instance_info() {
        if (this.myVoucher.getHotel().getBranch() != null) {
            branchTF.setText(this.myVoucher.getHotel().getBranch());
        }

        adultsTF.setText(String.format("%d", this.myVoucher.getHeadCount().getAdults()));
        childrenTF.setText(String.format("%d", this.myVoucher.getHeadCount().getChildren()));
        infantsTF.setText(String.format("%d", this.myVoucher.getHeadCount().getInfants()));
        resTF.setText(String.format("%d", this.myVoucher.getHeadCount().getRes()));

        singlesRB.setSelected(this.myVoucher.getRoomType().getSingles());
        doublesRB.setSelected(this.myVoucher.getRoomType().getDoubles());
        triplesRB.setSelected(this.myVoucher.getRoomType().getTriples());

        String[] split = this.myVoucher.getTimeLine().getArrival().split(" ");
        arrivalDP.setValue(LocalDate.parse(split[0], dateFormatter));
        arrivalTP.setValue(LocalTime.parse(split[1], timeFormatter));
        String[] split2 = this.myVoucher.getTimeLine().getDeparture().split(" ");
        departureDP.setValue(LocalDate.parse(split2[0], dateFormatter));
        departureTP.setValue(LocalTime.parse(split2[1], timeFormatter));
        daysTF.setText(String.format("%d", this.myVoucher.getTimeLine().getDays()));
        nightsTF.setText(String.format("%d", this.myVoucher.getTimeLine().getNights()));

        if (this.myVoucher.getPaidBy().equals("Odssey Safaris")) {
            wePayRB.setSelected(true);
        } else {
            directClientRB.setSelected(true);
        }

        if (this.myVoucher.getRemarks() != null) {
            remarksTF.setText(this.myVoucher.getRemarks());
        }

    }

}
