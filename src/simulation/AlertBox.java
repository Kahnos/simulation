package simulation;

import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Libny on 14/06/2016.
 */

public class AlertBox {
    public static void display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Button acceptButton = new Button();
        acceptButton.setOnAction(e -> window.close());
        acceptButton.setText("Aceptar");


        Label label = new Label();
        label.setText(message);
        label.setPadding(new Insets(20,20,0,10));

        VBox layout = new VBox();
        layout.getChildren().addAll(label,acceptButton);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);
        layout.setPadding(new Insets(0,0,10,0));

        window.setResizable(false);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();
    }
}
