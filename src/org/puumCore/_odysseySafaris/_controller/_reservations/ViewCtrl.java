package org.puumCore._odysseySafaris._controller._reservations;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import org.puumCore._odysseySafaris._custom.Brain;
import org.puumCore._odysseySafaris._interface.ViewingService;
import org.puumCore._odysseySafaris._models._object.Voucher;
import org.puumCore._odysseySafaris._models._table.Reservations;
import org.puumCore._odysseySafaris._outsourced.ActionButtonTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 24/03/2022
 */

public class ViewCtrl extends Brain implements ViewingService {

    private final JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();

    @FXML
    private JFXTextField searchTF;

    @FXML
    private JFXTreeTableView<Reservations> treeTable;

    @FXML
    private TreeTableColumn<Reservations, Integer> voucherIdCol;

    @FXML
    private TreeTableColumn<Reservations, String> hotelNameCol;

    @FXML
    private TreeTableColumn<Reservations, String> hotelBranchCol;

    @FXML
    private TreeTableColumn<Reservations, String> statusCol;

    @FXML
    private TreeTableColumn<Reservations, String> paymentInfoCol;

    @FXML
    private TreeTableColumn<Reservations, Integer> adultsCol;

    @FXML
    private TreeTableColumn<Reservations, Integer> childrenCol;

    @FXML
    private TreeTableColumn<Reservations, Integer> infantCol;

    @FXML
    private TreeTableColumn<Reservations, Integer> resCol;

    @FXML
    private TreeTableColumn<Reservations, Integer> nonResCol;

    @FXML
    private TreeTableColumn<Reservations, String> singlesCol;

    @FXML
    private TreeTableColumn<Reservations, String> doublesCol;

    @FXML
    private TreeTableColumn<Reservations, String> triplesCol;

    @FXML
    private TreeTableColumn<Reservations, String> arrivalCol;

    @FXML
    private TreeTableColumn<Reservations, String> departureCol;

    @FXML
    private TreeTableColumn<Reservations, Integer> daysCol;

    @FXML
    private TreeTableColumn<Reservations, Integer> nightsCol;

    @FXML
    private TreeTableColumn<Reservations, String> bbCol;

    @FXML
    private TreeTableColumn<Reservations, String> hbCol;

    @FXML
    private TreeTableColumn<Reservations, String> fbCol;

    @FXML
    private TreeTableColumn<Reservations, String> lunchCol;

    @FXML
    private TreeTableColumn<Reservations, String> dinnerCol;

    @FXML
    private TreeTableColumn<Reservations, String> xtraCol;

    @FXML
    private TreeTableColumn<Reservations, JFXButton> remarksCol;

    @FXML
    private TreeTableColumn<Reservations, JFXButton> updateCol;

    @FXML
    private TreeTableColumn<Reservations, JFXButton> downloadCol;

    @FXML
    void find(ActionEvent event) {
        if (searchTF.getText().trim().length() == 0 || searchTF.getText() == null) {
            no_input_warning_message(searchTF.getParent().getParent()).show();
            event.consume();
            return;
        }
        String param = searchTF.getText().trim();
        if (param.equals(ALL_WILDCARD)) {
            param = null;
        }
        List<Reservations> reservationsBasedOnParam = get_reservations_based_on_param(param);
        if (reservationsBasedOnParam == null) {
            error_message("Failed to connect!", "Could not connect to the datasource.").show();
        } else if (reservationsBasedOnParam.isEmpty()) {
            warning_message("No results found!", "Please update your parameters").show();
        } else {
            load_runnable_into_a_thread(show_results(reservationsBasedOnParam)).start();
            String message = (reservationsBasedOnParam.size() == 1) ? "One Result found!" : String.format("%d Results found!", reservationsBasedOnParam.size());
            success_notification(message).show();
        }
        event.consume();
    }

    @Override
    public Runnable auto_update_search_suggestions() {
        return () -> {
            Set<String> voucherSearchSuggestions = get_voucher_search_suggestions();
            if (voucherSearchSuggestions != null) {
                ObservableList<String> stringObservableList = FXCollections.observableArrayList();
                stringObservableList.addAll(voucherSearchSuggestions);
                stringObservableList.add(ALL_WILDCARD);
                update_suggestions(autoCompletePopup, stringObservableList);
            }
        };

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        create_autocompletion_feature_for_textField(autoCompletePopup, searchTF);

        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(auto_update_search_suggestions(), 1, 10, TimeUnit.SECONDS);
        load_runnable_into_a_thread(show_results(get_reservations_based_on_param(null))).start();
    }


