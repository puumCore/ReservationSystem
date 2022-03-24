package org.puumCore._odysseySafaris._controller._reservations;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 24/03/2022
 */

public class ViewCtrl {

    @FXML
    private JFXTextField searchTF1;

    @FXML
    private JFXTreeTableView<?> treeTable;

    @FXML
    private TreeTableColumn<?, ?> idCol;

    @FXML
    private TreeTableColumn<?, ?> clientCol;

    @FXML
    private TreeTableColumn<?, ?> clientNameCol;

    @FXML
    private TreeTableColumn<?, ?> clientPhoneCol;

    @FXML
    private TreeTableColumn<?, ?> clientEmailCol;

    @FXML
    private TreeTableColumn<?, ?> statusCol;

    @FXML
    private TreeTableColumn<?, ?> approverCol;

    @FXML
    private TreeTableColumn<?, ?> approverNameCol;

    @FXML
    private TreeTableColumn<?, ?> approverPhoneCol;

    @FXML
    private TreeTableColumn<?, ?> paymentInfoCol;

    @FXML
    private TreeTableColumn<?, ?> headCountCol;

    @FXML
    private TreeTableColumn<?, ?> adultsCol;

    @FXML
    private TreeTableColumn<?, ?> childrenCol;

    @FXML
    private TreeTableColumn<?, ?> resCol;

    @FXML
    private TreeTableColumn<?, ?> nonResCol;

    @FXML
    private TreeTableColumn<?, ?> roomTypesCol;

    @FXML
    private TreeTableColumn<?, ?> singlesCol;

    @FXML
    private TreeTableColumn<?, ?> doublesCol;

    @FXML
    private TreeTableColumn<?, ?> tripplesCol;

    @FXML
    private TreeTableColumn<?, ?> unitsCol;

    @FXML
    private TreeTableColumn<?, ?> mealPlanCol;

    @FXML
    private TreeTableColumn<?, ?> remarksCol;

    @FXML
    private TreeTableColumn<?, ?> updateCol;

    @FXML
    private TreeTableColumn<?, ?> downloadCol;

    @FXML
    void find(ActionEvent event) {

    }

}
