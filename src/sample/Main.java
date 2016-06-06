package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage window) throws Exception{
        Parent configFXML = FXMLLoader.load(getClass().getResource("configuration.fxml"));
        window.setTitle("Simulación");
        window.setScene(new Scene(configFXML, 800, 600));
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
