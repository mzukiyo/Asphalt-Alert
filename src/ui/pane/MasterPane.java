package ui.pane;

import helper.MyAlert;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ui.graph.GraphCanvas;
import ui.pane.controls.SideMenu;

/**
 * The pane that controls all the other panes
 * @author KM KIYO
 */
public class MasterPane extends BorderPane
{
    // Constants
    private final int BTN_WIDTH = 400;
    private final int BTN_HEIGHT = 30;
    private final int TF_SIZE = 30;

    GraphCanvas j;
    GraphCanvas c;
    GraphCanvas d;

	/** Constructor */
	public MasterPane()
	{
        // creates cities
        j = new GraphCanvas(new String("J"));
        c = new GraphCanvas(new String("C"));
        d = new GraphCanvas(new String("D"));

		// creates the main menu bar
        MenuBar menuBar = new MenuBar();

        // creates a menu "Options"
        Menu menuOptions = new Menu("Options"); 
        // adds the menu "Options" to the menu bar
        menuBar.getMenus().add(menuOptions);

        MenuItem opInfo = new MenuItem("User Guide");
        MenuItem opRestart = new MenuItem("Restart");
        MenuItem opExit = new MenuItem("Exit");

        // adds menuItems to menu "Options"
        menuOptions.getItems().addAll(opInfo, new SeparatorMenuItem(), opRestart, new SeparatorMenuItem(), opExit);

        opExit.setOnAction(e -> {
            Platform.exit();
        });

        // side menu setup
        SideMenu sideMenu = new SideMenu();
        
        // city option
        GridPane cityGridPane = new GridPane();
        Label cityLabel = new Label("City: ");
        cityGridPane.add(cityLabel, 0, 0);
        ComboBox<String> cityChoice = new ComboBox<String>();
        cityChoice.setPrefSize(BTN_WIDTH - 50, BTN_HEIGHT);
        cityGridPane.add(cityChoice, 1, 0);
        cityChoice.getItems().addAll("Johannesburg", "Cape Town", "Durban");
        cityChoice.setValue("Johannesburg");
        cityGridPane.setHgap(20);
        cityGridPane.setPadding(new Insets(0, 0, 5, 0));
        cityGridPane.setAlignment(Pos.TOP_CENTER);
        
        // buttons
        Button btnAddNode = new Button("Log Pothole");
        btnAddNode.setMinSize(BTN_WIDTH / 2 - 5, BTN_HEIGHT);
        Button btnRemoveNode = new Button("Remove Pothole");
        btnRemoveNode.setMinSize(BTN_WIDTH / 2 - 5, BTN_HEIGHT);
        Button btnGetInfo = new Button("Get Pothole Info");
        btnGetInfo.setMinSize(BTN_WIDTH / 2 - 5, BTN_HEIGHT);
        Button btnSetInfo = new Button("Update Pothole Info");
        btnSetInfo.setMinSize(BTN_WIDTH / 2 - 5, BTN_HEIGHT);

        TextField tNodeID = new TextField();
        tNodeID.setPromptText("Pothole ID");
        tNodeID.setMaxSize(BTN_WIDTH / 3 - 6, TF_SIZE);
        TextField tNodeD1 = new TextField();
        tNodeD1.setPromptText("Depth (mm)");
        tNodeD1.setMaxSize(BTN_WIDTH / 3 - 6, TF_SIZE);
        TextField tNodeD2 = new TextField();
        tNodeD2.setPromptText("Diameter (mm)");
        tNodeD2.setMaxSize(BTN_WIDTH / 3 - 6, TF_SIZE);

        HBox hTNodes = new HBox();
        hTNodes.getChildren().addAll(tNodeID, tNodeD1, tNodeD2);
        hTNodes.setSpacing(10);
        hTNodes.setAlignment(Pos.CENTER);

        HBox hBtnNodes = new HBox();
        hBtnNodes.setSpacing(10);
        hBtnNodes.getChildren().addAll(btnAddNode, btnRemoveNode);
        hBtnNodes.setAlignment(Pos.CENTER);

        HBox hBtnInfo = new HBox();
        hBtnInfo.setSpacing(10);
        hBtnInfo.getChildren().addAll(btnGetInfo, btnSetInfo);
        hBtnInfo.setAlignment(Pos.CENTER);

        Button btnAddEdge = new Button("Add Connection");
        btnAddEdge.setMinSize(BTN_WIDTH / 2 - 5, BTN_HEIGHT);
        Button btnRemoveEdge = new Button("Remove Connection");
        btnRemoveEdge.setMinSize(BTN_WIDTH / 2 - 5, BTN_HEIGHT);

        TextField tEdgeOne = new TextField();
        tEdgeOne.setPrefSize(BTN_WIDTH / 3 - 6, TF_SIZE);
        tEdgeOne.setPromptText("Pothole A");
        TextField tEdgeTwo = new TextField();
        tEdgeTwo.setPrefSize(BTN_WIDTH / 3 - 6, TF_SIZE);
        tEdgeTwo.setPromptText("Pothole B");
        TextField tEdgeWeight = new TextField();
        tEdgeWeight.setPrefSize(BTN_WIDTH / 3 - 6, TF_SIZE);
        tEdgeWeight.setPromptText("Distance (m)");

        HBox hbEdgeBtns = new HBox();
        hbEdgeBtns.setSpacing(10);
        hbEdgeBtns.getChildren().addAll(btnAddEdge, btnRemoveEdge);
        hbEdgeBtns.setAlignment(Pos.CENTER);

        HBox hbEdgeAreas = new HBox();
        hbEdgeAreas.setSpacing(10);
        hbEdgeAreas.getChildren().addAll(tEdgeOne, tEdgeTwo, tEdgeWeight);
        hbEdgeAreas.setAlignment(Pos.CENTER);

        Line dividerLineA = new Line(0, 0, 200, 0);
        dividerLineA.setStroke(Color.AQUA);
        Line dividerLineB = new Line(0, 0, 200, 0);
        dividerLineB.setStroke(Color.AQUA);
        Line dividerLineC = new Line(0, 0, 200, 0);
        dividerLineC.setStroke(Color.AQUA);
        Line dividerLineD = new Line(0, 0, 200, 0);
        dividerLineD.setStroke(Color.AQUA);
        
        Button btnPrim = new Button("Minimum Spanning Tree");
        btnPrim.setMinSize(BTN_WIDTH, BTN_HEIGHT);
        Button btnDijkstra = new Button("Shortest Distance");
        btnDijkstra.setMinSize(BTN_WIDTH, BTN_HEIGHT);

        TextField tDStart = new TextField();
        tDStart.setPromptText("Starting Pothole");
        tDStart.setPrefSize(BTN_WIDTH / 3 - 6, TF_SIZE);
        TextField tDMid = new TextField();
        tDMid.setPromptText("Intermediary Pothole");
        tDMid.setPrefSize(BTN_WIDTH / 3 - 6, TF_SIZE);
        TextField tDEnd = new TextField();
        tDEnd.setPromptText("Ending Pothole");
        tDEnd.setPrefSize(BTN_WIDTH / 3 - 6, TF_SIZE);

        HBox hbDAreas = new HBox();
        hbDAreas.setSpacing(10);
        hbDAreas.getChildren().addAll(tDStart, tDMid, tDEnd);
        hbDAreas.setAlignment(Pos.CENTER);

        Button btnDepthFirst = new Button("Depth First Search");
        btnDepthFirst.setMinSize(BTN_WIDTH, BTN_HEIGHT);

        TextArea tInfo = new TextArea();
        tInfo.setEditable(false); // read-only
        tInfo.setMaxWidth(BTN_WIDTH);
        tInfo.setFont(Font.font("Calibri", 17));
        tInfo.setPromptText("...");
        tInfo.setWrapText(true); // removes scrollbar

        // refresh button
        Button btnClear = new Button();
        ImageView bImageView = new ImageView(new Image("file:data/icons/recycle-bin.png"));
        bImageView.setFitWidth(20);
        bImageView.setFitHeight(20);
        btnClear.setGraphic(bImageView);
        btnClear.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1);");

