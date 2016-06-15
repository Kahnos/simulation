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

        // Test config.
        /*ArrayList<TimeDistribution> arrivalDistribution = new ArrayList<>();
        arrivalDistribution.add(new TimeDistribution(3, 0.5, arrivalDistribution));
        arrivalDistribution.add(new TimeDistribution(6, 0.2, arrivalDistribution));
        arrivalDistribution.add(new TimeDistribution(9, 0.15, arrivalDistribution));
        arrivalDistribution.add(new TimeDistribution(10, 0.15, arrivalDistribution));

        ArrayList<TimeDistribution> serviceDistribution = new ArrayList<>();
        serviceDistribution.add(new TimeDistribution(14, 0.25, serviceDistribution));
        serviceDistribution.add(new TimeDistribution(18, 0.25, serviceDistribution));
        serviceDistribution.add(new TimeDistribution(12, 0.25, serviceDistribution));
        serviceDistribution.add(new TimeDistribution(10, 0.25, serviceDistribution));

        Config config = new Config(1, 60, 2, 4, arrivalDistribution, serviceDistribution);
        Config.writeConfigToFile("./test/Saved Configuration - Test.txt", config);*/

        // Test config when reading from file.
        /*Config config = Config.readConfigFromFile("./test/Configuration - Example.txt");
        config.setAllMinMax();

        Simulation simulation = new Simulation(config);
        System.out.println(simulation.toString());*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
