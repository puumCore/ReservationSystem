package org.puumCore._odysseySafaris._controller._units;

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
    private JFXTreeTableView<?> unitsTable;

    @FXML
    private TreeTableColumn<?, ?> indexCol;

    @FXML
    private TreeTableColumn<?, ?> nameCol;

    @FXML
    private TreeTableColumn<?, ?> inDateCol;

    @FXML
    private TreeTableColumn<?, ?> outDateCol;

    @FXML
    private TreeTableColumn<?, ?> nightsCountCol;

    @FXML
    private TreeTableColumn<?, ?> editActionCol;

    @FXML
    void save(ActionEvent event) {

    }

}