        StackPane spInfo = new StackPane();
        spInfo.getChildren().addAll(tInfo, btnClear);
        spInfo.setAlignment(Pos.CENTER);
        StackPane.setMargin(btnClear, new Insets(0, 0, 100, 340));
        // clear textarea
        btnClear.setOnAction(e -> tInfo.clear());
        
        sideMenu.getChildren().addAll(cityGridPane, spInfo, dividerLineA, hTNodes, hBtnNodes, 
                                      hBtnInfo, dividerLineB, hbEdgeAreas, hbEdgeBtns, 
                                      dividerLineC, hbDAreas, btnPrim, btnDijkstra);
        sideMenu.setSpacing(15);
        sideMenu.setAlignment(Pos.CENTER);

        // alignment
        this.setCenter(sideMenu);
        this.setTop(menuBar);
        BorderPane.setMargin(sideMenu, new Insets(10, 0, 30, 0));
        
        opInfo.setOnAction(e -> {
            
            Stage popUpStage = new Stage();
            popUpStage.setScene(new Scene(new InfoPane(popUpStage)));
            popUpStage.setTitle("Asphalt Alert: User Guide");
            popUpStage.getIcons().add(new Image("file:data/icons/pothole.png"));
            popUpStage.show();
        });
        
        this.setRight(j);
        
