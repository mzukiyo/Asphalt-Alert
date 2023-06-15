package helper;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MyAlert 
{
    @SuppressWarnings("unused")
    private static boolean IsANumber(String num)
    {
        try
        {
            double nodeToRemove = Double.parseDouble(num);
            return true;
        }
        catch(NumberFormatException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Not a Number");
            alert.setContentText("Positive numbers only. Use the graph to assist you.");
            alert.setGraphic(new ImageView(new Image("file:data/icons/user.png", 50, 50, true, true)));
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    	    stage.getIcons().add(new Image("file:data/icons/warning.png"));

            // Display the alert dialog
            alert.showAndWait();
            return false;
        }
    }

    public static boolean IsPositive(double num)
    {
        if(num < 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Negative Number");
            alert.setContentText("Positive numbers only. Use the graph to assist you.");
            alert.setGraphic(new ImageView(new Image("file:data/icons/user.png", 50, 50, true, true)));
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:data/icons/warning.png"));

            // Display the alert dialog
            alert.showAndWait();

            return false;
        }

        return true;
    }

    public static boolean preChecks(String num)
    {
        double intNum;

        if(IsANumber(num))
        {
            intNum = Double.parseDouble(num);
            return IsPositive(intNum);
        }

        return false;
    }

    public static void elementNotFound()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Element Not Found");
        alert.setContentText("Element(s) not on the graph. Use the graph to assist you.");
        alert.setGraphic(new ImageView(new Image("file:data/icons/user.png", 50, 50, true, true)));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image("file:data/icons/warning.png"));

        // Display the alert dialog
        alert.showAndWait();
    }

    public static void edgeNotFound()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Edge Not Found");
        alert.setContentText("Edge specified is not on the graph. Use the graph to assist you.");
        alert.setGraphic(new ImageView(new Image("file:data/icons/user.png", 50, 50, true, true)));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image("file:data/icons/warning.png"));

        // Display the alert dialog
        alert.showAndWait();
    }

    public static void noTextFound()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Textfield(s) Incomplete");
        alert.setContentText("Certain textfield(s) are incomplete, try again.");
        alert.setGraphic(new ImageView(new Image("file:data/icons/user.png", 50, 50, true, true)));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image("file:data/icons/warning.png"));

        // Display the alert dialog
        alert.showAndWait();
    }

    public static void savedSuccessfully()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Saved Successfully");
        alert.setHeaderText("The graph has been successfully saved!");
        alert.setContentText(null);
        alert.setGraphic(new ImageView(new Image("file:data/icons/check.png", 50, 50, true, true)));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image("file:data/icons/check.png"));

        // Display the alert dialog
        alert.showAndWait();
    }

    public static void alreadyImported()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Graph Already Imported");
        alert.setContentText("The graph you are trying to import is already on the canvas.");
        alert.setGraphic(new ImageView(new Image("file:data/icons/user.png", 50, 50, true, true)));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image("file:data/icons/warning.png"));

        // Display the alert dialog
        alert.showAndWait();
    }

    public static void biggerThan20()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Connection cannot be greater than 20m");
        alert.setContentText("Create an connection smaller than 20 with a different pothole.");
        alert.setGraphic(new ImageView(new Image("file:data/icons/user.png", 50, 50, true, true)));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image("file:data/icons/warning.png"));

        // Display the alert dialog
        alert.showAndWait();
    }

    public static void alreadyConnected()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Only one connection can exist between any 2 potholes.");
        alert.setContentText("Use the graph to assist you.");
        alert.setGraphic(new ImageView(new Image("file:data/icons/user.png", 50, 50, true, true)));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image("file:data/icons/warning.png"));

        // Display the alert dialog
        alert.showAndWait();
    }
}
