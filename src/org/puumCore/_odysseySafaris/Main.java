package org.puumCore._odysseySafaris;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.puumCore._odysseySafaris._custom.WatchDog;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends Application {

    //for production
    //public static final File RESOURCE_PATH = new File(System.getenv("APP_HOME").concat("\\_odyssey_safaris\\_reservation_sys"));
    //for dev only
    public static final File RESOURCE_PATH = new File(System.getenv("JAVAFX_DEV_APP_HOME").concat("\\_odyssey_safaris\\_reservation_sys"));
    public static Connection DATA_SOURCE_CONNECTION;
    public static Stage stage;
    public static volatile boolean isAlive;
    private final String PATH_TO_SETTINGS_FILE = Main.RESOURCE_PATH.getAbsolutePath().concat("\\_config\\settings.properties");
    private final WatchDog watchDog = new WatchDog();
    private double xOffset;
    private double yOffset;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        for (int attempts = 1; attempts <= 3; ++attempts) {
            String host = watchDog.get_property_value("host", PATH_TO_SETTINGS_FILE);
            String port = watchDog.get_property_value("port", PATH_TO_SETTINGS_FILE);
            String ds_name = watchDog.get_property_value("ds_name", PATH_TO_SETTINGS_FILE);
            String ds_user = watchDog.get_property_value("ds_user", PATH_TO_SETTINGS_FILE);
            String ds_password = watchDog.get_property_value("ds_password", PATH_TO_SETTINGS_FILE);
            if (host == null || port == null || ds_name == null || ds_user == null || ds_password == null) {
                watchDog.error_alert(String.format("%d Attempts left", (3 - attempts)), "Update the settings file appropriately then retry.");
            } else {
                Main.DATA_SOURCE_CONNECTION = get_connection_to_dataSource(host, port, ds_name, ds_user, ds_password);
                break;
            }
        }

        if (Main.DATA_SOURCE_CONNECTION == null) {
            watchDog.error_alert("Connection could not be established!", "Could not connect to the database.");
            watchDog.system_exit();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/_ui_ux/_fxml/sample.fxml"));
        Scene scene = new Scene(root);
        scene.setOnMousePressed(event2 -> {
            this.xOffset = event2.getSceneX();
            this.yOffset = event2.getSceneY();
        });
        scene.setOnMouseDragged(event1 -> {
            primaryStage.setX(event1.getScreenX() - this.xOffset);
            primaryStage.setY(event1.getScreenY() - this.yOffset);
        });
        scene.getStylesheets().addAll(Objects.requireNonNull(getClass().getResource("/_ui_ux/_css/autoCompletePopupStyle.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Reservation System");
        primaryStage.initStyle(StageStyle.DECORATED);

        primaryStage.show();

        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> watchDog.system_exit());

        Main.stage = primaryStage;
    }

    /**
     * AS long as mariadb-java-client-2.6.0.jar
     * is a module
     * (does not matter if is compile or runtime)
     * there is no need for Class.forName("org.mariadb.jdbc.Driver");
     */
    private Connection get_connection_to_dataSource(String host, String port, String datasource, String user, String password) {
        try {
            return DriverManager.getConnection(String.format("jdbc:mariadb://%s:%s/%s", host, port, datasource), user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            new Thread(watchDog.write_stack_trace(e)).start();
            Platform.runLater(() -> watchDog.programmer_error(e).show());
        }
        return null;
    }
}
