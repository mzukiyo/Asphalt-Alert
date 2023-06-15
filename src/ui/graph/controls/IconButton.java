package ui.graph.controls;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconButton extends Button
{
    public IconButton(String file)
    {
        ImageView imageView = new ImageView(new Image("file:" + file));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        this.setGraphic(imageView);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1);");
    }
}
