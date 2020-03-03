package question4;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Main extends Application {

    int[] letters = new int[26];
    char[] alpha = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                    'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    GraphPane graph = new GraphPane();

    @Override
    public void start(Stage primaryStage) throws Exception{
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);

        graph.drawGraph();
        hbox.getChildren().add(new Label("Filename:"));
        TextField filename = new TextField();
        hbox.getChildren().addAll(filename);
        Button btGraph = new Button("Graph");
        hbox.getChildren().addAll(btGraph);

        btGraph.setOnAction(e -> {
            calculateOccurrence(filename.getText());
            graph.drawGraph();
        });

        filename.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER:
                    calculateOccurrence(filename.getText());
                    graph.drawGraph();
                    break;
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(graph);
        borderPane.setBottom(hbox);

        primaryStage.setTitle("Word Count");
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.show();
    }

    class GraphPane extends StackPane {
        Canvas canvas = new Canvas(400,270);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        public GraphPane() {
            getChildren().add(canvas);
        }

        private void drawGraph() {
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
                double height = ((double)letters[i] / max) * 200;
                double x = 10+(i*15);
                gc.strokeRect(x, 250-height, 10, 250-(250-height));
                gc.strokeText(String.valueOf(alpha[i]), x, 262);
            }
        }
    }

    private void calculateOccurrence(String fileNameTxt) {
        //clear array
        for (int i = 0; i < 26; i++) {
            letters[i] = 0;
        }
        //make reader
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File(fileNameTxt)));
            //Read each character and count
            int c;
            while ((c = in.read()) != -1) {
                char curr = (char) c;
                for (int i = 0; i < 26; i++) {
                    if (Character.toLowerCase(curr) == alpha[i]) {
                        letters[i]++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("FAILED TO READ FILE");
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}