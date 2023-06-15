package ui.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class InfoPane extends VBox
{
    // information about program
    public InfoPane(Stage stage)
    {
        Text title = new Text("Welcome to Asphalt Alert");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 24));

        Text body = new Text();
        body.setFont(Font.font("Calibri", 16));

        String content = "";
        content += "The City Picker to choose from various cities.\n\n";
        content += "The Text Box displays important information as you use the application.\n\n";
        content += "Potholes: Adding, Removing and Getting Information about Potholes.\n\n";
        content += "Connections: Adding & Removing Connections between Potholes based on Distance.\n\n";
        content += "Algorithms:\n";
        content += "  i) Prim's Algorithm - Minimum Spanning Set of Potholes.\n";
        content += "  ii) Dijkstra's Algorithm\n";
        content += "  - Shortest Path between 2 Potholes.\n";
        content += "  - Shortest Path between 2 Potholes via a 3rd Pothole.\n";
        body.setText(content);
        body.setTextAlignment(TextAlignment.CENTER);

        Button btnClose = new Button("Close");
        btnClose.setPrefSize(100, 30);
        
        this.getChildren().addAll(title, body, btnClose);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setPadding(new Insets(20));

        btnClose.setOnAction(e -> {
            stage.close();
        });
    }

}
