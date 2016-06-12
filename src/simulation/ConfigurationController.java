package simulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ConfigurationController implements Initializable {

    /*@FXML TableView<Config.TimeDistribution> arrivalTable;
    @FXML TableColumn<Config.TimeDistribution, Integer> timeColumn;
    @FXML TableColumn<Config.TimeDistribution, Integer> probColumn;*/

    //Distribution time table
    @FXML TableView<Day> arrivalTable;
    @FXML TableColumn<Day, Integer> timeColumn;
    @FXML TableColumn<Day, Integer> probColumn;
    @FXML TextField serversField;
    @FXML Button simulationButton;

    @FXML FileChooser fileChooser = new FileChooser();

    //Day day = new Day(2,3);


    @Override
    public void initialize (URL url, ResourceBundle rb){
//        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
//        probColumn.setCellValueFactory(new PropertyValueFactory<>("probability"));

        timeColumn.setCellValueFactory(new PropertyValueFactory<>("nextArrivalTime"));
        probColumn.setCellValueFactory(new PropertyValueFactory<>("nextDepartureTime"));
        arrivalTable.setItems(getData());
        simulationButton.setOnAction(e -> System.out.println(serversField.getText()));


    }

    public ObservableList<Day> getData(){
        ObservableList<Day> list = FXCollections.observableArrayList();
        //list.add(day);
        return list;
    }

}
