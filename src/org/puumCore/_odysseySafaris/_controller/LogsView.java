package org.puumCore._odysseySafaris._controller;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import org.puumCore._odysseySafaris._custom.Brain;
import org.puumCore._odysseySafaris._interface.ViewingService;
import org.puumCore._odysseySafaris._models._table.LogsInfo;

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

public class LogsView extends Brain implements ViewingService {

    private final JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();

    @FXML
    private JFXTextField searchTF;

    @FXML
    private JFXTreeTableView<LogsInfo> treeTable;

    @FXML
    private TreeTableColumn<LogsInfo, String> timestampCol;

    @FXML
    private TreeTableColumn<LogsInfo, Integer> voucherIdCol;

    @FXML
    private TreeTableColumn<LogsInfo, String> infoCol;

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
        List<LogsInfo> logsBasedOnParam = get_logs_based_on_param(param);
        if (logsBasedOnParam == null) {
            error_message("Failed to connect!", "Could not connect to the datasource.").show();
        } else if (logsBasedOnParam.isEmpty()) {
            warning_message("No results found!", "Please update your parameters").show();
        } else {
            load_runnable_into_a_thread(show_results(logsBasedOnParam)).start();
            String message = (logsBasedOnParam.size() == 1) ? "One Result found!" : String.format("%d Results found!", logsBasedOnParam.size());
            success_notification(message).show();
        }
        event.consume();
    }

    @Override
    public Runnable auto_update_search_suggestions() {
        return () -> {
            Set<String> logSearchSuggestions = get_log_search_suggestions();
            if (logSearchSuggestions != null) {
                ObservableList<String> stringObservableList = FXCollections.observableArrayList();
                stringObservableList.addAll(logSearchSuggestions);
                stringObservableList.add(ALL_WILDCARD);
                update_suggestions(autoCompletePopup, stringObservableList);
            }
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        create_autocompletion_feature_for_textField(autoCompletePopup, searchTF);

        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(auto_update_search_suggestions(), 1, 10, TimeUnit.SECONDS);
        load_runnable_into_a_thread(show_results(get_logs_based_on_param(null))).start();
    }

    private Runnable show_results(List<LogsInfo> logsInfoList) {
        return () -> {
            if (logsInfoList != null && logsInfoList.size() > 0) {
                JFXTreeTableView<LogsInfo> jfxTreeTableView = treeTable;
                timestampCol.setCellValueFactory(param -> param.getValue().getValue().timeStampProperty());
                voucherIdCol.setCellValueFactory(param -> param.getValue().getValue().idProperty());
                infoCol.setCellValueFactory(param -> param.getValue().getValue().activityProperty());

                ObservableList<LogsInfo> reservationsObservableList = FXCollections.observableArrayList(logsInfoList);
                TreeItem<LogsInfo> root = new RecursiveTreeItem<>(reservationsObservableList, RecursiveTreeObject::getChildren);
                Platform.runLater(() -> {
                    jfxTreeTableView.setRoot(root);
                    jfxTreeTableView.setShowRoot(false);
                });
                jfxTreeTableView.refresh();
            }
        };
    }


}