    private Runnable show_results(List<Reservations> reservationsList) {
        return () -> {
            if (reservationsList != null && reservationsList.size() > 0) {
                JFXTreeTableView<Reservations> jfxTreeTableView = treeTable;
                voucherIdCol.setCellValueFactory(param -> param.getValue().getValue().idProperty());

                hotelNameCol.setCellValueFactory(param -> param.getValue().getValue().hotelNameProperty());
                hotelBranchCol.setCellValueFactory(param -> param.getValue().getValue().hotelBranchProperty());

                statusCol.setCellValueFactory(param -> param.getValue().getValue().statusProperty());

                paymentInfoCol.setCellValueFactory(param -> param.getValue().getValue().paid_byProperty());

                adultsCol.setCellValueFactory(param -> param.getValue().getValue().adultsProperty());
                childrenCol.setCellValueFactory(param -> param.getValue().getValue().childrenProperty());
                infantCol.setCellValueFactory(param -> param.getValue().getValue().infantsProperty());
                resCol.setCellValueFactory(param -> param.getValue().getValue().reservationsProperty());
                nonResCol.setCellValueFactory(param -> param.getValue().getValue().nonReservationsProperty());

                singlesCol.setCellValueFactory(param -> param.getValue().getValue().singlesProperty());
                doublesCol.setCellValueFactory(param -> param.getValue().getValue().doublesProperty());
                triplesCol.setCellValueFactory(param -> param.getValue().getValue().triplesProperty());

                arrivalCol.setCellValueFactory(param -> param.getValue().getValue().singlesProperty());
                departureCol.setCellValueFactory(param -> param.getValue().getValue().singlesProperty());
                daysCol.setCellValueFactory(param -> param.getValue().getValue().daysProperty());
                nightsCol.setCellValueFactory(param -> param.getValue().getValue().nightsProperty());

                bbCol.setCellValueFactory(param -> param.getValue().getValue().b_bProperty());
                hbCol.setCellValueFactory(param -> param.getValue().getValue().h_bProperty());
                fbCol.setCellValueFactory(param -> param.getValue().getValue().f_bProperty());
                lunchCol.setCellValueFactory(param -> param.getValue().getValue().lunchProperty());
                dinnerCol.setCellValueFactory(param -> param.getValue().getValue().dinnerProperty());
                xtraCol.setCellValueFactory(param -> param.getValue().getValue().xtra_directProperty());

                remarksCol.setCellFactory(ActionButtonTableCell.for_table_column("View", (Reservations reservations) -> {
                    reservations.getRemarksDisplay().show();
                    return reservations;
                }));
                updateCol.setCellFactory(ActionButtonTableCell.for_table_column("Update", (Reservations reservations) -> {
                    try {
                        Voucher fullVoucherWithItsId = get_full_voucher_with_its_ID(reservations.getId());
                        if (fullVoucherWithItsId == null) {
                            error_message("Failed to connect!", "Could not connect to the datasource.").show();
                        } else if (fullVoucherWithItsId.isEmpty()) {
                            warning_message("Voucher not found!", "It appears the voucher has been deleted. Please refresh your table to see changes made.").show();
                        } else {
                            EditCtrl.voucher = fullVoucherWithItsId;
                            Node node = FXMLLoader.load(getClass().getResource("/_ui_ux/_fxml/_reservations/edit.fxml"));
                            JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
                            jfxDialogLayout.setBody(node);
                            JFXDialog jfxDialog = new JFXDialog(get_parent_for_popup_dialogue(treeTable, "workSpacePane"), jfxDialogLayout, JFXDialog.DialogTransition.BOTTOM);
                            jfxDialog.setOnDialogClosed(event1 -> jfxDialogLayout.getBody().forEach(node1 -> Platform.runLater(() -> {
                                StackPane.clearConstraints(node1);
                                jfxDialogLayout.getChildren().remove(node1);
                                if (searchTF.getText().trim().length() == 0 || searchTF.getText() == null) {
                                    load_runnable_into_a_thread(show_results(get_reservations_based_on_param(null))).start();
                                } else {
                                    find(new ActionEvent());
                                }
                            })));
                            jfxDialog.show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        load_runnable_into_a_thread(write_stack_trace(e)).start();
                        Platform.runLater(() -> programmer_error(e).show());
                    }
                    return reservations;
                }));
                downloadCol.setCellFactory(ActionButtonTableCell.for_table_column("Get", (Reservations reservations) -> {
                    information_message("This is not yet configured");
                    return reservations;
                }));

                ObservableList<Reservations> reservationsObservableList = FXCollections.observableArrayList(reservationsList);
                TreeItem<Reservations> root = new RecursiveTreeItem<>(reservationsObservableList, RecursiveTreeObject::getChildren);
                Platform.runLater(() -> {
                    jfxTreeTableView.setRoot(root);
                    jfxTreeTableView.setShowRoot(false);
                });
                jfxTreeTableView.refresh();
            }
        };
    }

}
