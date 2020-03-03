//Connor Mahon
//100702878

package question2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Prep a grid pane
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10,10,10,10));
        pane.setVgap(5);
        pane.setHgap(5);

        //Making text boxes and label them
        //Initial value box
        TextField initBox = new TextField();
        pane.add(new Label("Investment Amount:"),0,0);
        pane.add(initBox, 1, 0);
        //Years box
        TextField yearsBox = new TextField();
        pane.add(new Label("Years:"),0,1);
        pane.add(yearsBox, 1, 1);
        //Investment rate box
        TextField rateBox = new TextField();
        pane.add(new Label("Annual Interest Rate:"),0,2);
        pane.add(rateBox, 1, 2);
        //Final value box, set to be not editable by user
        TextField finalBox = new TextField();
        pane.add(new Label("Future Value:"),0,3);
        pane.add(finalBox, 1, 3);
        finalBox.setEditable(false);
        finalBox.setDisable(true);

        //Create the button
        Button calcBtt = new Button("Calculate");
        pane.add(calcBtt, 1,4);
        //Crate a label to display an error if no valid entry is made
        Label warning = new Label("");
        pane.add(warning, 0, 4);

        //Add button functionality
        calcBtt.setOnAction(button -> {
            try {
                //retrieve values from text boxes
                double initialVaue = Double.valueOf(initBox.getText());
                int years = Integer.valueOf(yearsBox.getText());
                double rate = Double.valueOf(rateBox.getText());
                rate = rate / 100; //correct rate to percent
                //Calculate the final value using equation provided
                double futureVal = (initialVaue * Math.pow(1 + (rate / 12), years*12));
                futureVal = Math.round(futureVal*100.0)/100.0; //round to 2 decimals
                //Set the value of the final value text box and clear any warnings that may be there
                finalBox.setText(String.valueOf(futureVal));
                warning.setText("");
            }catch (NumberFormatException e) {
                //if any of the input values are invalid display a warning
                warning.setText("INVALID ENTRY");
                finalBox.setText("INVALID ENTRY");
            }

        });
        GridPane.setHalignment(calcBtt, HPos.RIGHT); //put button on far right

        //Setup stage and ad pane
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Investment Calculator");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}