package org.puumCore._odysseySafaris._controller;

import com.jfoenix.controls.JFXButton;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.puumCore._odysseySafaris._custom.Assistant;
import org.puumCore._odysseySafaris._interface.UserExperience;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 24/03/2022
 */

public class Controller extends Assistant implements UserExperience {

    @FXML
    private StackPane aboutPane;

    @FXML
    private StackPane logsPane;

    @FXML
    private StackPane addPane;

    @FXML
    private StackPane viewPane;

    @FXML
    void show_about_page(ActionEvent event) {
        if (event.getSource() instanceof JFXButton) {
            JFXButton jfxButton = (JFXButton) event.getSource();
            focus_on_desired_child_page(jfxButton, viewPane, addPane, logsPane, aboutPane);
        }
        event.consume();
    }

    @FXML
    void show_add_page(ActionEvent event) {
        if (event.getSource() instanceof JFXButton) {
            JFXButton jfxButton = (JFXButton) event.getSource();
            focus_on_desired_child_page(jfxButton, viewPane, addPane, logsPane, aboutPane);
        }
        event.consume();
    }

    @FXML
    void show_log_page(ActionEvent event) {
        if (event.getSource() instanceof JFXButton) {
            JFXButton jfxButton = (JFXButton) event.getSource();
            focus_on_desired_child_page(jfxButton, viewPane, addPane, logsPane, aboutPane);
        }
        event.consume();
    }

    @FXML
    void show_view_page(ActionEvent event) {
        if (event.getSource() instanceof JFXButton) {
            JFXButton jfxButton = (JFXButton) event.getSource();
            focus_on_desired_child_page(jfxButton, viewPane, addPane, logsPane, aboutPane);
        }
        event.consume();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        load_task_into_a_thread(build_skeleton()).start();
    }

    @Override
    public void highlight_selected_button(JFXButton selectedBtn) {
        super.highlight_selected_button(selectedBtn);
    }

    @Override
    public Task<Object> build_skeleton() {
        return new Task<Object>() {
            @Override
            protected Object call() {
                load_runnable_into_a_thread(home_skeleton("/_ui_ux/_fxml/_reservations/view.fxml", viewPane)).start();
                load_runnable_into_a_thread(home_skeleton("/_ui_ux/_fxml/_reservations/add.fxml", addPane)).start();
                load_runnable_into_a_thread(home_skeleton("/_ui_ux/_fxml/logsView.fxml", logsPane)).start();
                return null;
            }
        };
    }

    private void focus_on_desired_child_page(JFXButton selectedBtn, StackPane... stackPanes) {
        this.highlight_selected_button(selectedBtn);
        show_landing_page(selectedBtn, stackPanes);
    }

}
