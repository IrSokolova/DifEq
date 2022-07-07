package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        configureStage(primaryStage, scene);
        primaryStage.show();
    }

    private void configureStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.setTitle("Computational practicum");
        stage.setMinHeight(500);
        stage.setMinWidth(600);
    }


    public static void main(String[] args) {
        launch(args);
    }
}