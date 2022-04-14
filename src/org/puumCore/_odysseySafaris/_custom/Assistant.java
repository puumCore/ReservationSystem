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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.puumCore._odysseySafaris.Main;
import org.puumCore._odysseySafaris._models._object.Voucher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 14/03/2022
 */

public abstract class Assistant extends WatchDog {

    private static final int YEAR_OF_BIRTH = 2022;
    protected final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    protected final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
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
    private final File EXCEL_TEMPLATE_FILE = new File(Main.RESOURCE_PATH.getAbsolutePath().concat("\\_excel\\_template\\voucher_template.xlsx"));
    private final String EXCEL_DUMP_PATH = Main.RESOURCE_PATH.getAbsolutePath().concat("\\_excel\\_dump\\");

    protected final File get_the_voucher_that_has_been_built(Voucher voucher) {
        System.out.println("voucher = " + voucher);
        File outputFile = null;
        try {
            /*
             * NOTE!
             * Rows : indexing starts from 0
             * Columns : indexing starts from 1
             * */
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(EXCEL_TEMPLATE_FILE));
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            Row voucherRow = xssfSheet.getRow(9);
            voucherRow.getCell(2).setCellValue(voucher.getId());

            Row hotelRow = xssfSheet.getRow(12);
            hotelRow.getCell(0).setCellValue(voucher.getHotel().getName());
            hotelRow.getCell(4).setCellValue(voucher.getHotel().getBranch());

            Row clientRow = xssfSheet.getRow(15);
            clientRow.getCell(2).setCellValue(voucher.getClient().getName());
            clientRow.getCell(5).setCellValue(voucher.getClient().getPhone());

            Row headCount_aRow = xssfSheet.getRow(16);
            headCount_aRow.getCell(2).setCellValue(voucher.getHeadCount().getAdults());
            headCount_aRow.getCell(5).setCellValue(voucher.getHeadCount().getChildren());
            headCount_aRow.getCell(8).setCellValue(voucher.getHeadCount().getInfants());

            Row headCount_bRow = xssfSheet.getRow(17);
            headCount_bRow.getCell(2).setCellValue(voucher.getHeadCount().getRes());
            headCount_bRow.getCell(5).setCellValue(voucher.getHeadCount().getNonRes());

            Row reservationsRow = xssfSheet.getRow(20);
            reservationsRow.getCell(2).setCellValue(voucher.getRoomType().getSingles() ? "YES" : "NO");
            reservationsRow.getCell(5).setCellValue(voucher.getRoomType().getDoubles() ? "YES" : "NO");
            reservationsRow.getCell(8).setCellValue(voucher.getRoomType().getTriples() ? "YES" : "NO");

            Row units_aRow = xssfSheet.getRow(23);
            units_aRow.getCell(2).setCellValue(voucher.getTimeLine().getArrival());
            units_aRow.getCell(5).setCellValue(voucher.getTimeLine().getDeparture());

            Row units_bRow = xssfSheet.getRow(24);
            units_bRow.getCell(2).setCellValue(voucher.getTimeLine().getDays());
            units_bRow.getCell(5).setCellValue(voucher.getTimeLine().getNights());

            Row mealPlans_aRow = xssfSheet.getRow(27);
            mealPlans_aRow.getCell(3).setCellValue(voucher.getMealPlan().getB_b() ? "YES" : "NO");
            mealPlans_aRow.getCell(6).setCellValue(voucher.getMealPlan().getH_b() ? "YES" : "NO");
            mealPlans_aRow.getCell(9).setCellValue(voucher.getMealPlan().getF_b() ? "YES" : "NO");

            Row mealPlans_bRow = xssfSheet.getRow(28);
            mealPlans_bRow.getCell(3).setCellValue(voucher.getMealPlan().getLunch() ? "YES" : "NO");
            mealPlans_bRow.getCell(6).setCellValue(voucher.getMealPlan().getDinner() ? "YES" : "NO");
            mealPlans_bRow.getCell(9).setCellValue(voucher.getMealPlan().getXtra_direct() ? "YES" : "NO");


            if (voucher.getRemarks() != null) {
                Row remarksRow = xssfSheet.getRow(31);
                remarksRow.getCell(0).setCellValue(voucher.getRemarks());
            }

            if (voucher.getConfirmPerson() != null) {
                Row remarksRow = xssfSheet.getRow(34);
                remarksRow.getCell(1).setCellValue(String.format("%s (%s)", voucher.getConfirmPerson().getName(), voucher.getConfirmPerson().getPhone()));
            }

            Row paymentRow = xssfSheet.getRow(37);
            paymentRow.getCell(2).setCellValue(voucher.getPaidBy());

            Row timeStampRow = xssfSheet.getRow(41);
            timeStampRow.getCell(6).setCellValue(get_time_stamp());

            outputFile = new File(EXCEL_DUMP_PATH.concat(String.format("voucher_%d.xlsx", voucher.getId())));
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            prevent_excel_from_been_modified(xssfWorkbook, xssfSheet);
            xssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            new Thread(write_stack_trace(e)).start();
            Platform.runLater(() -> programmer_error(e).show());
        }
        return outputFile;
    }

    private void prevent_excel_from_been_modified(XSSFWorkbook xssfWorkbook, XSSFSheet xssfSheet) {
        String password = RandomStringUtils.randomAlphanumeric(8);
        byte[] pwdBytes = password.getBytes(StandardCharsets.UTF_8);
        xssfSheet.lockDeleteColumns();
        xssfSheet.lockDeleteRows();
        xssfSheet.lockFormatCells();
        xssfSheet.lockFormatColumns();
        xssfSheet.lockFormatRows();
        xssfSheet.lockInsertColumns();
        xssfSheet.lockInsertRows();
        xssfSheet.getCTWorksheet().getSheetProtection().setPassword(pwdBytes);
        xssfSheet.enableLocking();
        xssfWorkbook.lockStructure();
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
            } else if (node instanceof JFXDatePicker) {
                ((JFXDatePicker) node).getEditor().clear();
            } else if (node instanceof JFXTimePicker) {
                ((JFXTimePicker) node).getEditor().clear();
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
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, dateFormatter);
                    } else {
                        return null;
                    }
                }
            };
            jfxDatePicker.setConverter(converter);
        }
    }

    protected final void set_my_preferred_time_format(JFXTimePicker... jfxTimePickers) {
        for (JFXTimePicker jfxTimePicker : jfxTimePickers) {
            StringConverter<LocalTime> converter = new StringConverter<LocalTime>() {
                @Override
                public String toString(LocalTime time) {
                    if (time != null) {
                        return timeFormatter.format(time);
                    } else {
                        return "";
                    }
                }

                @Override
                public LocalTime fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalTime.parse(string, timeFormatter);
                    } else {
                        return null;
                    }
                }
            };
            jfxTimePicker.setConverter(converter);
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

    protected final void validation_of_time(JFXTimePicker jfxTimePicker) {
        TextField textField = jfxTimePicker.getEditor();
        textField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (the_time_is_the_correct_format(newValue)) {
                Platform.runLater(() -> textField.setStyle("-fx-background-radius: 7px;\n -fx-text-fill: #1D1A1A;"));
            } else {
                Platform.runLater(() -> textField.setStyle("-fx-background-radius: 7px;\n -fx-text-fill: rgb(241, 58, 58);"));
            }
        }));
    }

    protected final boolean the_time_is_the_correct_format(String param) {
        return Pattern.matches("^([0]?[0-9]|2[0-3]):[0-5][0-9]$", param);
    }

    protected final boolean phoneNumber_is_in_correct_format(String param) {
        return Pattern.matches("^\\d{10}$", param) ||
                Pattern.matches("^(\\d{3}[- .]?){2}\\d{4}$", param) ||
                Pattern.matches("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", param) ||
                Pattern.matches("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", param);
    }

    protected final Runnable home_skeleton(final String fxmlResourcePath, final StackPane stackPane) {
        return () -> Platform.runLater(() -> {
            try {
                Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlResourcePath)));
                stackPane.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
                load_runnable_into_a_thread(write_stack_trace(e)).start();
                Platform.runLater(() -> programmer_error(e).showAndWait());
            }
        });
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

    public enum PersonType {
        CLIENT(), CONFIRM()
    }

    public enum VoucherStatus {
        RESERVE("Please Reserve"), AMEND("Amend"), CANCEL("Cancel");

        String status;

        VoucherStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

}
