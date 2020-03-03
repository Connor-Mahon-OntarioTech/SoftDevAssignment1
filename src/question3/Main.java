package question3;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Random;


public class Main extends Application {

    Circle[] points = {new Circle(), new Circle(), new Circle()};
    Text[] angles = {new Text(), new Text(), new Text()};
    Group root = new Group();

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Setup canvas stuff

        Canvas canvas = new Canvas(300,300);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        //Draw the circle
        double circleR = 200; //Wrote the code so this can be changed
        double circleX = (300-circleR)/2, circleY = (300-circleR)/2;
        gc.strokeArc(circleY,circleX, circleR, circleR, 0, 360, ArcType.CHORD);

        //Create the points
        for (int i = 0; i < 3; i++) {
            points[i].setCenterX(new Random().nextInt(300));
            points[i].setCenterY(new Random().nextInt(300));
            points[i].setRadius(8);
            angles[i].setX(points[i].getCenterX()+10);
            angles[i].setY(points[i].getCenterY()+10);
            //points[i].setOnMouseDragged(new PointDragHandler(i)); //The event handler
            points[i].setStroke(Color.BLACK);
            points[i].setFill(Color.RED);
        }

        points[0].setOnMouseClicked(e -> {
            System.out.println("test");
        });
        points[1].setOnMouseClicked(e -> {
            System.out.println("test");
        });
        points[2].setOnMouseClicked(e -> {
            System.out.println("test");
        });

        //draw the triangle
        drawTriangle(gc);

        //pane and stage stuff
        root.getChildren().addAll(points[0], points[1], points[2]);
        root.getChildren().addAll(angles[0], angles[1], angles[2]);
        root.getChildren().add(canvas);
        primaryStage.setTitle("Circle and Triangle");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    class PointDragHandler implements EventHandler<MouseEvent> {
        int i; //store the index of the point this handler is assigned to
        PointDragHandler(int i) {
            this.i = i;
        }
        public void handle(MouseEvent event) {
            System.out.println(event.getX() + "," + event.getY());
            points[i].setCenterX(event.getX());
            points[i].setCenterY(event.getY());
        }
    }


    public void drawTriangle(GraphicsContext gc) {
        double[] lens = new double[3];
        for (int i = 0; i < 3; i++) {
            int j = i+1;
            if ((i+1) > 2) {
                j = 0;
            }
            double x1 = points[i].getCenterX(), y1 = points[i].getCenterY();
            double x2 = points[j].getCenterX(), y2 = points[j].getCenterY();
            gc.strokeLine(x1, y1, x2, y2);
            lens[i] = Math.sqrt(Math.pow(x1+x2,2)+Math.pow(y1+y2,2));
        }
        angles[0].setText(String.valueOf(Math.round(Math.toDegrees(Math.acos((lens[0] * lens[0] - lens[1] *
                lens[1] - lens[2] * lens[2]) / (-2 * lens[1] * lens[2]))))));
        angles[1].setText(String.valueOf(Math.round(Math.toDegrees(Math.acos((lens[1] * lens[1] - lens[0] *
                lens[0] - lens[2] * lens[2]) / (-2 * lens[0] * lens[2]))))));
        angles[2].setText(String.valueOf(Math.round(Math.toDegrees(Math.acos((lens[2] * lens[2] - lens[1] *
                lens[1] - lens[0] * lens[0]) / (-2 * lens[0] * lens[1]))))));
    }


    public static void main(String[] args) {
        launch(args);
    }
}

