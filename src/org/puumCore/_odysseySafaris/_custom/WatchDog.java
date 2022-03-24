/*
 * Copyright (c) Puum Core 2022.
 */

package org.puumCore._odysseySafaris._custom;

import animatefx.animation.Shake;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.puumCore._odysseySafaris.Main;
import org.puumCore._odysseySafaris._object._logs.Log;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * @author Puum Core (Mandela Muriithi)<br>
 * <a href = https://github.com/puumCore>GitHub: Mandela Muriithi</a>
 * @version 1.0
 * @since 14/03/2022
 */
public class WatchDog {

    private final String PATH_TO_INFO_FOLDER = Main.RESOURCE_PATH.getAbsolutePath().concat("\\_watchDog\\_log\\");
    private final String PATH_TO_STACK_TRACE_ERROR_FOLDER = Main.RESOURCE_PATH.getAbsolutePath().concat("\\_watchDog\\_error\\");


    protected final boolean NOT_connected_to_the_internet() {
        try {
            URL url = new URL("http://www.google.com");
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            new Thread(write_stack_trace(e)).start();
        }
        return true;
    }

    public final void system_exit() {
        Main.isAlive = false;
        if (Main.DATA_SOURCE_CONNECTION != null) {
            try {
                if (!Main.DATA_SOURCE_CONNECTION.isClosed()) {
                    Main.DATA_SOURCE_CONNECTION.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Thread(write_stack_trace(e)).start();
            }
        }
        System.exit(0);
    }

    protected final boolean i_want_to(String actionName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(Main.stage);
        alert.setTitle("The app requires your confirmation to continue...");
        alert.setHeaderText("Are you sure you want to ".concat(actionName).concat(" ?"));
        alert.setContentText("This can not be undone!");
        ButtonType yesButtonType = new ButtonType("YES");
        ButtonType noButtonType = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().setAll(yesButtonType, noButtonType);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get().equals(yesButtonType);
    }

    protected final String get_text_from_text_dialog(String title, String header, String contextOfInput, String defaultValue) {
        TextInputDialog textInputDialog = new TextInputDialog(defaultValue);
        textInputDialog.initOwner(Main.stage);
        textInputDialog.setTitle(title);
        textInputDialog.setHeaderText(header);
        textInputDialog.setContentText(contextOfInput.concat(": "));
        Optional<String> userChoice = textInputDialog.showAndWait();
        return userChoice.orElse(null);
    }

    public final void error_alert(String heading, String info) {
        get_alert(Alert.AlertType.ERROR, "Application Error!", heading, info).showAndWait();
    }

    public final Alert get_alert(Alert.AlertType alertType, String title, String heading, String info) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(heading);
        alert.setContentText(info);

        return alert;
    }

    protected final void information_message(String message) {
        warning_message("Information", "\n".concat(message))
                .hideAfter(Duration.seconds(12))
                .graphic(null)
                .position(Pos.BOTTOM_RIGHT)
                .show();
    }

