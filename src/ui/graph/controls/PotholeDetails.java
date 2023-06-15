package ui.graph.controls;

import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PotholeDetails extends VBox
{
    public PotholeDetails()
    {
        HBox h1= new HBox();
        HBox h2 = new HBox();
        HBox h3 = new HBox();

        Label vLabel = new Label("SANRAL Pothole Classification List");
        vLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Circle class1 = new Circle(5);
        class1.setFill(Paint.valueOf("rgb(238, 255, 0)"));
        Text tClass1 = new Text("Class 1: <25mm depth & <100mm diameter");
        tClass1.setFont(Font.font("Calibri", FontWeight.NORMAL, 14));
        h1.getChildren().addAll(class1, tClass1);
        h1.setSpacing(10);

        Circle class2 = new Circle(5);
        class2.setStyle("-fx-background-color: rgb(255, 153, 0);");
        class2.setFill(Paint.valueOf("rgb(255, 153, 0)"));
        Text tClass2 = new Text("Class 2: 25mm - 50mm depth &  100mm - 300mm diameter");
        tClass2.setFont(Font.font("Calibri", FontWeight.NORMAL, 14));
        h2.getChildren().addAll(class2, tClass2);
        h2.setSpacing(10);
        
        Circle class3 = new Circle(5);
        class3.setFill(Paint.valueOf("rgb(228, 11, 0)"));
        Text tClass3 = new Text("Class 3: >50mm depth & >300mm diameter");
        tClass3.setFont(Font.font("Calibri", FontWeight.NORMAL, 14));
        h3.getChildren().addAll(class3, tClass3);
        h3.setSpacing(10);

        this.setSpacing(5);
        this.getChildren().addAll(vLabel, h1, h2, h3);
        this.setVisible(false);
        this.setMaxSize(100, 60);

        Border border = new Border(new BorderStroke(
        Color.AQUA, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, new BorderWidths(2)));

        // set the border to the VBox
        this.setBorder(border);
        this.setStyle("-fx-background-color: rgba(164, 164, 162, 0.1);");
    }
}