        // choose city
        cityChoice.setOnAction(e -> {
            if(cityChoice.getValue().equals("Johannesburg"))
            {
                this.setRight(j);
            }
            else if(cityChoice.getValue().equals("Cape Town"))
            {
                this.setRight(c);
            }
            else if(cityChoice.getValue().equals("Durban"))
            {
                this.setRight(d);
            }
            
            tInfo.clear();
        });

        opRestart.setOnAction(e -> {
            j = new GraphCanvas(new String("J"));
            c = new GraphCanvas(new String("C"));
            d = new GraphCanvas(new String("D"));
            this.setRight(j);
        });

        // adds nodes on click
        btnAddNode.setOnAction(e -> {
            if(!(tNodeD1.getText().isBlank() || tNodeD1.getText().isBlank()))
            {
                if(MyAlert.preChecks(tNodeD1.getText()) &&
                   MyAlert.preChecks(tNodeD2.getText()))
                {
                    double depth = Double.parseDouble(tNodeD1.getText());
                    double diameter = Double.parseDouble(tNodeD2.getText());

                    if(cityChoice.getValue().equals("Johannesburg"))
                    {
                        j.addNode(diameter, depth);
                    }
                    else if(cityChoice.getValue().equals("Cape Town"))
                    {
                        c.addNode(diameter, depth);
                    }
                    else if(cityChoice.getValue().equals("Durban"))
                    {
                        d.addNode(diameter, depth);
                    }
                }

                tNodeD1.clear();
                tNodeD2.clear();
            }
            else
            {
                MyAlert.noTextFound();
            }
        });

        // removes nodes on click
        btnRemoveNode.setOnAction(e -> {
            if(!tNodeID.getText().isBlank())
            {   
                if(cityChoice.getValue().equals("Johannesburg"))
                {
                    if(MyAlert.preChecks(tNodeID.getText()))
                        j.removeNode(tNodeID.getText());
                }
                else if(cityChoice.getValue().equals("Cape Town"))
                {
                    if(MyAlert.preChecks(tNodeID.getText()))
                        c.removeNode(tNodeID.getText());
                }
                else if(cityChoice.getValue().equals("Durban"))
                {
                    if(MyAlert.preChecks(tNodeID.getText()))
                        d.removeNode(tNodeID.getText());
                }

                tNodeID.clear();
            }
            else
            {
                MyAlert.noTextFound();
            }

        });

        btnGetInfo.setOnAction(e -> {
            if(!tNodeID.getText().isBlank())
            {   
                if(cityChoice.getValue().equals("Johannesburg"))
                {
                    if(MyAlert.preChecks(tNodeID.getText()))
                    {
                        String infoString = j.getNodeInfo(tNodeID.getText());
                        tInfo.setText(infoString);
                    }
                }
                else if(cityChoice.getValue().equals("Cape Town"))
                {
                    if(MyAlert.preChecks(tNodeID.getText()))
                    {
                        String infoString = c.getNodeInfo(tNodeID.getText());
                        tInfo.setText(infoString);
                    }
                }
                else if(cityChoice.getValue().equals("Durban"))
                {
                    if(MyAlert.preChecks(tNodeID.getText()))
                    {
                        String infoString = d.getNodeInfo(tNodeID.getText());
                        tInfo.setText(infoString);
                    }
                }

                tNodeID.clear();
            }
            else
            {
                MyAlert.noTextFound();
            }

        });

