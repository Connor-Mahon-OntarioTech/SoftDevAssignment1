package question1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        int[] cards = {0,0,0}; //Represent the 3 cards
        for (int i = 0; i < 3; i++) { //Loop through all 3 cards
            int newCard = new Random().nextInt(52) + 1;
            for (int j = 0; j < 3; j++){ //Check all cards for duplicates
                //If a duplicate is found, it restarts
                if (newCard == cards[j]) {
                    i = 0; //may not be the most efficient way at handling duplicates but it works pretty neatly
                    System.out.println("Duplicate");
                }
            }
            cards[i] = newCard;
        }

        //Some debug output
        //for (int i = 0; i < 3; i++) {
        //    System.out.println(cards[i]);
        //}

        Pane pane = new HBox(10);
        pane.setPadding(new Insets(5,5,5,5));

        //Load images and resize them
        ImageView card0 = new ImageView(new Image(getClass()
                .getResourceAsStream("cards/" + String.valueOf(cards[0]) + ".png")));
        card0.setFitHeight(153);
        card0.setFitWidth(100);
        ImageView card1 = new ImageView(new Image(getClass()
                .getResourceAsStream("cards/" + String.valueOf(cards[1]) + ".png")));
        card1.setFitHeight(153);
        card1.setFitWidth(100);
        ImageView card2 = new ImageView(new Image(getClass()
                .getResourceAsStream("cards/" + String.valueOf(cards[2]) + ".png")));
        card2.setFitHeight(153);
        card2.setFitWidth(100);

        //Setup and display pane and stage
        pane.getChildren().addAll(card0, card1, card2);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cards");
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
