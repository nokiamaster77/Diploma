package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Maksym on 10/1/2016.
 */
public class BackgroundRect {

    Rectangle redRect;
    Rectangle yellowRect;
    Rectangle greenRect;
    int redX, redY, redWidth, redHeight,
        yellowX, yellowY, yellowWidth, yellowHeight,
        greenX, greenY, greenWidth, greenHeight;

    public BackgroundRect(int rX, int rY, int rWidth, int rHeight, int yHeight, int gHeight) {
        redX = rX;
        yellowX = rX;
        greenX = rX;
        redY = rY;
        redWidth = rWidth;
        yellowWidth = rWidth;
        greenWidth = rWidth;
        redHeight = rHeight;
        yellowY = redY + rHeight;
        yellowHeight = yHeight;
        greenY = yellowY + yHeight;
        greenHeight = gHeight;

        redRect = new Rectangle(redX, redY, redWidth, redHeight);
        redRect.setFill(Color.rgb(255,0,0, 0.05));

        yellowRect = new Rectangle(yellowX, yellowY, yellowWidth, yellowHeight);
        yellowRect.setFill(Color.rgb(255,255,0,0.05));

        greenRect = new Rectangle(greenX, greenY, greenWidth, greenHeight);
        greenRect.setFill(Color.rgb(34,139,34,0.05));

    }

    public Rectangle getRedRectangle() {
        return redRect;
    }

    public Rectangle getYellowRectangle() {
        return yellowRect;
    }

    public Rectangle getGreenRectangle() {
        return greenRect;
    }

    public void updateBounds(int upperBound, int lowerBound, LineChart<Number, Number> chart) {
        redX = ((int)chart.getXAxis().getLayoutX() + (int)chart.getPadding().getLeft());
        redRect.setX(redX);

        yellowX = redX;
        yellowRect.setX(yellowX);

        greenX = redX;
        greenRect.setX(greenX);

        redY = ((int)chart.getYAxis().getLayoutY() + (int)chart.getPadding().getTop());
        redRect.setY(redY);

        redWidth = ((int)chart.getXAxis().getWidth());
        redRect.setWidth(redWidth);

        yellowWidth = (redWidth);
        yellowRect.setWidth(yellowWidth);

        greenWidth = (redWidth);
        greenRect.setWidth(greenWidth);

        redHeight = ((int)((chart.getYAxis().getZeroPosition() * ((100.0-upperBound)/100.0))));
        redRect.setHeight(redHeight);

        yellowY = (redY + redHeight);
        yellowRect.setY(yellowY);

        yellowHeight = ((int)((chart.getYAxis().getZeroPosition() * ((upperBound-lowerBound)/100.0))));
        yellowRect.setHeight(yellowHeight);

        greenY = (yellowY + yellowHeight);
        greenRect.setY(greenY);

        greenHeight = ((int)((chart.getYAxis().getZeroPosition() * (lowerBound/100.0))));
        greenRect.setHeight(greenHeight);
    }
}