        btnSetInfo.setOnAction(e -> {
            if(!(tNodeID.getText().isBlank() || tNodeD1.getText().isBlank() || tNodeD2.getText().isBlank()))
            {
                if(MyAlert.preChecks(tNodeID.getText()) &&
                   MyAlert.preChecks(tNodeD1.getText()) &&
                   MyAlert.preChecks(tNodeD2.getText()))
                {
                    double depth = Double.parseDouble(tNodeD1.getText());
                    double diameter = Double.parseDouble(tNodeD2.getText());

                    if(cityChoice.getValue().equals("Johannesburg"))
                    {
                        String infoString = j.setNodeInfo(tNodeID.getText(), diameter, depth);
                        tInfo.setText(infoString);
                    }
                    else if(cityChoice.getValue().equals("Cape Town"))
                    {
                        String infoString = c.setNodeInfo(tNodeID.getText(), diameter, depth);
                        tInfo.setText(infoString);
                    }
                    else if(cityChoice.getValue().equals("Durban"))
                    {
                        String infoString = d.setNodeInfo(tNodeID.getText(), diameter, depth);
                        tInfo.setText(infoString);
                    }
                }

                tNodeID.clear();
                tNodeD1.clear();
                tNodeD2.clear();
            }
            else
            {
                MyAlert.noTextFound();
            }

        });

        // adds edges on click
        btnAddEdge.setOnAction(e -> {
            if(!(tEdgeOne.getText().isBlank() || tEdgeTwo.getText().isBlank() || tEdgeWeight.getText().isBlank()))
            {
                if(MyAlert.preChecks(tEdgeOne.getText()) && 
                   MyAlert.preChecks(tEdgeTwo.getText()) &&
                   MyAlert.preChecks(tEdgeWeight.getText()))
                {
                    if(cityChoice.getValue().equals("Johannesburg"))
                    {
                        j.addEdge(tEdgeOne.getText(), tEdgeTwo.getText(), tEdgeWeight.getText());
                    }
                    else if(cityChoice.getValue().equals("Cape Town"))
                    {
                        c.addEdge(tEdgeOne.getText(), tEdgeTwo.getText(), tEdgeWeight.getText());
                    }
                    else if(cityChoice.getValue().equals("Durban"))
                    {
                        d.addEdge(tEdgeOne.getText(), tEdgeTwo.getText(), tEdgeWeight.getText());
                    }
                }

                tEdgeOne.clear();
                tEdgeTwo.clear();
                tEdgeWeight.clear();
            }
            else
            {
                MyAlert.noTextFound();
            }
        });

        // removes edges on click
        btnRemoveEdge.setOnAction(e -> {
            if(!(tEdgeOne.getText().isBlank() || tEdgeTwo.getText().isBlank()))
            {
                if(MyAlert.preChecks(tEdgeOne.getText()) && 
                   MyAlert.preChecks(tEdgeTwo.getText()))
                {
                    if(cityChoice.getValue().equals("Johannesburg"))
                    {
                        j.removeEdge(tEdgeOne.getText(), tEdgeTwo.getText());
                    }
                    else if(cityChoice.getValue().equals("Cape Town"))
                    {
                        c.removeEdge(tEdgeOne.getText(), tEdgeTwo.getText());
                    }
                    else if(cityChoice.getValue().equals("Durban"))
                    {
                        d.removeEdge(tEdgeOne.getText(), tEdgeTwo.getText());
                    }
                }

                tEdgeOne.clear();
                tEdgeTwo.clear();
                tEdgeWeight.clear();
            }
            else
            {
                MyAlert.noTextFound();
            }
        });

