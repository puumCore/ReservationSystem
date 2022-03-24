/*
 * Copyright (c) Puum Core 2022.
 */

package org.puumCore._odysseySafaris._interface;

import com.jfoenix.controls.JFXButton;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;

/**
 * @author Puum Core<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 12/11/2021
 */

public interface UserExperience extends Initializable {

    void highlight_selected_button(JFXButton selectedBtn);

    Task<Object> build_skeleton();

}
