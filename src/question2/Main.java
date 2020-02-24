package question2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10,10,10,10));
        pane.setVgap(5);
        pane.setHgap(5);

        TextField initBox = new TextField();
        pane.add(new Label("Investment Amount:"),0,0);
        pane.add(initBox, 1, 0);

        TextField yearsBox = new TextField();
        pane.add(new Label("Years:"),0,1);
        pane.add(yearsBox, 1, 1);

        TextField rateBox = new TextField();
        pane.add(new Label("Annual Interest Rate:"),0,2);
        pane.add(rateBox, 1, 2);

        TextField finalBox = new TextField();
        pane.add(new Label("Future Value:"),0,3);
        pane.add(finalBox, 1, 3);
        finalBox.setEditable(false);
        finalBox.setDisable(true);


        Button calcBtt = new Button("Calculate");
        pane.add(calcBtt, 1,4);
        Label warning = new Label("");
        pane.add(warning, 0, 4);

        calcBtt.setOnAction(button -> {
            try {
                double initialVaue = Double.valueOf(initBox.getText());
                int years = Integer.valueOf(yearsBox.getText());
                double rate = Double.valueOf(rateBox.getText());
                rate = rate / 100;
                double futureVal = (initialVaue * Math.pow(1 + (rate / 12), years*12));
                futureVal = Math.round(futureVal*100.0)/100.0;
                finalBox.setText(String.valueOf(futureVal));
                warning.setText("");
            }catch (NumberFormatException e) {
                warning.setText("INVALID ENTRY");
                finalBox.setText("INVALID ENTRY");
            }

        });
        GridPane.setHalignment(calcBtt, HPos.RIGHT);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Investment Calculator");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}