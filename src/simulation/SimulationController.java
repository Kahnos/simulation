package simulation;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
public class SimulationController extends VBox {

    @FXML private Pane mainPanel;
    @FXML private Label q;

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
    @FXML private TableColumn [] tableColumns;
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

        //Creación de las columnas de la tabla de simulación
        //Eventos
        idColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        idColumn.setText("Evento");
        //Tipo de evento
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setText("Tipo");
        //Número de cliente
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        clientColumn.setText("Cliente");
        //TM
        tmColumn.setCellValueFactory(new PropertyValueFactory<>("TM"));
        tmColumn.setText("TM");
        simulationTable.getColumns().addAll(idColumn,typeColumn,clientColumn,tmColumn);

        //Cola de espera
        waitlineColumn.setCellValueFactory(waitline -> new SimpleObjectProperty<Integer>(waitline.getValue().getWaitLine().size()));
        waitlineColumn.setText("Cola");
        //Total de clientes en el sistema
        totalClientsColumn.setCellValueFactory(new PropertyValueFactory<>("totalClients"));
        totalClientsColumn.setText("");
        //AT
        atColumn.setCellValueFactory(new PropertyValueFactory<>("nextArrivalTime"));
        atColumn.setText("AT");
        //DT
        dtColumn.setCellValueFactory(new PropertyValueFactory<>("nextDepartureTime"));
        dtColumn.setText("DT");

        tableColumns = new TableColumn[config.getServerAmount()];

//        window.initModality(Modality.APPLICATION_MODAL);
//        window.setTitle("mediConsulta - Agregar paciente");
//        window.setMinWidth(250);
//
        VBox vb = new VBox();
        vb.getChildren().addAll(mainPanel);

//        vb.getChildren().addAll();
        vb.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vb);
        window.setScene(scene);
        window.show();




    }

}
