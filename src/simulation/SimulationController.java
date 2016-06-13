package simulation;
import javafx.beans.property.StringProperty;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    //@FXML private Pane mainPanel;
    @FXML private TableView simulationTable;

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

    public void display(){
        Stage window = new Stage();
        Stage configWindow = new Stage();

//        window.initModality(Modality.APPLICATION_MODAL);
//        window.setTitle("mediConsulta - Agregar paciente");
//        window.setMinWidth(250);
//
        VBox vb = new VBox();
        //vb.getChildren().addAll(mainPanel);

        vb.getChildren().addAll();
        vb.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vb);
        window.setScene(scene);
        window.show();

    }

}