        // Prim's algorithm
        btnPrim.setOnAction(e -> {
            if(cityChoice.getValue().equals("Johannesburg"))
            {
                int span = j.primAlgo();
                if(span == -1) tInfo.setText("Graph is disconnected!");
                else tInfo.setText("Minimum Span: " + span);
            }
            else if(cityChoice.getValue().equals("Cape Town"))
            {
                int span = c.primAlgo();
                if(span == -1) tInfo.setText("Graph is disconnected!");
                else tInfo.setText("Minimum Span: " + span);
            }
            else if(cityChoice.getValue().equals("Durban"))
            {
                int span = d.primAlgo();
                if(span == -1) tInfo.setText("Graph is disconnected!");
                else tInfo.setText("Minimum Span: " + span);
            }
        });

        // Dijkstra's algorithm
        btnDijkstra.setOnAction(e -> {
            if(!(tDStart.getText().isBlank() || tDEnd.getText().isBlank()))
            {
                if(MyAlert.preChecks(tDStart.getText()) && 
                   MyAlert.preChecks(tDEnd.getText()))
                {
                    if(cityChoice.getValue().equals("Johannesburg"))
                    {
                        if(!tDMid.getText().isBlank())
                        {
                            int distance = j.dijkstraAlgoMid(tDStart.getText(), tDEnd.getText(), tDMid.getText());
                            if(distance == -1) {} // do nothing
                            else if(distance > 0)
                            {
                                tInfo.setText(distanceOutputMid("J", tDStart.getText(), tDEnd.getText(), tDMid.getText(), 
                                                                distance));
                            }
                            else tInfo.setText("No path found!");

                            tDMid.clear();
                        }
                        else
                        {
                            int distance = j.dijkstraAlgo(tDStart.getText(), tDEnd.getText());
                            if(distance == -1) {} // do nothing
                            else if(distance > 0)
                                tInfo.setText(distanceOutput("J", tDStart.getText(), tDEnd.getText(), distance));
                            else tInfo.setText("No path found!");
                        }

                    }
                    else if(cityChoice.getValue().equals("Cape Town"))
                    {
                        if(!tDMid.getText().isBlank())
                        {
                            int distance = j.dijkstraAlgoMid(tDStart.getText(), tDEnd.getText(), tDMid.getText());
                            if(distance == -1) {} // do nothing
                            else if(distance > 0)
                            {
                                tInfo.setText(distanceOutputMid("C", tDStart.getText(), tDEnd.getText(), tDMid.getText(), 
                                                                distance));
                            }
                            else tInfo.setText("No path found!");

                            tDMid.clear();
                        }
                        else
                        {
                            int distance = c.dijkstraAlgo(tDStart.getText(), tDEnd.getText());
                            if(distance == -1) {} // do nothing
                            else if(distance > 0)
                                tInfo.setText(distanceOutput("C", tDStart.getText(), tDEnd.getText(), distance));
                            else tInfo.setText("No path found!");
                        }

                    }
                    else if(cityChoice.getValue().equals("Durban"))
                    {
                        if(!tDMid.getText().isBlank())
                        {
                            int distance = j.dijkstraAlgoMid(tDStart.getText(), tDEnd.getText(), tDMid.getText());
                            if(distance == -1) {} // do nothing
                            else if(distance > 0)
                            {
                                tInfo.setText(distanceOutputMid("D", tDStart.getText(), tDEnd.getText(), tDMid.getText(), 
                                                                distance));
                            }
                            else tInfo.setText("No path found!");

                            tDMid.clear();
                        }
                        else
                        {
                            int distance = d.dijkstraAlgo(tDStart.getText(), tDEnd.getText());
                            if(distance == -1) {} // do nothing
                            else if(distance > 0)
                                tInfo.setText(distanceOutput("D", tDStart.getText(), tDEnd.getText(), distance));
                            else tInfo.setText("No path found!");
                        }
                    }
                }

                tDStart.clear();
                tDEnd.clear();
            }
            else
            {
                MyAlert.noTextFound();
            }
        });

        // background color - (240,248,255)
        this.setBackground(new Background(new BackgroundFill(Color.rgb(240,248,255), null, null)));
	}

    private String distanceOutput(String c, String s, String e, int distance)
    {
        return "Total Distance --> P" + c + "-" + s + " and P" + c + "-" + e + ": " + distance + "m";
    }

    private String distanceOutputMid(String c, String s, String e, String m, int distance)
    {
        return "Total Distance --> P" + c + "-" + s + " and P" + c + "-" + e + " via P" + c + "-" + m + ": " + distance + "m";
    }
}

