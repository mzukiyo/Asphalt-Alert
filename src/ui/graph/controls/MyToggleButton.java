package ui.graph.controls;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyToggleButton extends ToggleButton
{
    public MyToggleButton(String text)
    {
        this.setMaxSize(100, 40);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1);");

        setPicture("data/icons/close-eye.png");
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1);");
        this.setText(text);
    }

    public void setPicture(String file)
    {
        ImageView imageView = new ImageView(new Image("file:" + file));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        this.setGraphic(imageView);
    }
}
