import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ui.pane.MasterPane;

public class Main extends Application
{
	public static void main(String args[]) 
    {
		System.setProperty("org.graphstream.ui", "javafx");
        launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setMaximized(true);
		primaryStage.setTitle("Asphalt Alert");
		primaryStage.setScene(new Scene(new MasterPane()));

		// set window icon
		primaryStage.setOnShown(e -> {
		});
		primaryStage.getIcons().add(new Image("file:data/icons/pothole.png"));

		primaryStage.show();
	}
}

