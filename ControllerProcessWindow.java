package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maksym on 12/13/2016.
 */
public class ControllerProcessWindow implements Initializable{

    /////////////////////////TableView /////////////////////////////////

    public TableView<CProcessInfo> tv;
    public TableColumn<CProcessInfo, Long> columnID;
    public TableColumn<CProcessInfo, String> columnCpu;
    public TableColumn<CProcessInfo, String> columnMem;

    ////////////////////////// CPU /////////////////////////////////////

    public NumberAxis singleProcessxAxisCPU;
    public NumberAxis singleProcessyAxisCPU;
    public NumberAxis singleProcessxAxisDerCPU;
    public NumberAxis singleProcessyAxisDerCPU;
    public NumberAxis singleProcessxAxisDer2CPU;
    public NumberAxis singleProcessyAxisDer2CPU;

    public LineChart<Number, Number> singleProcessChartCPU;
    public LineChart<Number, Number> singleProcessChartDerCPU;
    public LineChart<Number, Number> singleProcessChartDer2CPU;

    public XYChart.Series seriesCPU = new XYChart.Series();
    public XYChart.Series seriesDerCPU = new XYChart.Series();
    public XYChart.Series seriesDer2CPU = new XYChart.Series();

    public Label labelCpuSum;
    public Label labelCPU;
    public Label labelDerCpuSum;
    public Label labelDer2CpuSum;

    /////////////////////////// Memory /////////////////////////////////

    public NumberAxis singleProcessxAxisMem;
    public NumberAxis singleProcessyAxisMem;
    public NumberAxis singleProcessxAxisDerMem;
    public NumberAxis singleProcessyAxisDerMem;
    public NumberAxis singleProcessxAxisDer2Mem;
    public NumberAxis singleProcessyAxisDer2Mem;

    public LineChart<Number, Number> singleProcessChartMem;
    public LineChart<Number, Number> singleProcessChartDerMem;
    public LineChart<Number, Number> singleProcessChartDer2Mem;

    public XYChart.Series seriesMem = new XYChart.Series();
    public XYChart.Series seriesDerMem = new XYChart.Series();
    public XYChart.Series seriesDer2Mem = new XYChart.Series();

    public Label labelMemSum;
    public Label labelMem;
    public Label labelDerMemSum;
    public Label labelDer2MemSum;

    /////////////////////////////////////////////////////////////////////
    static String title = "";
    static public void setTitle(String t) {
        title = t;
    }
    SingleProcessStat sps;
    ObservableList<CProcessInfo> data;
    Timeline timeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sps = new SingleProcessStat(title, 60);
        data = FXCollections.observableArrayList(sps.getProcesses());
        tv.setItems(data);

        singleProcessChartCPU.getData().add(seriesCPU);
        singleProcessChartDerCPU.getData().add(seriesDerCPU);
        singleProcessChartDer2CPU.getData().add(seriesDer2CPU);

        singleProcessChartMem.getData().add(seriesMem);
        singleProcessChartDerMem.getData().add(seriesDerMem);
        singleProcessChartDer2Mem.getData().add(seriesDer2Mem);

        singleProcessChartCPU.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        singleProcessChartMem.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        singleProcessChartDerCPU.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        singleProcessChartDerMem.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        singleProcessChartDer2CPU.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        singleProcessChartDer2Mem.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());

        columnID.setCellValueFactory(new PropertyValueFactory<>("pid"));
        columnCpu.setCellValueFactory(new PropertyValueFactory<>("cpu"));
        columnMem.setCellValueFactory(new PropertyValueFactory<>("mem"));

        timeline = new Timeline(new KeyFrame(Duration.millis(2000), e -> dataUpdater()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void cpuUpdate() {
        seriesCPU.getData().clear();
        seriesDerCPU.getData().clear();
        seriesDer2CPU.getData().clear();
        for (int i = 0; i < 60; i++) {
            seriesCPU.getData().add(new XYChart.Data(i, sps.getCpuElement(i)));
            seriesDerCPU.getData().add(new XYChart.Data(i, sps.getDerCpuElement(i)));
            seriesDer2CPU.getData().add(new XYChart.Data(i, sps.getDer2CpuElement(i)));
        }

        labelCPU.setText("CPU: " + String.format("%.2f", sps.getCpuLastElement())  + " (%) - " + sps.getMgz() + " MHz");
        labelCpuSum.setText("Work estimation: " + String.format("%.2f", sps.getCpuUtilTotal()));
        labelDerCpuSum.setText("Speed estimation: " + String.format("%.2f", sps.getCpuDerUtilTotal()));
        labelDer2CpuSum.setText("Intensive estimation: " + String.format("%.2f", sps.getCpuDer2UtilTotal()));
    }

    private void memUpdate() {
        seriesMem.getData().clear();
        seriesDerMem.getData().clear();
        seriesDer2Mem.getData().clear();
        for (int i = 0; i < 60; i++) {
            seriesMem.getData().add(new XYChart.Data(i, sps.getMemElement(i)));
            seriesDerMem.getData().add(new XYChart.Data(i, sps.getDerMemElement(i)));
            seriesDer2Mem.getData().add(new XYChart.Data(i, sps.getDer2MemElement(i)));
        }

        labelMem.setText("Memory: " + String.format("%.5f", sps.getMemLastElement())  + " (%) - " + String.format("%,d", sps.getMgb())  + " MB");
        labelMemSum.setText("Work estimation: " + String.format("%.2f", sps.getMemUtilTotal()));
        labelDerMemSum.setText("Speed estimation: " + String.format("%.2f", sps.getMemDerUtilTotal()));
        labelDer2MemSum.setText("Speed estimation: " + String.format("%.2f", sps.getMemDer2UtilTotal()));
    }

    private void singleProcessUpdate() {
        data = FXCollections.observableArrayList(sps.getProcesses());
        tv.setItems(data);
    }

    private void dataUpdater() {
        try {
            sps.Update();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error while updating SingleProcessStat info");
            alert.showAndWait();
        }
        cpuUpdate();
        memUpdate();
        singleProcessUpdate();
        if (sps.getProcessCount() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Process");
            alert.setContentText("Process " + sps.getTitle() + " was closed.");
            alert.show();
            timeline.stop();
        }
    }
}
