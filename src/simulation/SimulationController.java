package simulation;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
 * Created by libcorrales and Kahnos on 12/06/2016.
 */
public class SimulationController extends HBox {

    @FXML private Pane mainPanel;
    @FXML private VBox vb;
    @FXML private Label title;
    @FXML private Label cantClientsLabel;
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

    @FXML private VBox statisticsVB;

    private Simulation simulation;

    //Charts para las estadísticas
    @FXML private PieChart cantClientsPie;

    @FXML private BarChart<String, Number> avgTimeBar;
    @FXML private CategoryAxis avgTimeBarCategory;
    @FXML private NumberAxis avgTimeBarNumber;

    @FXML private BarChart<String, Number> avgServerBar;
    @FXML private CategoryAxis avgServerBarCategory;
    @FXML private NumberAxis avgServerBarNumber;

    @FXML private TextField simulationDayField;
    @FXML private Button simulationDayButton;

    private Config config;
    private int daySelected;
    private ObservableList<Event> eventList = FXCollections.observableArrayList();



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

    public void simulationDayButtonClicked(){
        daySelected = Integer.parseInt(simulationDayField.getText());
        eventList.clear();
        eventList.addAll(simulation.getDays().get(daySelected).getEvents());
//        simulationTable.setItems(eventList);
    }

