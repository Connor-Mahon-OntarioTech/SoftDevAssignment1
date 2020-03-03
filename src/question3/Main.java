package question3;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

    //Setup canvas stuff
    Group root = new Group();
    Canvas canvas = new Canvas(300, 300);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    @Override
    public void start(Stage primaryStage) throws Exception {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        //Create the points
        for (int i = 0; i < 3; i++) {
            points[i].setCenterX(new Random().nextInt(300));
            points[i].setCenterY(new Random().nextInt(300));
            points[i].setRadius(8);
            //Correct the points onto the circle
            correctPoint(i);
            angles[i].setX(points[i].getCenterX() + 10);
            angles[i].setY(points[i].getCenterY());
            points[i].setOnMouseDragged(new PointDragHandler(i)); //The event handler
            points[i].setStroke(Color.BLACK);
            points[i].setFill(Color.RED);
        }
        //draw the triangle
        drawCanvas(gc);

        //pane and stage stuff
        root.getChildren().addAll(points[0], points[1], points[2]);
        root.getChildren().addAll(angles[0], angles[1], angles[2]);
        root.getChildren().add(canvas);
        canvas.toBack();
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
            points[i].setCenterX(event.getX());
            points[i].setCenterY(event.getY());
            correctPoint(i);
            angles[i].setX(points[i].getCenterX() + 10);
            angles[i].setY(points[i].getCenterY());
            drawCanvas(gc);
        }
    }


    public void drawCanvas(GraphicsContext gc) {
        //clear canvas
        gc.clearRect(0,0,300,300);
        //Draw the triangle and get the side lengths
        double[] lens = new double[3];
        for (int i = 0; i < 3; i++) {
            int j = i + 1;
            if ((i + 1) > 2) {
                j = 0;
            }
            double x1 = points[i].getCenterX(), y1 = points[i].getCenterY();
            double x2 = points[j].getCenterX(), y2 = points[j].getCenterY();
            gc.strokeLine(x1, y1, x2, y2);
            lens[i] = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        }
        //Calculate angles
        double a = lens[0], b = lens[1], c = lens[2]; //convert to make more readable
        double angle;
        angle = Math.acos((a*a - b*b - c*c) / (-2 * b * c));
        angle = Math.round(Math.toDegrees(angle));
        angles[2].setText(String.valueOf(angle));
        angle = Math.acos((b*b - a*a - c*c) / (-2 * a * c));
        angle = Math.round(Math.toDegrees(angle));
        angles[0].setText(String.valueOf(angle));
        angle = Math.acos((c*c - b*b - a*a) / (-2 * a * b));
        angle = Math.round(Math.toDegrees(angle));
        angles[1].setText(String.valueOf(angle));

        //Draw the circle
        double circleR = 200; //THIS CAN NOT BE CHANGED ANYMORE
        double circleX = (300 - circleR) / 2, circleY = (300 - circleR) / 2;
        gc.strokeArc(circleY, circleX, circleR, circleR, 0, 360, ArcType.CHORD);
    }

    public void correctPoint(int i) {
        double newX = 150 + 100 * ((points[i].getCenterX() - 150) / Math.sqrt(Math.pow(points[i].getCenterX() - 150, 2)
                + Math.pow(points[i].getCenterY() - 150, 2)));
        double newY = 150 + 100 * ((points[i].getCenterY() - 150) / Math.sqrt(Math.pow(points[i].getCenterX() - 150, 2)
                + Math.pow(points[i].getCenterY() - 150, 2)));
        points[i].setCenterX(newX);
        points[i].setCenterY(newY);
    }


    public static void main(String[] args) {
        launch(args);
    }
}