package sample;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class Controller implements Initializable {

    //////////// CPU /////////////////////////
    public NumberAxis xAxisCPU;
    public NumberAxis yAxisCPU;
    public NumberAxis xAxisCPUDer;
    public NumberAxis yAxisCPUDer;
    public NumberAxis xAxisCPUPrim;
    public NumberAxis yAxisCPUPrim;
    public LineChart<Number, Number> chartCPU;
    public LineChart<Number, Number> chartCPUDer;
    public LineChart<Number, Number> chartCPUPrim;
    public XYChart.Series seriesCPU = new XYChart.Series();
    public XYChart.Series seriesCPUDer = new XYChart.Series();
    public XYChart.Series seriesCPUPrim = new XYChart.Series();

    public BackgroundRect chartRectCPU, chartRectMem;
    public AnchorPane panelCPU;
    public Label labelCPU;
    int startXCPU = 48, startYCPU = 15;
    int uBoundCPU = 90, lBoundCPU = 30;
    ImageView imgViewCPU, tabImgViewCPU;
    public Button okCPU;
    public TextField txFieldUBoundCPU;
    public TextField txFieldLBoundCPU;
    public Tab tabCPU;

///////////////////////////// Memory  //////////////////////////////////////////////////

    public NumberAxis xAxisMem;
    public NumberAxis yAxisMem;
    public LineChart<Number, Number> chartMem;
    public XYChart.Series seriesMem = new XYChart.Series();
    public AnchorPane panelMem;
    public Label labelMem;
    int startXMem = 48, startYMem = 15;
    int uBoundMem = 90, lBoundMem = 30;
    ImageView imgViewMem, tabImgViewMem;
    public Button okMemory;
    public TextField txFieldUBoundMem;
    public TextField txFieldLBoundMem;
    public Tab tabMem;

 ///////////////////////////// Disk /////////////////////

    public NumberAxis xAxisDisk;
    public NumberAxis yAxisDisk;
    public LineChart<Number, Number> chartDisk;
    public XYChart.Series seriesDiskWrite = new XYChart.Series();
    public XYChart.Series seriesDiskRead = new XYChart.Series();
    public Tab tabDisk;
    public Label labelDiskRead;
    public Label labelDiskWrite;

    //////////////////////////// Net ///////////////////////

    public NumberAxis xAxisNet;
    public NumberAxis yAxisNet;
    public LineChart<Number, Number> chartNet;
    public XYChart.Series seriesNetRec = new XYChart.Series();
    public XYChart.Series seriesNetSend = new XYChart.Series();
    public Tab tabNet;
    public Label labelNetRec;
    public Label labelNetSend;

    //////////////////////////////////////////////////////////////

    static CPUUsage cpuUsage = new CPUUsage();
    static MemoryUsage memUsage = new MemoryUsage();
    static DiskUsage diskUsage = new DiskUsage();
    static NetUsage netUsage = new NetUsage();
    static Arrow arrowImg = new Arrow();

    @Override
     public void initialize(URL location, ResourceBundle resources) {

        imgViewCPU = new ImageView(arrowImg.getImageCPU(15, 2, 90, 30));
        imgViewCPU.setLayoutX(chartCPU.getPrefWidth() + 10);
        imgViewCPU.setFitWidth(64);
        imgViewCPU.setFitHeight(64);
        panelCPU.getChildren().add(imgViewCPU);

        tabImgViewCPU = new ImageView(arrowImg.getImageCPU(15, 2, 90, 30));
        tabImgViewCPU.setFitWidth(16);
        tabImgViewCPU.setFitHeight(16);
        tabCPU.setGraphic(tabImgViewCPU);

        imgViewMem = new ImageView(arrowImg.getImageMem(15, 2, 90, 30));
        imgViewMem.setLayoutX(chartMem.getPrefWidth() + 10);
        imgViewMem.setFitWidth(64);
        imgViewMem.setFitHeight(64);
        panelMem.getChildren().add(imgViewMem);

        tabImgViewMem = new ImageView(arrowImg.getImageMem(15, 2, 90, 30));
        tabImgViewMem.setFitWidth(16);
        tabImgViewMem.setFitHeight(16);
        tabMem.setGraphic(tabImgViewMem);

        chartRectCPU = new BackgroundRect(startXCPU, startYCPU, 367, (int)(2.25 * (100- uBoundCPU)),(int)(2.25 * (uBoundCPU - lBoundCPU)), (int)(2.25 * lBoundCPU));
        panelCPU.getChildren().addAll(chartRectCPU.getRedRectangle(), chartRectCPU.getYellowRectangle(), chartRectCPU.getGreenRectangle());

        chartRectMem = new BackgroundRect(startXMem, startYMem, 367, (int)(2.25 * (100- uBoundCPU)),(int)(2.25 * (uBoundCPU - lBoundCPU)), (int)(2.25 * lBoundCPU));
        panelMem.getChildren().addAll(chartRectMem.getRedRectangle(), chartRectMem.getYellowRectangle(), chartRectMem.getGreenRectangle());

        chartMem.setAnimated(false);
        chartMem.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());

        chartCPU.setAnimated(false);
        chartCPU.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        chartCPUDer.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        chartCPUPrim.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());

        chartDisk.setAnimated(false);
        chartDisk.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());

        chartNet.setAnimated(false);
        chartNet.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> dataUpdater()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
     }

    private void cpuUpdate() {
        if (!chartCPU.getData().isEmpty())
            chartCPU.getData().clear();
        if (!seriesCPU.getData().isEmpty())
            seriesCPU.getData().clear();
        for (int i = 0; i < 60; i++)
            seriesCPU.getData().add(new XYChart.Data(i, cpuUsage.getElement(i)));

        chartCPU.getData().add(seriesCPU);

        if (!chartCPUDer.getData().isEmpty())
            chartCPUDer.getData().clear();
        if (!seriesCPUDer.getData().isEmpty())
            seriesCPUDer.getData().clear();
        for (int i = 0; i < 60; i++)
            seriesCPUDer.getData().add(new XYChart.Data(i, cpuUsage.getDerElement(i)));

        chartCPUDer.getData().add(seriesCPUDer);

        if (!chartCPUPrim.getData().isEmpty())
            chartCPUPrim.getData().clear();
        if (!seriesCPUPrim.getData().isEmpty())
            seriesCPUPrim.getData().clear();
        for (int i = 0; i < 60; i++)
            seriesCPUPrim.getData().add(new XYChart.Data(i, cpuUsage.getSumElement(i)));

        chartCPUPrim.getData().add(seriesCPUPrim);

        imgViewCPU.setImage(arrowImg.getImageCPU(cpuUsage.getLastElement(), cpuUsage.getAverage(), uBoundCPU, lBoundCPU));
        tabImgViewCPU.setImage(imgViewCPU.getImage());

        labelCPU.setText("CPU: " + cpuUsage.getLastElement() + "%");

        try {
            cpuUsage.Update();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error while getting CPU info");
            alert.showAndWait();
        }
    }

    private void memUpdate() {
        if (!chartMem.getData().isEmpty())
            chartMem.getData().clear();
        if (!seriesMem.getData().isEmpty())
            seriesMem.getData().clear();
        for (int i = 0; i < 60; i++)
            seriesMem.getData().add(new XYChart.Data(i, memUsage.getElement(i)));

        chartMem.getData().add(seriesMem);

        imgViewMem.setImage(arrowImg.getImageMem(memUsage.getLastElement(), memUsage.getAverage(), uBoundMem, lBoundMem));
        tabImgViewMem.setImage(imgViewMem.getImage());

        labelMem.setText("Memory: " + memUsage.getLastElement() + "%");

        try {
            memUsage.Update();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error while getting Memory info");
            alert.showAndWait();
        }
    }

    private void diskUpdate() {
        if (!chartDisk.getData().isEmpty())
            chartDisk.getData().clear();
        if (!seriesDiskWrite.getData().isEmpty())
            seriesDiskWrite.getData().clear();
        if (!seriesDiskRead.getData().isEmpty())
            seriesDiskRead.getData().clear();
        for (int i = 0; i < 60; i++) {
            seriesDiskWrite.getData().add(new XYChart.Data(i, diskUsage.getWriteElement(i)));
            seriesDiskRead.getData().add(new XYChart.Data(i, diskUsage.getReadElement(i)));
        }

        chartDisk.getData().add(seriesDiskWrite);
        chartDisk.getData().add(seriesDiskRead);

        labelDiskRead.setText("Read: " + (int)diskUsage.getReadLastElement() + " kB/s");
        labelDiskWrite.setText("Write: " + (int)diskUsage.getWriteLastElement() + " kB/s");

        try {
            diskUsage.Update();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error while getting Disk i/o info");
            alert.showAndWait();
        }
    }

    private void netUpdate() {
        if (!chartNet.getData().isEmpty())
            chartNet.getData().clear();
        if (!seriesNetRec.getData().isEmpty())
            seriesNetRec.getData().clear();
        if (!seriesNetSend.getData().isEmpty())
            seriesNetSend.getData().clear();
        for (int i = 0; i < 60; i++) {
            seriesNetRec.getData().add(new XYChart.Data(i, netUsage.getReceiveElement(i)));
            seriesNetSend.getData().add(new XYChart.Data(i, netUsage.getSendElement(i)));
        }

        chartNet.getData().add(seriesNetRec);
        chartNet.getData().add(seriesNetSend);

        labelNetRec.setText("Receive: " + (int)netUsage.getLastReceiveElement() + " B/s");
        labelNetSend.setText("Send: " + (int)netUsage.getLastSendElement() + " B/s");
        try {
            netUsage.Update();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error while getting Net send/receiving info");
            alert.showAndWait();
        }
    }

    private void dataUpdater() {
        cpuUpdate();
        memUpdate();
        diskUpdate();
        netUpdate();
    }

    public void updateBoundsCPU() {
        try {
            uBoundCPU = Integer.parseInt(txFieldUBoundCPU.getText(), 10);
            lBoundCPU = Integer.parseInt(txFieldLBoundCPU.getText(), 10);
        } catch(NumberFormatException e) {
            uBoundCPU = 90;
            lBoundCPU = 30;
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error " + e.getMessage());
            alert.setContentText("Enter only integer value");
            alert.show();
        }
        if (uBoundCPU > 95) uBoundCPU = 95;
        if (uBoundCPU < 50) uBoundCPU = 50;
        if (lBoundCPU < 5) lBoundCPU = 5;
        if (lBoundCPU > 50) lBoundCPU = 50;
        txFieldUBoundCPU.setText(Integer.toString(uBoundCPU));
        txFieldLBoundCPU.setText(Integer.toString(lBoundCPU));
        chartRectCPU.updateBounds(uBoundCPU, lBoundCPU, chartCPU);
    }

    public void updateBoundsMemory() {
        try {
            uBoundMem = Integer.parseInt(txFieldUBoundMem.getText(), 10);
            lBoundMem = Integer.parseInt(txFieldLBoundMem.getText(), 10);
        } catch (NumberFormatException e) {
            uBoundMem = 90;
            lBoundMem = 30;
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error " + e.getMessage());
            alert.setContentText("Enter only integer value");
            alert.show();
        }
        if (uBoundMem > 95) uBoundMem = 95;
        if (uBoundMem < 50) uBoundMem = 50;
        if (lBoundMem < 5) lBoundMem = 5;
        if (lBoundMem > 50) lBoundMem = 50;
        txFieldUBoundMem.setText(Integer.toString(uBoundMem));
        txFieldLBoundMem.setText(Integer.toString(lBoundMem));
        chartRectMem.updateBounds(uBoundMem, lBoundMem, chartMem);
    }

    public void okBtnOnClick() { updateBoundsCPU(); }

    public void memOkBtnOnClick() {updateBoundsMemory();}

}