    protected final Notifications success_notification(String about) {
        return Notifications.create()
                .title("Success")
                .text(about)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(5))
                .graphic(new ImageView(new Image("/_ui_ux/_image/_icon/icons8_Ok_48px.png")));
    }

    protected final Notifications error_message(String title, String text) {
        Image image = new Image("/_ui_ux/_image/_icon/icons8_Close_Window_48px.png");
        return Notifications.create()
                .title(title)
                .text(text)
                .graphic(new ImageView(image))
                .hideAfter(Duration.seconds(8))
                .position(Pos.TOP_LEFT);
    }

    protected final Notifications warning_message(String title, String text) {
        Image image = new Image("/_ui_ux/_image/_icon/icons8_Error_48px.png");
        return Notifications.create()
                .title(title)
                .text(text)
                .graphic(new ImageView(image))
                .hideAfter(Duration.seconds(8))
                .position(Pos.TOP_RIGHT);
    }

    protected final Notifications no_input_warning_message(Node node) {
        Image image = new Image("/_ui_ux/_image/_icon/icons8_Error_48px.png");
        return Notifications.create()
                .title("Something is Missing")
                .text("Click Here to trace this Error.")
                .graphic(new ImageView(image))
                .hideAfter(Duration.seconds(8))
                .position(Pos.TOP_RIGHT)
                .onAction(event -> {
                    new Shake(node).play();
                    node.requestFocus();
                });
    }

    public final Alert programmer_error(Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(Main.stage);
        alert.setTitle("WATCH DOG");
        alert.setHeaderText("ERROR TYPE : " + exception.getClass());
        alert.setContentText("This dialog is a detailed explanation of the error that has occurred");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        String exceptionText = stringWriter.toString();
        Label label = new Label("The exception stacktrace was: ");
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        VBox vBox = new VBox();
        vBox.getChildren().add(label);
        vBox.getChildren().add(textArea);
        alert.getDialogPane().setExpandableContent(vBox);
        return alert;
    }


    protected final Task<Object> write_log(Log log) {
        return new Task<Object>() {
            @Override
            protected Object call() {
                log.setTimeStamp(time_stamp());
                write_object_of_an_array_into_a_json_file(new Gson().toJsonTree(log, Log.class), PATH_TO_INFO_FOLDER.concat(gate_date_for_file_name()).concat(".json"));
                return null;
            }
        };
    }

    private void write_object_of_an_array_into_a_json_file(JsonElement jsonElement, String pathToJsonFile) {
        try {
            final File file = new File(pathToJsonFile);
            if (file.exists()) {
                final JsonArray jsonArray = get_array_from_json_file(file.getAbsolutePath());
                jsonArray.add(jsonElement);
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(jsonArray));
                fileWriter.close();
            } else {
                FileWriter fileWriter = new FileWriter(file);
                final JsonArray jsonArray = new JsonArray();
                jsonArray.add(jsonElement);
                fileWriter.write(new Gson().toJson(jsonArray));
                fileWriter.close();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            programmer_error(ex).show();
        }
    }

    protected final JsonArray get_array_from_json_file(String pathToJsonFile) {
        final JsonArray jsonArray = new JsonArray();
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToJsonFile));
            jsonArray.addAll(new Gson().fromJson(bufferedReader, JsonArray.class));
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            new Thread(write_stack_trace(e)).start();
        }
        return jsonArray;
    }

    protected final <T> boolean list_is_written_to_json_file(List<T> list, String pathToJsonFile) {
        try {
            final File file = new File(pathToJsonFile);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(list));
            fileWriter.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            Platform.runLater(() -> programmer_error(ex).show());
        }
        return false;
    }

    public final Runnable write_stack_trace(Exception exception) {
        return () -> {
            BufferedWriter bw = null;
            try {
                File log = new File(PATH_TO_STACK_TRACE_ERROR_FOLDER.concat(gate_date_for_file_name().concat(" stackTrace_log.txt")));
                if (!log.exists()) {
                    Files.write(log.toPath(), String.format("This is a newly created file [ %s ]\n\n", time_stamp()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
                }
                if (log.canWrite() & log.canRead()) {
                    FileWriter fw = new FileWriter(log, true);
                    bw = new BufferedWriter(fw);
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(stringWriter);
                    exception.printStackTrace(printWriter);
                    String exceptionText = stringWriter.toString();
                    bw.write("\n ##################################################################################################"
                            + " \n " + time_stamp()
                            + "\n " + exceptionText
                            + "\n\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                programmer_error(ex).show();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    programmer_error(ex).show();
                }
            }
        };
    }


    private String gate_date_for_file_name() {
        return get_date().replaceAll("-", " ");
    }

    public final String time_stamp() {
        return String.format("%s %s", get_date(), get_time());
    }

    public final String get_time() {
        return new SimpleDateFormat("HH:mm:ss:SSS").format(Calendar.getInstance().getTime());
    }

    public final String get_date() {
        return new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime());
    }


}
