/*
 * Copyright (c) Puum Core 2022.
 */

package org.puumCore._odysseySafaris._custom;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 14/03/2022
 */

public abstract class Assistant extends WatchDog {

    protected static final long ATTACHMENT_FILE_MAX_SIZE_IN_BYTES = 34 * 1024 * 1024;
    private static final int YEAR_OF_BIRTH = 2020;
    protected final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    protected final DateTimeFormatter combinedDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss:SSS");
    private final UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        String newText = change.getControlNewText();
        String NON_NEGATIVE_INTEGERS_ONLY = "([0-9][0-9]*)?";
        if (newText.matches(NON_NEGATIVE_INTEGERS_ONLY)) {
            return change;
        } else if ("-".equals(change.getText())) {
            if (change.getControlText().startsWith("-")) {
                change.setText("");
                change.setRange(0, 1);
                change.setCaretPosition(change.getCaretPosition() - 2);
                change.setAnchor(change.getAnchor() - 2);
            } else {
                change.setRange(0, 0);
            }
            return change;
        }
        return null;
    };
    private final StringConverter<Integer> converter = new IntegerStringConverter() {
        @Override
        public Integer fromString(String string) {
            if (string.isEmpty()) {
                return 0;
            }
            return super.fromString(string);
        }
    };

    protected final <K> List<K> clear_null_values_from_list(K... k) {
        return Arrays.stream(k).filter(Objects::nonNull).collect(Collectors.toList());
    }

    protected final Thread load_task_into_a_thread(Task<?> task) {
        task.exceptionProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Exception exception = (Exception) newValue;
                exception.printStackTrace();
                load_runnable_into_a_thread(write_stack_trace(exception)).start();
                Platform.runLater(() -> programmer_error(exception).show());
            }
        }));
        return new Thread(task);
    }

    protected final Thread load_runnable_into_a_thread(Runnable runnable) {
        Task<?> task = new Task<Object>() {
            @Override
            protected Object call() {
                runnable.run();
                return null;
            }
        };
        return load_task_into_a_thread(task);
    }


    protected final void clear_textFields(Node... nodes) {
        Arrays.stream(nodes).forEach(node -> {
            if (node instanceof JFXTextField) {
                ((JFXTextField) node).clear();
            } else if (node instanceof JFXPasswordField) {
                ((JFXPasswordField) node).clear();
            }
        });
    }

    protected final void accept_only_numbers(JFXTextField... jfxTextFields) {
        for (JFXTextField jfxTextField : jfxTextFields) {
            TextFormatter<Integer> textFormatter = new TextFormatter<>(converter, 0, integerFilter);
            jfxTextField.setTextFormatter(textFormatter);
        }
    }

    protected final void update_suggestions(JFXAutoCompletePopup<String> jfxAutoCompletePopup, ObservableList<String> observableList) {
        Set<String> stringSet = new HashSet<>(observableList);
        Platform.runLater(() -> {
            try {
                if (!jfxAutoCompletePopup.getSuggestions().isEmpty()) {
                    jfxAutoCompletePopup.getSuggestions().clear();
                }
                jfxAutoCompletePopup.getSuggestions().addAll(stringSet);
            } catch (Exception exception) {
                exception.printStackTrace();
                load_runnable_into_a_thread(write_stack_trace(exception)).start();
            }
        });
    }

    protected final void set_my_preferred_date_format(JFXDatePicker... jfxDatePickers) {
        for (JFXDatePicker jfxDatePicker : jfxDatePickers) {
            StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        return dateTimeFormatter.format(date);
                    } else {
                        return "";
                    }
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, dateTimeFormatter);
                    } else {
                        return null;
                    }
                }
            };
            jfxDatePicker.setConverter(converter);
        }
    }

    protected final void create_autocompletion_feature_for_textField(JFXAutoCompletePopup<String> stringJFXAutoCompletePopup, JFXTextField jfxTextField) {
        stringJFXAutoCompletePopup.setMinSize(400, 400);
        stringJFXAutoCompletePopup.setSelectionHandler(event -> {
            jfxTextField.setText(event.getObject());
            jfxTextField.fireEvent(new ActionEvent());
        });
        jfxTextField.textProperty().addListener(observable -> {
            stringJFXAutoCompletePopup.filter(string -> string.toLowerCase().contains(jfxTextField.getText().toLowerCase()));
            if (stringJFXAutoCompletePopup.getFilteredSuggestions().isEmpty() || jfxTextField.getText().isEmpty()) {
                Platform.runLater(stringJFXAutoCompletePopup::hide);
            } else {
                Platform.runLater(() -> stringJFXAutoCompletePopup.show(jfxTextField));
            }
        });
    }

    protected final void validation_of_phoneNumber(JFXTextField jfxTextField) {
        jfxTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (phoneNumber_is_in_correct_format(newValue)) {
                Platform.runLater(() -> jfxTextField.setStyle("-fx-background-radius: 7px;\n -fx-text-fill: #1D1A1A;"));
            } else {
                Platform.runLater(() -> jfxTextField.setStyle("-fx-background-radius: 7px;\n -fx-text-fill: rgb(241, 58, 58);"));
            }
        }));
    }

    protected final void validation_status_on_email_type(JFXTextField jfxTextField) {
        jfxTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (email_is_in_correct_format(newValue)) {
                Platform.runLater(() -> jfxTextField.setStyle("-fx-background-radius: 7px;\n -fx-text-fill: #1D1A1A;"));
            } else {
                Platform.runLater(() -> jfxTextField.setStyle("-fx-background-radius: 7px;\n -fx-text-fill: rgb(241, 58, 58);"));
            }
        }));
    }

    protected final boolean the_param_is_an_email(String param) {
        return email_is_in_correct_format(param);
    }

    protected final boolean the_date_is_the_correct_format(String param) {
        return Pattern.matches("[0-9]{1,2}-[a-zA-Z]{3,4}-[0-9]{4}", param);
    }

    protected final boolean phoneNumber_is_in_correct_format(String param) {
        return Pattern.matches("^\\d{10}$", param) ||
                Pattern.matches("^(\\d{3}[- .]?){2}\\d{4}$", param) ||
                Pattern.matches("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", param) ||
                Pattern.matches("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", param);
    }

    protected final boolean email_is_in_correct_format(String param) {
        return Pattern.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", param);
    }

    protected final boolean is_a_number(String param) {
        return Pattern.matches("[+]?[0-9]+", param);
    }


    protected final void fading_animation(Node outgoing, Node incoming) {
        new FadeOut(outgoing).play();
        incoming.toFront();
        new FadeIn(incoming).setDelay(Duration.seconds(0.6)).play();
    }

    protected final StackPane get_parent_for_popup_dialogue(Node node, String parentName) {
        Node parentNode = node.getParent();
        if (parentNode instanceof StackPane) {
            if (parentNode.getId() != null) {
                if (parentNode.getId().equals(parentName)) {
                    return (StackPane) parentNode;
                }
            }
        }
        return get_parent_for_popup_dialogue(parentNode, parentName);
    }

    public void show_landing_page(JFXButton selectedBtn, StackPane... stackPanes) {
        final String selectedButtonText = selectedBtn.getText().replaceAll(" ", "").toLowerCase();
        CopyOnWriteArrayList<StackPane> stackPaneCopyOnWriteArrayList = new CopyOnWriteArrayList<>(Arrays.asList(stackPanes));
        StackPane pane = stackPaneCopyOnWriteArrayList.stream().filter(stackPane -> stackPane.getId().toLowerCase().contains(selectedButtonText.contains(" ") ? selectedButtonText.replace(" ", "") : selectedButtonText)).findAny().orElse(null);
        bring_landing_page_to_the_light(stackPaneCopyOnWriteArrayList, pane);
    }

    protected void bring_landing_page_to_the_light(CopyOnWriteArrayList<StackPane> stackPaneCopyOnWriteArrayList, StackPane pane) {
        stackPaneCopyOnWriteArrayList.forEach(stackPane -> {
            boolean letsDoThis = false;
            if (pane == null) {
                letsDoThis = true;
            } else if (!stackPane.equals(pane)) {
                letsDoThis = true;
            }
            if (letsDoThis) {
                if (stackPane.getOpacity() > 0) {
                    new FadeOut(stackPane).play();
                }
            }
        });
        if (pane != null) {
            if (pane.getOpacity() < 1) {
                pane.toFront();
                new FadeIn(pane).setDelay(Duration.seconds(0.6)).play();
            }
        }
    }

    protected void highlight_selected_button(JFXButton selectedBtn) {
        HBox hBox = (HBox) selectedBtn.getParent();
        CopyOnWriteArrayList<Node> nodeCopyOnWriteArrayList = new CopyOnWriteArrayList<>(hBox.getChildren());
        for (Node child : nodeCopyOnWriteArrayList) {
            if (child instanceof JFXButton) {
                JFXButton jfxButton = (JFXButton) child;
                if (!jfxButton.equals(selectedBtn)) {
                    jfxButton.setStyle("-fx-text-fill: #E1E1E1;");
                } else {
                    jfxButton.setStyle("-fx-text-fill: #000000;");
                }
            }
        }
    }

}
