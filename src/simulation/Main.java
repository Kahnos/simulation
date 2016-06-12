package simulation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        // Test objects.
        ArrayList<TimeDistribution> arrivalDistribution = new ArrayList<>();
        arrivalDistribution.add(new TimeDistribution(1, 0.5, arrivalDistribution));
        arrivalDistribution.add(new TimeDistribution(2, 0.2, arrivalDistribution));
        arrivalDistribution.add(new TimeDistribution(3, 0.15, arrivalDistribution));
        arrivalDistribution.add(new TimeDistribution(4, 0.05, arrivalDistribution));
        arrivalDistribution.add(new TimeDistribution(5, 0.1, arrivalDistribution));

        ArrayList<TimeDistribution> serviceDistribution = new ArrayList<>();
        serviceDistribution.add(new TimeDistribution(10, 0.25, serviceDistribution));
        serviceDistribution.add(new TimeDistribution(12, 0.25, serviceDistribution));
        serviceDistribution.add(new TimeDistribution(14, 0.25, serviceDistribution));
        serviceDistribution.add(new TimeDistribution(18, 0.25, serviceDistribution));

        Config config = new Config(1, 60, 2, 6, arrivalDistribution, serviceDistribution);
        Day day = new Day(1, config);
        System.out.println(day.toString());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
