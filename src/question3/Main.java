//Connor Mahon
//100702878

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
    //Declare the points and labels for angles here for easy access
    Circle[] points = {new Circle(), new Circle(), new Circle()};
    Text[] angles = {new Text(), new Text(), new Text()};

    //Setup canvas
    Group root = new Group();
    Canvas canvas = new Canvas(300, 300);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    @Override
    public void start(Stage primaryStage) throws Exception {
        //set some canvas stuff
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        //Create the points
        for (int i = 0; i < 3; i++) {
            //Random start locations
            points[i].setCenterX(new Random().nextInt(300));
            points[i].setCenterY(new Random().nextInt(300));
            //Point size
            points[i].setRadius(8);
            //Correct the points onto the circle
            correctPoint(i);
            //Set label locations beside the points
            angles[i].setX(points[i].getCenterX() + 10);
            angles[i].setY(points[i].getCenterY());
            //Add the event handler for the point
            points[i].setOnMouseDragged(new PointDragHandler(i));
            //set colour for the points
            points[i].setStroke(Color.BLACK);
            points[i].setFill(Color.RED);
            //and the point and its label to the pane
            root.getChildren().addAll(points[i], angles[i]);
        }

        //draw the (initial) triangle and circle
        drawCanvas(gc);

        //pane stuff
        root.getChildren().add(canvas);
        canvas.toBack(); //set canvas behind the points to prevent interfering with point mouse listeners
        primaryStage.setTitle("Circle and Triangle");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    class PointDragHandler implements EventHandler<MouseEvent> {
        int i; //store the index of the point this handler is assigned to
        PointDragHandler(int i) {
            this.i = i;
        }
        //handling the mouse event
        public void handle(MouseEvent event) {
            //move the point to mouse location
            points[i].setCenterX(event.getX());
            points[i].setCenterY(event.getY());
            //Correct the point onto circle so it can not be dragged off
            correctPoint(i);
            //set the new location for the angle label
            angles[i].setX(points[i].getCenterX() + 10);
            angles[i].setY(points[i].getCenterY());
            //redraw the triangle and circle
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
            //correct the last loop to complete triangle
            if ((i + 1) > 2) {
                j = 0;
            }
            //extract the coordinates
            double x1 = points[i].getCenterX(), y1 = points[i].getCenterY();
            double x2 = points[j].getCenterX(), y2 = points[j].getCenterY();
            //draw the lines
            gc.strokeLine(x1, y1, x2, y2);
            //save the side length to calculate the angles later
            lens[i] = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        }
        //Calculate angles using provided equations
        double a = lens[0], b = lens[1], c = lens[2]; //extract to make code more readable
        double angle;
        //angle A
        angle = Math.acos((a*a - b*b - c*c) / (-2 * b * c));
        angle = Math.round(Math.toDegrees(angle)); //Convert from radians to degrees and round the value (done to each)
        angles[2].setText(String.valueOf((int)angle));
        //angle B
        angle = Math.acos((b*b - a*a - c*c) / (-2 * a * c));
        angle = Math.round(Math.toDegrees(angle));
        angles[0].setText(String.valueOf((int)angle));
        //angle C
        angle = Math.acos((c*c - b*b - a*a) / (-2 * a * b));
        angle = Math.round(Math.toDegrees(angle));
        angles[1].setText(String.valueOf((int)angle));

        //Draw the circle
        double circleR = 200;
        double circleX = (300 - circleR) / 2, circleY = (300 - circleR) / 2;
        gc.strokeArc(circleY, circleX, circleR, circleR, 0, 360, ArcType.CHORD);
    }

    public void correctPoint(int i) {
        //Find the corrected x value
        double newX = 150 + 100 * ((points[i].getCenterX() - 150) / Math.sqrt(Math.pow(points[i].getCenterX() - 150, 2)
                + Math.pow(points[i].getCenterY() - 150, 2)));
        //Find the corrected y value
        double newY = 150 + 100 * ((points[i].getCenterY() - 150) / Math.sqrt(Math.pow(points[i].getCenterX() - 150, 2)
                + Math.pow(points[i].getCenterY() - 150, 2)));
        //set the new coordinates
        points[i].setCenterX(newX);
        points[i].setCenterY(newY);
    }


    public static void main(String[] args) {
        launch(args);
    }
}