package question4;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

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

        calculateOccurrence();
        graph.drawGraph();
        Button btGraph = new Button("Graph");
        hbox.getChildren().addAll(btGraph);
        //hbox.getChildren().add(new Label("Filename:"));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(graph);
        borderPane.setBottom(hbox);

        primaryStage.setTitle("Word Count");
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.show();
    }

    class GraphPane extends StackPane {
        Group root = new Group();
        Canvas canvas = new Canvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        public GraphPane() {
            root.getChildren().add(canvas);
            getChildren().add(root);
        }

        private void drawGraph() {
            gc.setStroke(Color.BLACK);
            int max = 0;
            //find max
            for (int letter: letters) {
                if (letter > max){
                    max = letter;
                }
            }
            //draw graph
            for (int i = 0; i < 26; i++) {
                double height = ((double)letters[i] / max) * 200;
                double x = 10+(i*15);
                gc.strokeRect(x, 250-height, 10, 250-(250-height));
            }
        }
    }

    private void calculateOccurrence() throws IOException {
        //clear array
        for (int i = 0; i < 26; i++) {
            letters[i] = 0;
        }
        //make reader
        BufferedReader in = new BufferedReader(new FileReader(new File("src/question4/hp.txt")));
        //Read each character and count
        int c;
        while ((c = in.read()) != -1) {
            char curr = (char)c;
            for (int i = 0; i < 26; i++) {
                if (Character.toLowerCase(curr) == alpha[i]) {
                    letters[i] ++;
                }
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}