package ui.pane.controls;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HeaderLabel extends Label
{
    private final int L_SIZE = 35;
    
    public HeaderLabel(String text)
    {
        super(text);
        this.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        ImageView hImageView = new ImageView(new Image("file:data/icons/pothole.png"));
        hImageView.setFitWidth(L_SIZE);
        hImageView.setFitHeight(L_SIZE);
        this.setGraphic(hImageView);
        this.setAlignment(Pos.BASELINE_CENTER);
    }
}
