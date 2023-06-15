package ui.pane.controls;

import javafx.scene.layout.VBox;

public class SideMenu extends VBox
{
    public SideMenu()
    {
        // Header Label
        HeaderLabel hLabel = new HeaderLabel(" Asphalt Alert");
        this.getChildren().add(hLabel);
    }
}
