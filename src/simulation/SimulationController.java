package simulation;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Chart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
/**
 * Created by Libny on 12/06/2016.
 */
public class SimulationController extends HBox {

    @FXML private Pane mainPanel;
    @FXML private VBox vb;
    @FXML private Label title;
    @FXML private Label label2;
    @FXML private ListView<String> statisticsListView;

    //Simulation table
    @FXML private TableView<Event> simulationTable;
    @FXML private TableColumn<Event, Integer> idColumn = new TableColumn<>();
    @FXML private TableColumn<Event, String> typeColumn = new TableColumn<>();
    @FXML private TableColumn<Event, Integer> clientColumn = new TableColumn<>();
    @FXML private TableColumn<Event, Integer> tmColumn = new TableColumn<>();
    //servidores
    @FXML private TableColumn<Event, Integer> waitlineColumn = new TableColumn<>();
    @FXML private TableColumn<Event, Integer> totalClientsColumn = new TableColumn<>();
    @FXML private TableColumn<Event, Integer> atColumn = new TableColumn<>();
    @FXML private TableColumn<Event, Integer> dtColumn = new TableColumn<>();
    @FXML private TableColumn<Event, Integer> [] tableColumns;
    private Config config;

    public SimulationController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("simulation.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void display(Config config){
        Stage window = new Stage();

        this.config = config;
        window.setResizable(false);

        // Test day
        Day testDay = getTestDay();

        ObservableList<Event> eventList = FXCollections.observableArrayList();
        eventList.addAll(testDay.getEvents());

        //Creación de las columnas de la tabla de simulación
        //Eventos
        idColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        idColumn.setText("Evento");
        //Tipo de evento
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setText("Tipo");
        //Número de cliente
        clientColumn.setCellValueFactory(event -> new SimpleObjectProperty<Integer>(event.getValue().getClient().getId()));
        clientColumn.setText("Cliente");
        //TM
        tmColumn.setCellValueFactory(new PropertyValueFactory<>("TM"));
        tmColumn.setText("TM");
        simulationTable.getColumns().addAll(idColumn,typeColumn,clientColumn,tmColumn);

        //Cola de espera
        waitlineColumn.setCellValueFactory(event -> new SimpleObjectProperty<Integer>(event.getValue().getWaitLine().size()));
        waitlineColumn.setText("Cola");
        //Total de clientes en el sistema
        totalClientsColumn.setCellValueFactory(new PropertyValueFactory<>("totalClients"));
        totalClientsColumn.setText("Total");
        //AT
        atColumn.setCellValueFactory(new PropertyValueFactory<>("nextArrivalTime"));
        atColumn.setText("AT");
        //DT
        dtColumn.setCellValueFactory(new PropertyValueFactory<>("nextDepartureTime"));
        dtColumn.setText("DT");

        tableColumns = new TableColumn[config.getServerAmount()];

        for(int i=0; i < config.getServerAmount(); i++){
            int finalI = i;
            tableColumns[i] = new TableColumn<>();
            tableColumns[i].setCellValueFactory(event -> {
                int busy;

                if (event.getValue().getServers().get(finalI).isBusy())
                    busy = 1;
                else
                    busy = 0;

                return new SimpleObjectProperty<>(busy);
            });
            tableColumns[i].setText("S" + i);
            simulationTable.getColumns().add(tableColumns[i]);
        }
        simulationTable.getColumns().addAll(waitlineColumn,totalClientsColumn, atColumn, dtColumn);

        simulationTable.setItems(eventList);
//        simulationTable.setMouseTransparent(true);
        simulationTable.setFocusTraversable(false);
//        simulationTable.setManaged(false);
//        mainPanel.getChildren().remove(simulationTable);

//        window.initModality(Modality.APPLICATION_MODAL);
//        window.setTitle("mediConsulta - Agregar paciente");
//        window.setMinWidth(250);
//
        HBox hb = new HBox();
        hb.getChildren().addAll(mainPanel);

//        vb.getChildren().addAll();
        hb.setAlignment(Pos.CENTER);
        Scene scene = new Scene(hb);
        window.setScene(scene);
        window.show();

        title.setText("Estadísticas");
        title.setPadding(new Insets(10, 0, 10, 20)); //arriba, derecha, abajo, izquierda
//        label2.setText("Prueba 2");
//        label2.setPadding(new Insets(10, 0, 0, 20));

        ObservableList<String> statistics = FXCollections.observableArrayList();

        //statisticsListView.setPrefSize(200, 250);

        statistics.addAll("A", "B", "C", "D", "E");
        statisticsListView.setItems(statistics);
        statisticsListView.setMouseTransparent(true);
        statisticsListView.setFocusTraversable(false);
    }

    public static Day getTestDay() {
        ArrayList<TimeDistribution> arrivalDistribution = new ArrayList<>();
        arrivalDistribution.add(new TimeDistribution(3, 0.5, arrivalDistribution));
        arrivalDistribution.add(new TimeDistribution(6, 0.2, arrivalDistribution));
        arrivalDistribution.add(new TimeDistribution(9, 0.15, arrivalDistribution));
        arrivalDistribution.add(new TimeDistribution(12, 0.05, arrivalDistribution));
        arrivalDistribution.add(new TimeDistribution(15, 0.1, arrivalDistribution));

        ArrayList<TimeDistribution> serviceDistribution = new ArrayList<>();
        serviceDistribution.add(new TimeDistribution(10, 0.25, serviceDistribution));
        serviceDistribution.add(new TimeDistribution(12, 0.25, serviceDistribution));
        serviceDistribution.add(new TimeDistribution(14, 0.25, serviceDistribution));
        serviceDistribution.add(new TimeDistribution(18, 0.25, serviceDistribution));

        Config config = new Config(1, 60, 2, 4, arrivalDistribution, serviceDistribution);
        Day day = new Day(1, config);
        System.out.println(day.toString());
        return day;
    }

}
