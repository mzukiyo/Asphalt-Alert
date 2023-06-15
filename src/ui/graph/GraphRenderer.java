package ui.graph;

import javafx.scene.layout.Region;
import org.graphstream.ui.fx_viewer.FxDefaultView;

public class GraphRenderer extends Region 
{
    private FxDefaultView view;

    public GraphRenderer(FxDefaultView view) 
    {
        this.view = view;
        getChildren().add(view);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        view.getCamera().setViewPercent(10.0);
        view.getCamera().setAutoFitView(true);
        view.getCamera().setGraphViewport(getLayoutX(), getLayoutY(), getWidth(), getHeight());
        view.getCamera().resetView();
    }
}