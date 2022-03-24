package org.puumCore._odysseySafaris._outsourced;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

import java.util.function.Function;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 14/03/2022
 */

public class ActionButtonTableCell<S> extends TreeTableCell<S, JFXButton> {

    private final JFXButton jfxButton;

    public ActionButtonTableCell(String label, Function<S, S> function) {
        this.getStyleClass().add("action-button-table-cell");

        this.jfxButton = new JFXButton(label);
        this.jfxButton.setOnAction(event -> function.apply(get_current_item()));
        this.jfxButton.setStyle(String.format("%s" +
                " -fx-text-fill: F4F9FB;" +
                " -fx-font-family: 'Montserrat SemiBold';" +
                " -fx-font-size: 14px;" +
                " -fx-background-color: #E31717;" +
                " -fx-cursor: hand;", this.jfxButton.getStyle()));
    }

    public S get_current_item() {
        return getTreeTableView().getTreeItem(getIndex()).getValue();
    }

    public static <S> Callback<TreeTableColumn<S, JFXButton>, TreeTableCell<S, JFXButton>> for_table_column(String label, Function<S, S> function) {
        return param -> new ActionButtonTableCell<>(label, function);
    }

    @Override
    protected void updateItem(JFXButton item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(jfxButton);
        }
    }
}
