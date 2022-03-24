package org.puumCore._odysseySafaris._controller;

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

public class LogsView {

    @FXML
    private JFXTextField searchTF;

    @FXML
    private JFXTreeTableView<?> treeTable;

    @FXML
    private TreeTableColumn<?, ?> timestampCol;

    @FXML
    private TreeTableColumn<?, ?> voucherIdCol;

    @FXML
    private TreeTableColumn<?, ?> infoCol;

    @FXML
    void find(ActionEvent event) {

    }

}
