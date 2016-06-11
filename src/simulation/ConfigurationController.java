package simulation;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;


public class ConfigurationController implements Initializable {

    /*@FXML TableView<Config.TimeDistribution> arrivalTable;
    @FXML TableColumn<Config.TimeDistribution, Integer> timeColumn;
    @FXML TableColumn<Config.TimeDistribution, Integer> probColumn;*/

    //Distribution time table
    @FXML TableView<TimeDistribution> arrivalTable;
    @FXML TableView<TimeDistribution> serviceTable;
    @FXML TableColumn<TimeDistribution, Integer> timeColumn;
    @FXML TableColumn<TimeDistribution, Integer> probColumn;

    @FXML TextField serversField;
    @FXML TextField arrivalTimeField = new TextField();
    @FXML TextField arrivalProbField = new TextField();
    @FXML TextField serviceTimeField = new TextField();
    @FXML TextField serviceProbField = new TextField();

    @FXML Button simulationButton;
    @FXML Button addButtonArrival;
    @FXML Button deleteButtonArrival;

    @FXML FileChooser fileChooser = new FileChooser();




    @Override
    public void initialize (URL url, ResourceBundle rb){
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        probColumn.setCellValueFactory(new PropertyValueFactory<>("probabilityTotal"));
//        timeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

    }

    public void addButtonClickedArrival(){
        ArrayList<TimeDistribution> arrivalTableItems = new ArrayList<>();
        arrivalTableItems.addAll(arrivalTable.getItems());
        TimeDistribution timeDistribution = new TimeDistribution(Integer.parseInt(arrivalTimeField.getText()), Double.parseDouble(arrivalProbField.getText()), arrivalTableItems);
        arrivalTable.getItems().add(timeDistribution);
        arrivalTimeField.clear();
        arrivalProbField.clear();
    }
    public void deleteButtonClickedArrival(){
        ObservableList<TimeDistribution> rowSelected, allRows;
        rowSelected = arrivalTable.getSelectionModel().getSelectedItems();
        allRows = arrivalTable.getItems();
        rowSelected.forEach(allRows::remove);
    }

    public void simulationButtonClicked(){
        ArrayList<TimeDistribution> arrivalTableItems = new ArrayList<>();
        arrivalTableItems.addAll(arrivalTable.getItems());
        for (TimeDistribution TD : arrivalTableItems) {
            TD.toString();
        }
    }

//    public ObservableList<Day> getData(){
//        ObservableList<Day> list = FXCollections.observableArrayList();
//        list.add(day);
//        return list;
//    }

}