    /**
     * Displays label on top of bars in the bar charts.
     * Code taken from: jewelsea http://stackoverflow.com/questions/15237192/how-to-display-bar-value-on-top-of-bar-javafx
     * @param data
     */
    private void displayLabelForData(XYChart.Data<String, Number> data) {
        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue() + "");
        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) {
                Group parentGroup = (Group) parent;
                parentGroup.getChildren().add(dataText);
            }
        });

        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
                dataText.setLayoutX(
                        Math.round(
                                bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
                        )
                );
                dataText.setLayoutY(
                        Math.round(
                                bounds.getMinY() - dataText.prefHeight(-1) * 0.5
                        )
                );
            }
        });
    }

    public void display(Config config, Boolean simulationTableCheck){
        Stage window = new Stage();
        window.setResizable(false);

        this.config = config;
        simulation = new Simulation(config);

        statisticsVB.setPadding(new Insets(0,0,0,20));

        eventList.addAll(simulation.getDays().get(0).getEvents());

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
        simulationTable.setFocusTraversable(false);
        if (!simulationTableCheck){
            simulationTable.setManaged(false);
        }

        HBox hb = new HBox();
        hb.getChildren().addAll(mainPanel);

        hb.setAlignment(Pos.CENTER);
        Scene scene = new Scene(hb);
        window.setScene(scene);
        window.show();

        title.setText("Estadísticas");
        title.setPadding(new Insets(10, 0, 10, 20)); //arriba, derecha, abajo, izquierda

        //PieChart para la cantidad de clientes
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData.addAll(new PieChart.Data("Con espera: " + simulation.countClientsWithWait(), simulation.countClientsWithWait()),
                new PieChart.Data("Sin espera: "+ simulation.countClientsWithoutWait(), simulation.countClientsWithoutWait()),
                new PieChart.Data("Se van sin ser atendidos: " + simulation.countLostClients(), simulation.countLostClients()));

        cantClientsPie.setData(pieChartData);
        //final PieChart chart = new PieChart(pieChartData);
        cantClientsPie.setTitle("Cantidad de clientes");
        cantClientsPie.setLegendSide(Side.RIGHT);
        cantClientsPie.setLegendVisible(true);
        cantClientsPie.setLabelsVisible(false);

        // BarChart para promedio de tiempo
        ObservableList<XYChart.Series<String, Number>> timeChartData = FXCollections.observableArrayList();
        ArrayList<BarChart.Series<String, Number>> timeBarSeries = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            timeBarSeries.add(new BarChart.Series<String, Number>());
        }

        timeBarSeries.get(0).setName("En sistema");
        timeBarSeries.get(0).getData().add(new XYChart.Data<String, Number>("", simulation.calculateTotalClientTime()));

        timeBarSeries.get(1).setName("En cola");
        timeBarSeries.get(1).getData().add(new XYChart.Data<String, Number>("", simulation.calculateClientWaitTime()));

        timeBarSeries.get(2).setName("En sistema cuando hace cola");
        timeBarSeries.get(2).getData().add(new XYChart.Data<String, Number>("",
                simulation.calculateTotalWaitingClientTime()));

        timeBarSeries.get(3).setName("En sistema cuando no hace cola");
        timeBarSeries.get(3).getData().add(new XYChart.Data<String, Number>("",
                simulation.calculateTotalNonWaitingClientTime()));

        timeBarSeries.get(4).setName("Trabajo extra del negocio");
        timeBarSeries.get(4).getData().add(new XYChart.Data<String, Number>("",
                simulation.calculateExtraWorkTime()));

        // Aplica el label en el tope a cada barra del bar chart.
        for (BarChart.Series<String, Number> serie: timeBarSeries) {
            for (final XYChart.Data<String, Number> data : serie.getData()) {
                data.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                        if (node != null) {
                            displayLabelForData(data);
                        }
                    }
                });
            }
        }

        avgTimeBar.setTitle("Tiempo promedio (Minutos)");
        avgTimeBarNumber.setLabel("Minutos");
        avgTimeBar.setLegendSide(Side.BOTTOM);
        avgTimeBar.setLegendVisible(true);
        timeChartData.addAll(timeBarSeries);
        avgTimeBar.setData(timeChartData);

        // Bar chart de los servidores
        ObservableList<XYChart.Series<String, Number>> serverChartData = FXCollections.observableArrayList();
        ArrayList<BarChart.Series<String, Number>> serverBarSeries = new ArrayList<>(config.getServerAmount() + 1);
        for (int i = 0; i < config.getServerAmount(); i++) {
            serverBarSeries.add(new BarChart.Series<String, Number>());
            serverBarSeries.get(i).setName("S" + i);
            serverBarSeries.get(i).getData().add(new XYChart.Data<String, Number>("",
                    simulation.calculateServerUseTime(i) * 100));
        }

        serverBarSeries.add(new BarChart.Series<String, Number>());
        serverBarSeries.get(serverBarSeries.size() - 1).setName("General");
        serverBarSeries.get(serverBarSeries.size() - 1).getData().add(new XYChart.Data<String, Number>("",
                simulation.calculateAllServersUseTime() * 100));

        // Aplica el label en el tope a cada barra del bar chart.
        for (BarChart.Series<String, Number> serie: serverBarSeries) {
            for (final XYChart.Data<String, Number> data : serie.getData()) {
                data.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                        if (node != null) {
                            displayLabelForData(data);
                        }
                    }
                });
            }
        }

        avgServerBar.setTitle("Porcentaje de uso");
        avgServerBarNumber.setLabel("Porcentaje");
        avgServerBar.setLegendSide(Side.BOTTOM);
        avgServerBar.setLegendVisible(true);
        serverChartData.addAll(serverBarSeries);
        avgServerBar.setData(serverChartData);

        //ListView para las estadísticas
        ObservableList<String> statistics = FXCollections.observableArrayList();
        statistics.addAll("Promedio de clientes en el sitema: " + String.valueOf(simulation.countClients()),
                          "Probabilidad de esperar: " + String.valueOf(simulation.getWaitProbability()),
                          "Clientes con espera: " + String.valueOf(simulation.countClientsWithWait()),
                          "Clientes sin espera: " + String.valueOf(simulation.countClientsWithoutWait()),
                          "Clientes que se van sin ser atendidos: " + String.valueOf(simulation.countLostClients()),
                          "Tiempo promedio de los clientes en el sistema: " + String.valueOf(simulation.calculateTotalClientTime()),
                          "Tiempo promedio que un cliente está en cola: " + String.valueOf(simulation.calculateClientWaitTime()),
                          "Tiempo promedio en el sitema de un cliente que hace cola: " + String.valueOf(simulation.calculateTotalWaitingClientTime()),
                          "Tiempo promedio en el sitema de un cliente que no hace cola: " + String.valueOf(simulation.calculateTotalNonWaitingClientTime()),
                          "Tiempo promedio adicional de trabajo del negocio: " + String.valueOf(simulation.calculateExtraWorkTime()),
                          "Porcentaje de utilización de todos los servidores: " + String.valueOf(simulation.calculateAllServersUseTime()),
                          "Porcentaje de utilización del servidor"
                          );

        statisticsListView.setItems(statistics);
   /*     statisticsListView.setMouseTransparent(true);
        statisticsListView.setFocusTraversable(false);
*/
        //Recomendación cuando el tiempo de espera es mayor a 15 minutos
        if (simulation.calculateClientWaitTime()>15){
            AlertBox.display("Advertencia: Objetivo no cumplido", "                         El objetivo no se cumple.\nSe recomienda agregar más trabajadores, mejorar su desempeño o limitar la llegada de clientes estableciendo citas");
        }
        else{
            AlertBox.display("Objetivo cumplido", "                         El objetivo se cumple.\n Los clientes no esperan más de 15 minutos para ser atendidos");
        }
    }

}
