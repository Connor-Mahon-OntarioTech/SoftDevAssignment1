//Connor Mahon
//100702878

package question4;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main extends Application {
    //Array of occurrences and alphabet for ease of access
    int[] letters = new int[26];
    char[] alpha = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                    'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    //GraphPane used to draw the graph
    GraphPane graph = new GraphPane();

    @Override
    public void start(Stage primaryStage) throws Exception{
        //setup hbox
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);

        graph.drawGraph(); //Draw empty graph

        //Create filename box, label and button
        hbox.getChildren().add(new Label("Filename:"));
        TextField filename = new TextField();
        hbox.getChildren().addAll(filename);
        Button btGraph = new Button("Graph");
        hbox.getChildren().addAll(btGraph);

        //Button action listener
        btGraph.setOnAction(e -> {
            //Relieve filename, calculate occurrence of each letter and draw the graph
            calculateOccurrence(filename.getText());
            graph.drawGraph();
        });
        //Enter key listener
        filename.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER:
                    //Relieve filename, calculate occurrence of each letter and draw the graph
                    calculateOccurrence(filename.getText());
                    graph.drawGraph();
                    break;
            }
        });

        //Crate border pane and add the GraphPane and the hbox containing text box and button
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(graph);
        borderPane.setBottom(hbox);
        //Set stage
        primaryStage.setTitle("Word Count");
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.show();
    }

    class GraphPane extends StackPane {
        //Create a canvas to draw on
        Canvas canvas = new Canvas(400,270);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        public GraphPane() {
            //add canvas to pane
            getChildren().add(canvas);
        }

        private void drawGraph() {
            //Clear the canvas
            gc.clearRect(0,0,400,270);
            gc.setStroke(Color.BLACK);

            int max = 0;
            //find max
            for (int letter: letters) {
                if (letter > max){
                    max = letter;
                }
            }
            //draw graph
            gc.strokeLine(0,250, 400, 250);
            for (int i = 0; i < 26; i++) {
                //Calculate the height of the bar and the location it should be drawn
                double height = ((double)letters[i] / max) * 200;
                double x = 10+(i*15);
                gc.strokeRect(x, 250-height, 10, 250-(250-height)); //Draw the bar representing frequency
                gc.strokeText(String.valueOf(alpha[i]), x, 262); //Draw the label
            }
        }
    }

    private void calculateOccurrence(String fileNameTxt) {
        //clear array
        for (int i = 0; i < 26; i++) {
            letters[i] = 0;
        }
        //try reading from the file
        try {
            //make reader
            BufferedReader in = new BufferedReader(new FileReader(new File(fileNameTxt)));
            //Read each character one by one and count
            int c;
            while ((c = in.read()) != -1) {
                char curr = (char) c; //BufferedReader reads in integers so convert it here to a character
                for (int i = 0; i < 26; i++) { //Check all characters in alphabet
                    if (Character.toLowerCase(curr) == alpha[i]) {
                        letters[i]++;
                    }
                }
            }
        } catch (Exception e) { //Output to console if and error occurs while reading file
            System.out.println("FAILED TO READ FILE");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}