package ui.pane.controls;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ToggleSwitch extends HBox
{
	private final Label label = new Label();
	private final Button button = new Button();
	
	private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
	public SimpleBooleanProperty switchOnProperty() { return switchedOn; }
	
	private void init() {
		
		label.setText("OFF");
		
		getChildren().addAll(label, button);	
		button.setOnAction((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		label.setOnMouseClicked((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		setStyle();
		bindProperties();
	}
	
	private void setStyle() 
	{
		// default Width
		setWidth(80);
		label.setAlignment(Pos.CENTER);
		setStyle("-fx-background-color: grey; -fx-text-fill:black; -fx-background-radius: 4;");
		setAlignment(Pos.CENTER_LEFT);
	}
	
	private void bindProperties() 
	{
		label.prefWidthProperty().bind(widthProperty().divide(2));
		label.prefHeightProperty().bind(heightProperty());
		button.prefWidthProperty().bind(widthProperty().divide(2));
		button.prefHeightProperty().bind(heightProperty());
	}
	
	public ToggleSwitch(VBox v) {
		init();
		switchedOn.addListener((a,b,c) -> {
			if(c)
			{
				label.setText("ON");
				setStyle("-fx-background-color: rgba(88, 244, 57, 0.53);");
				label.toFront();
				v.setVisible(true);
			}
			else 
			{
				label.setText("OFF");
				setStyle("-fx-background-color: rgba(0, 0, 0, 0.1);");
				button.toFront();
				v.setVisible(false);
			}
		});
	}
}
