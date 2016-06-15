package simulation;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ConfigurationController implements Initializable {

    /*@FXML TableView<Config.TimeDistribution> arrivalTable;
    @FXML TableColumn<Config.TimeDistribution, Integer> timeColumnArrival;
    @FXML TableColumn<Config.TimeDistribution, Integer> probColumnArrival;*/

    @FXML ImageView logo;



    @FXML private Spinner<Integer> serversSpinner = new Spinner<>();
    SpinnerValueFactory svfServers = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
    @FXML private Spinner<Integer> clientsSpinner = new Spinner<>();
    SpinnerValueFactory svfClients = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 1000);

    SpinnerValueFactory svfInt = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);

    @FXML private Spinner<Integer> openTimeSpinner = new Spinner<>();
    @FXML private Spinner<Integer> simulationDaysSpinner = new Spinner<>();

    //Distribution time table
    @FXML private TableView<TimeDistribution> arrivalTable;
    @FXML private TableColumn<TimeDistribution, Integer> timeColumnArrival;
    @FXML private TableColumn<TimeDistribution, Integer> probColumnArrival;
    @FXML private TextField arrivalTimeField = new TextField();
    @FXML private TextField arrivalProbField = new TextField();

    //Service time table
    @FXML private TableView<TimeDistribution> serviceTable;
    @FXML private TableColumn<TimeDistribution, Integer> timeColumnService;
    @FXML private TableColumn<TimeDistribution, Integer> probColumnService;
    @FXML private TextField serviceTimeField = new TextField();
    @FXML private TextField serviceProbField = new TextField();

    @FXML private Button simulationButton;
    @FXML private Button addButtonArrival;
    @FXML private Button deleteButtonArrival;
    @FXML private Button addButtonService;
    @FXML private Button deleteButtonService;
    @FXML private Button fileButton;

    @FXML private Config config = new Config();

    @FXML private AreaChart chart;



    @FXML private FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize (URL url, ResourceBundle rb){
        File file = new File("./assets/logoUCAB.png");
        Image image = new Image(file.toURI().toString());
        logo.setImage(image);

        timeColumnArrival.setCellValueFactory(new PropertyValueFactory<>("time"));
        probColumnArrival.setCellValueFactory(new PropertyValueFactory<>("probabilityTotal"));
        timeColumnService.setCellValueFactory(new PropertyValueFactory<>("time"));
        probColumnService.setCellValueFactory(new PropertyValueFactory<>("probabilityTotal"));
        //        timeColumnArrival.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        serversSpinner.setEditable(true);
        serversSpinner.setValueFactory(svfServers);

        clientsSpinner.setEditable(true);
        clientsSpinner.setValueFactory(svfClients);

        openTimeSpinner.setEditable(true);
        openTimeSpinner.setValueFactory(svfInt);

        simulationDaysSpinner.setEditable(true);
        simulationDaysSpinner.setValueFactory(svfInt);
    }


    public void fileButtonClicked() {
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null){
            System.out.println(selectedFile.getAbsolutePath());
        } else {
            System.out.println("Archivo inválido");
        }
    }

    public void addButtonClickedArrival(){
        if ((!arrivalTimeField.getText().isEmpty()) && (!arrivalProbField.getText().isEmpty())) {
            ArrayList<TimeDistribution> arrivalTableItems = new ArrayList<>();
            arrivalTableItems.addAll(arrivalTable.getItems());
            TimeDistribution timeDistribution = new TimeDistribution(Integer.parseInt(arrivalTimeField.getText()),
                    Double.parseDouble(arrivalProbField.getText()), arrivalTableItems);
            arrivalTable.getItems().add(timeDistribution);
            arrivalTimeField.clear();
            arrivalProbField.clear();
        }
    }

    public void addButtonClickedService(){
        if ((!serviceTimeField.getText().isEmpty()) && (!serviceProbField.getText().isEmpty())){
            ArrayList<TimeDistribution> serviceTableItems = new ArrayList<>();
            serviceTableItems.addAll(serviceTable.getItems());
            TimeDistribution timeDistribution = new TimeDistribution(Integer.parseInt(serviceTimeField.getText()),
                    Double.parseDouble(serviceProbField.getText()), serviceTableItems);
            serviceTable.getItems().add(timeDistribution);
            serviceTimeField.clear();
            serviceProbField.clear();
        }
    }

    public void deleteButtonClickedArrival(){
        ObservableList<TimeDistribution> rowSelected, allRows;
        rowSelected = arrivalTable.getSelectionModel().getSelectedItems();
        allRows = arrivalTable.getItems();
        rowSelected.forEach(allRows::remove);
    }

    public void deleteButtonClickedService(){
        ObservableList<TimeDistribution> rowSelected, allRows;
        rowSelected = serviceTable.getSelectionModel().getSelectedItems();
        allRows = serviceTable.getItems();
        rowSelected.forEach(allRows::remove);
    }

    @FXML
    public void simulationButtonClicked() throws IOException {

        /*ArrayList<TimeDistribution> arrivalTableItems = new ArrayList<>();
        arrivalTableItems.addAll(arrivalTable.getItems());
        for (TimeDistribution TD : arrivalTableItems) {
            TD.toString();*/

        //Se obtiene el valor de la cantidad de servidores
        config.setServerAmount(serversSpinner.getValue());

        //Se obtiene el valor de la cantidad de clientes permitidos en el sistema
        config.setMaxClients(clientsSpinner.getValue());

        //Se obtiene el valor del tiempo de trabajo en el sistema
        config.setOpenTime(openTimeSpinner.getValue());

        //Se obtiene el valor de la cantidad de días de simulación
        config.setSimulationDays(simulationDaysSpinner.getValue());

        //Se obtienen todos los elementos de la tabla de distribución de tiempo de llegadas
        ArrayList<TimeDistribution> arrivalTableItems = new ArrayList<>();
        arrivalTableItems.addAll(arrivalTable.getItems());

        ArrayList<TimeDistribution> serviceTableItems = new ArrayList<>();
        serviceTableItems.addAll(serviceTable.getItems());

        if ((TimeDistribution.verifyProbabilities(arrivalTableItems)==1) && (TimeDistribution.verifyProbabilities(serviceTableItems)==1)){
            SimulationController sc = new SimulationController();
            sc.display(config);
            Stage primaryStage = (Stage) simulationButton.getScene().getWindow();
            primaryStage.close();
        }
        else {
            if (TimeDistribution.verifyProbabilities(arrivalTableItems) != 1) {
                AlertBox.display("Advertencia", "La sumatoria de las probabilidades de la distribución de tiempo de llegadas debe ser igual a 1");
            }
            if (TimeDistribution.verifyProbabilities(serviceTableItems) != 1) {
                AlertBox.display("Advertencia", "La sumatoria de las probabilidades de la distribución de tiempo de servicio debe ser igual a 1");
            }
        }

    }

}
