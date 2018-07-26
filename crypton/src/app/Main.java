package app;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.LoginController;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Scene login = new Scene(root);
        stage.setScene(login);
        stage.setResizable(false);
        stage.setTitle("Crypton");
        stage.setHeight(600);
        stage.setWidth(800);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
