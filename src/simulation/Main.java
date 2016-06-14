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
        /*Parent configFXML = FXMLLoader.load(getClass().getResource("configuration.fxml"));
        window.setTitle("Simulación");
        window.setScene(new Scene(configFXML, 800, 600));
        window.show();*/

        // Test config when reading from file.
        Config config = Config.readConfigFromFile("./test/Configuration - Example.txt");
        config.setAllMinMax();

        Simulation simulation = new Simulation(config);
        System.out.println(simulation.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
