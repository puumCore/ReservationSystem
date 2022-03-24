package org.puumCore._odysseySafaris;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.puumCore._odysseySafaris._custom.WatchDog;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends Application {

    //for production
    //public static final File RESOURCE_PATH = new File(System.getenv("APP_HOME").concat("\\_odyssey_safaris\\_reservation_sys"));
    //for dev only
    public static final File RESOURCE_PATH = new File(System.getenv("JAVAFX_DEV_APP_HOME").concat("\\_odyssey_safaris\\_reservation_sys"));
    public static final String PATH_TO_DATA_SOURCE = Main.RESOURCE_PATH.getAbsolutePath().concat("\\_datasource\\odysseySafaris_ds.s3db");
    public static Connection DATA_SOURCE_CONNECTION;
    public static Stage stage;
    public static volatile boolean isAlive;
    private final WatchDog watchDog = new WatchDog();
    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception {
        File file = new File(PATH_TO_DATA_SOURCE);
        if (!file.exists()) {
            watchDog.error_alert("Not Found!", "Could not locate the database, it must have been moved else where.");
            System.exit(0);
        }
        if (file.length() < 1L) {
            watchDog.error_alert("Database is corrupted!", "Could not read the database, it appears to be empty.");
            System.exit(0);
        }
        Main.DATA_SOURCE_CONNECTION = get_connection_to_dataSource();
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

    private Connection get_connection_to_dataSource() {
        try {
            SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
            sqLiteDataSource.setUrl("jdbc:sqlite:".concat(PATH_TO_DATA_SOURCE));
            return sqLiteDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            new Thread(watchDog.write_stack_trace(e)).start();
            Platform.runLater(() -> watchDog.programmer_error(e).show());
        }
        return null;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
