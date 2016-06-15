package simulation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Time;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage window) throws Exception{
        Parent configFXML = FXMLLoader.load(getClass().getResource("configuration.fxml"));
        window.setTitle("Configuración de la simulación");
        window.setScene(new Scene(configFXML, 530, 600));
        window.show();
        window.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
