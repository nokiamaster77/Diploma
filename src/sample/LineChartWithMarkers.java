package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * Created by Maksym on 10/4/2016.
 */
public class LineChartWithMarkers<X,Y> extends LineChart {

    private ObservableList<Data<X, X>> horizontalRangeMarkers;

    public LineChartWithMarkers(Axis<X> xAxis, Axis<Y> yAxis) {
        super(xAxis, yAxis);
        horizontalRangeMarkers = FXCollections.observableArrayList(data -> new Observable[] {data.XValueProperty()});
        horizontalRangeMarkers = FXCollections.observableArrayList(data -> new Observable[] {data.YValueProperty()}); // 2nd type of the range is X type as well
        horizontalRangeMarkers.addListener((InvalidationListener)observable -> layoutPlotChildren());
    }

    public void addNewBounds(int uBounds, int lBounds) {

    }

    public void addHorizontalRangeMarker(Data<X, X> marker) {
        Objects.requireNonNull(marker, "the marker must not be null");
        if (horizontalRangeMarkers.contains(marker)) return;

        Rectangle rectangle = new Rectangle(0,0,0,0);
        rectangle.setStroke(Color.TRANSPARENT);
        rectangle.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.2));

        marker.setNode( rectangle);

        getPlotChildren().add(rectangle);
        horizontalRangeMarkers.add(marker);
    }

    public void removeVerticalRangeMarker(Data<X, X> marker) {
        Objects.requireNonNull(marker, "the marker must not be null");
        if (marker.getNode() != null) {
            getPlotChildren().remove(marker.getNode());
            marker.setNode(null);
        }
        horizontalRangeMarkers.remove(marker);
    }

    @Override
    protected void layoutPlotChildren() {
        super.layoutPlotChildren();

        for (Data<X, X> horizontalRangeMarker : horizontalRangeMarkers) {

            Rectangle rectangle = (Rectangle) horizontalRangeMarker.getNode();
            rectangle.setX( getXAxis().getDisplayPosition(horizontalRangeMarker.getXValue()) + 0.5);  // 0.5 for crispness
            rectangle.setWidth( getXAxis().getDisplayPosition(horizontalRangeMarker.getYValue()) - getXAxis().getDisplayPosition(horizontalRangeMarker.getXValue()));
            rectangle.setY(0d);
            rectangle.setHeight(getBoundsInLocal().getHeight());
            rectangle.toBack();

        }
    }

}
