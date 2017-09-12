package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class Controller implements Initializable {

    //////////// CPU /////////////////////////
    public NumberAxis xAxisWorkCPU;
    public NumberAxis yAxisWorkCPU;
    public NumberAxis xAxisSpeedCPU;
    public NumberAxis yAxisSpeedCPU;
    public NumberAxis xAxisIntCPU;
    public NumberAxis yAxisIntCPU;
    public LineChart<Number, Number> chartWorkCPU;
    public LineChart<Number, Number> chartSpeedCPU;
    public LineChart<Number, Number> chartIntCPU;
    public XYChart.Series seriesWorkCPU = new XYChart.Series();
    public XYChart.Series seriesSpeedCPU = new XYChart.Series();
    public XYChart.Series seriesIntCPU = new XYChart.Series();

    public BackgroundRect chartRectCPU, chartRectMem, chartRectDisk, chartRectNet;
    public AnchorPane panelCPU;
    public Label labelCPU;
    public Label labelWorkCpu;
    public Label labelSpeedCpu;
    public Label labelIntCpu;
    int startXCPU = 48, startYCPU = 15;
    int uBoundCPU = 90, lBoundCPU = 30;
    ImageView imgViewCPU, statBarImgViewCPU;
    public Button okCPU;
    public TextField txFieldUBoundCPU;
    public TextField txFieldLBoundCPU;
    public VBox vboxImgCpu;

///////////////////////////// Memory  //////////////////////////////////////////////////

    public NumberAxis xAxisWorkMem;
    public NumberAxis yAxisWorkMem;
    public NumberAxis xAxisSpeedMem;
    public NumberAxis yAxisSpeedMem;
    public NumberAxis xAxisIntMem;
    public NumberAxis yAxisIntMem;
    public LineChart<Number, Number> chartWorkMem;
    public LineChart<Number, Number> chartSpeedMem;
    public LineChart<Number, Number> chartIntMem;
    public XYChart.Series seriesWorkMem = new XYChart.Series();
    public XYChart.Series seriesSpeedMem = new XYChart.Series();
    public XYChart.Series seriesIntMem = new XYChart.Series();
    public AnchorPane panelMem;
    public Label labelMem;
    public Label labelWorkMem;
    public Label labelSpeedMem;
    public Label labelIntMem;
    int startXMem = 48, startYMem = 15;
    int uBoundMem = 90, lBoundMem = 30;
    ImageView imgViewMem, statBarImgViewMem;
    public Button okMem;
    public TextField txFieldUBoundMem;
    public TextField txFieldLBoundMem;
    public VBox vboxImgMem;

 ///////////////////////////// Disk /////////////////////

    public NumberAxis xAxisWorkDisk;
    public NumberAxis yAxisWorkDisk;
    public NumberAxis xAxisSpeedDisk;
    public NumberAxis yAxisSpeedDisk;
    public NumberAxis xAxisIntDisk;
    public NumberAxis yAxisIntDisk;
    public LineChart<Number, Number> chartWorkDisk;
    public LineChart<Number, Number> chartSpeedDisk;
    public LineChart<Number, Number> chartIntDisk;
    public XYChart.Series seriesWorkDiskWrite = new XYChart.Series();
    public XYChart.Series seriesWorkDiskRead = new XYChart.Series();
    public XYChart.Series seriesSpeedDiskWrite = new XYChart.Series();
    public XYChart.Series seriesSpeedDiskRead = new XYChart.Series();
    public XYChart.Series seriesIntDiskWrite = new XYChart.Series();
    public XYChart.Series seriesIntDiskRead = new XYChart.Series();
    public Label labelDiskRead;
    public Label labelDiskWrite;

    public AnchorPane panelDisk;
    public Label labelDisk;
    public Label labelWorkDisk;
    public Label labelSpeedDisk;
    public Label labelIntDisk;
    int startXDisk = 48, startYDisk = 15;
    int uBoundDisk = 90, lBoundDisk = 30;
    ImageView imgViewDiskRead, imgViewDiskWrite, statBarImgViewDiskRead, statBarImgViewDiskWrite;
    public Button okDisk;
    public TextField txFieldUBoundDisk;
    public TextField txFieldLBoundDisk;
    public VBox vboxImgDisk;
    public HBox hboxDiskRead;
    public HBox hboxDiskWrite;


    //////////////////////////// Net ///////////////////////

    public NumberAxis xAxisWorkNet;
    public NumberAxis yAxisWorkNet;
    public NumberAxis xAxisSpeedNet;
    public NumberAxis yAxisSpeedNet;
    public NumberAxis xAxisIntNet;
    public NumberAxis yAxisIntNet;
    public LineChart<Number, Number> chartWorkNet;
    public LineChart<Number, Number> chartSpeedNet;
    public LineChart<Number, Number> chartIntNet;
    public XYChart.Series seriesWorkNetSend = new XYChart.Series();
    public XYChart.Series seriesWorkNetRec = new XYChart.Series();
    public XYChart.Series seriesSpeedNetSend = new XYChart.Series();
    public XYChart.Series seriesSpeedNetRec = new XYChart.Series();
    public XYChart.Series seriesIntNetSend = new XYChart.Series();
    public XYChart.Series seriesIntNetRec = new XYChart.Series();
    public Label labelNetRec;
    public Label labelNetSend;

    public AnchorPane panelNet;
    public Label labelNet;
    public Label labelWorkNet;
    public Label labelSpeedNet;
    public Label labelIntNet;
    int startXNet = 48, startYNet = 15;
    int uBoundNet = 90, lBoundNet = 30;
    ImageView imgViewNetRec, imgViewNetSend, statBarImgViewNetRec, statBarImgViewNetSend;
    public Button okNet;
    public TextField txFieldUBoundNet;
    public TextField txFieldLBoundNet;
    public VBox vboxImgNet;
    public HBox hboxNetRec;
    public HBox hboxNetSend;

    //////////////////////////////////////////////////////////////

    public TableView<CProcessInfo> tableDetails;
    public TableColumn<CProcessInfo, String> columnProcess;
    public TableColumn<CProcessInfo, Long> columnID;
    public TableColumn<CProcessInfo, String> columnCPU;
    public TableColumn<CProcessInfo, String> columnMem;
    public TableColumn<CProcessInfo, String> columnCMD;

    public GridPane statusBar;
    public Label statusBarCpuLabel;
    public Label statusBarMemLabel;
    public Label statusBarDiskLabel;
    public Label statusBarDiskLabel2;
    public Label statusBarNetLabel;
    public Label statusBarNetLabel2;
    public Label statusBarProcLabel;
    public HBox statBarCpuHBox;
    public HBox statBarMemHBox;
    public HBox statBarDiskHBox;
    public HBox statBarNetHBox;
    public HBox statBarProcHBox;
    public HBox hBoxCpuArrow;
    public HBox hBoxMemArrow;

    public Menu menuHelp;
    public MenuItem aboutMenuItem;


    static CPUUsage cpuUsage = new CPUUsage();
    static MemoryUsage memUsage = new MemoryUsage();
    static DiskUsage diskUsage = new DiskUsage();
    static NetUsage netUsage = new NetUsage();
    static Arrow arrowImg = new Arrow();
    static ProcessStat ps = new ProcessStat();
    ObservableList<CProcessInfo> data;

    public void aboutMenuOnAction(final ActionEvent e) {
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle("About");
        alert.setContentText("Performance monitor\nMade by Maksym Palamarchuk\n2017");
        alert.show();
    }

    @Override
     public void initialize(URL location, ResourceBundle resources) {

        statusBarCpuLabel = new Label("CPU: ");
        statusBarMemLabel = new Label("Memory: ");
        statusBarDiskLabel = new Label("Disk: read: ");
        statusBarDiskLabel2 = new Label("| write: ");
        statusBarNetLabel = new Label("Net: receive: ");
        statusBarNetLabel2 = new Label(" | send: ");
        statusBarProcLabel = new Label("Processes: ");

        statBarImgViewCPU = new ImageView(arrowImg.getImageCPU(15, 2, 90, 30));
        statBarImgViewMem = new ImageView(arrowImg.getImageMem(15, 2, 90, 30));
        statBarImgViewDiskRead = new ImageView(arrowImg.getImageDisk(15, 2, 90, 30));
        statBarImgViewDiskWrite = new ImageView(arrowImg.getImageDisk(15, 2, 90, 30));
        statBarImgViewNetRec = new ImageView(arrowImg.getImageNet(15, 2, 90, 30));
        statBarImgViewNetSend = new ImageView(arrowImg.getImageNet(15, 2, 90, 30));

        statBarImgViewCPU.setFitWidth(16);
        statBarImgViewCPU.setFitHeight(16);
        statBarImgViewMem.setFitWidth(16);
        statBarImgViewMem.setFitHeight(16);
        statBarImgViewDiskRead.setFitWidth(16);
        statBarImgViewDiskRead.setFitHeight(16);
        statBarImgViewDiskWrite.setFitWidth(16);
        statBarImgViewDiskWrite.setFitHeight(16);
        statBarImgViewNetRec.setFitWidth(16);
        statBarImgViewNetRec.setFitHeight(16);
        statBarImgViewNetSend.setFitWidth(16);
        statBarImgViewNetSend.setFitHeight(16);

        statBarCpuHBox = new HBox();
        statBarMemHBox = new HBox();
        statBarDiskHBox = new HBox();
        statBarNetHBox = new HBox();
        statBarProcHBox = new HBox();

        statBarCpuHBox.getChildren().addAll(statusBarCpuLabel, statBarImgViewCPU);
        statBarMemHBox.getChildren().addAll(statusBarMemLabel, statBarImgViewMem);
        statBarDiskHBox.getChildren().addAll(statusBarDiskLabel, statBarImgViewDiskRead, statusBarDiskLabel2, statBarImgViewDiskWrite);
        statBarNetHBox.getChildren().addAll(statusBarNetLabel, statBarImgViewNetRec, statusBarNetLabel2, statBarImgViewNetSend);
        statBarProcHBox.getChildren().addAll(statusBarProcLabel);

        statusBar.add(statBarCpuHBox,0,0);
        statusBar.add(statBarMemHBox,1,0);
        statusBar.add(statBarDiskHBox,2,0);
        statusBar.add(statBarNetHBox,3,0);
        statusBar.add(statBarProcHBox,4,0);

        data = FXCollections.observableArrayList(ps.getProcesses());
        tableDetails.setItems(data);

        chartWorkCPU.getData().add(seriesWorkCPU);
        chartSpeedCPU.getData().add(seriesSpeedCPU);
        chartIntCPU.getData().add(seriesIntCPU);

        chartWorkMem.getData().add(seriesWorkMem);
        chartSpeedMem.getData().add(seriesSpeedMem);
        chartIntMem.getData().add(seriesIntMem);

        chartWorkDisk.getData().add(seriesWorkDiskWrite);
        chartWorkDisk.getData().add(seriesWorkDiskRead);
        chartSpeedDisk.getData().add(seriesSpeedDiskWrite);
        chartSpeedDisk.getData().add(seriesSpeedDiskRead);
        chartIntDisk.getData().add(seriesIntDiskWrite);
        chartIntDisk.getData().add(seriesIntDiskRead);

        chartWorkNet.getData().add(seriesWorkNetSend);
        chartWorkNet.getData().add(seriesWorkNetRec);
        chartSpeedNet.getData().add(seriesSpeedNetSend);
        chartSpeedNet.getData().add(seriesSpeedNetRec);
        chartIntNet.getData().add(seriesIntNetSend);
        chartIntNet.getData().add(seriesIntNetRec);

        imgViewCPU = new ImageView(arrowImg.getImageCPU(15, 2, 90, 30));
        imgViewCPU.setFitWidth(96);
        imgViewCPU.setFitHeight(96);
        imgViewMem = new ImageView(arrowImg.getImageMem(15, 2, 90, 30));
        imgViewMem.setFitWidth(96);
        imgViewMem.setFitHeight(96);

        imgViewDiskRead = new ImageView(arrowImg.getImageDisk(15, 2, 90, 30));
        imgViewDiskRead.setFitWidth(64);
        imgViewDiskRead.setFitHeight(64);
        imgViewDiskWrite = new ImageView(arrowImg.getImageDisk(15, 2, 90, 30));
        imgViewDiskWrite.setFitWidth(64);
        imgViewDiskWrite.setFitHeight(64);

        imgViewNetRec = new ImageView(arrowImg.getImageNet(15, 2, 90, 30));
        imgViewNetRec.setFitWidth(64);
        imgViewNetRec.setFitHeight(64);
        imgViewNetSend = new ImageView(arrowImg.getImageNet(15, 2, 90, 30));
        imgViewNetSend.setFitWidth(64);
        imgViewNetSend.setFitHeight(64);

        vboxImgCpu.getChildren().add(imgViewCPU);
        vboxImgMem.getChildren().add(imgViewMem);
        hboxDiskRead.getChildren().add(imgViewDiskRead);
        hboxDiskWrite.getChildren().add(imgViewDiskWrite);
        hboxNetRec.getChildren().add(imgViewNetRec);
        hboxNetSend.getChildren().add(imgViewNetSend);

        panelCPU.setOnMousePressed(e->{
            updateBoundsCPU();
        });

        chartRectCPU = new BackgroundRect(startXCPU, startYCPU, 367, (int)(2.25 * (100- uBoundCPU)),(int)(2.25 * (uBoundCPU - lBoundCPU)), (int)(2.25 * lBoundCPU));
        panelCPU.getChildren().addAll(chartRectCPU.getRedRectangle(), chartRectCPU.getYellowRectangle(), chartRectCPU.getGreenRectangle());
        panelCPU.setBlendMode(BlendMode.MULTIPLY);

        chartRectMem = new BackgroundRect(startXMem, startYMem, 367, (int)(2.25 * (100- uBoundCPU)),(int)(2.25 * (uBoundCPU - lBoundCPU)), (int)(2.25 * lBoundCPU));
        panelMem.getChildren().addAll(chartRectMem.getRedRectangle(), chartRectMem.getYellowRectangle(), chartRectMem.getGreenRectangle());
        panelMem.setBlendMode(BlendMode.MULTIPLY);

        chartRectDisk = new BackgroundRect(startXDisk, startYDisk, 367, (int)(2.25 * (100- uBoundCPU)),(int)(2.25 * (uBoundCPU - lBoundCPU)), (int)(2.25 * lBoundCPU));
        panelDisk.getChildren().addAll(chartRectDisk.getRedRectangle(), chartRectDisk.getYellowRectangle(), chartRectDisk.getGreenRectangle());
        panelDisk.setBlendMode(BlendMode.MULTIPLY);

        chartRectNet = new BackgroundRect(startXNet, startYNet, 367, (int)(2.25 * (100- uBoundCPU)),(int)(2.25 * (uBoundCPU - lBoundCPU)), (int)(2.25 * lBoundCPU));
        panelNet.getChildren().addAll(chartRectNet.getRedRectangle(), chartRectNet.getYellowRectangle(), chartRectNet.getGreenRectangle());
        panelNet.setBlendMode(BlendMode.MULTIPLY);

        chartWorkCPU.setAnimated(false);
        chartWorkCPU.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        chartSpeedCPU.setAnimated(false);
        chartSpeedCPU.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        chartIntCPU.setAnimated(false);
        chartIntCPU.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());

        chartWorkMem.setAnimated(false);
        chartWorkMem.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        chartSpeedMem.setAnimated(false);
        chartSpeedMem.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        chartIntMem.setAnimated(false);
        chartIntMem.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());

        chartWorkDisk.setAnimated(false);
        chartWorkDisk.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        chartSpeedDisk.setAnimated(false);
        chartSpeedDisk.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        chartIntDisk.setAnimated(false);
        chartIntDisk.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());

        chartWorkNet.setAnimated(false);
        chartWorkNet.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        chartSpeedNet.setAnimated(false);
        chartSpeedNet.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        chartIntNet.setAnimated(false);
        chartIntNet.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());

        columnProcess.setCellValueFactory(new PropertyValueFactory<>("process"));
        columnID.setCellValueFactory(new PropertyValueFactory<>("pid"));
        columnCPU.setCellValueFactory(new PropertyValueFactory<>("cpu"));
        columnMem.setCellValueFactory(new PropertyValueFactory<>("mem"));
        columnCMD.setCellValueFactory(new PropertyValueFactory<>("cmd"));

        tableDetails.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                String cmd = tableDetails.getSelectionModel().getSelectedItem().getCmd();
                if ((!cmd.equals("")) && (!tableDetails.getSelectionModel().getSelectedItem().getProcess().isEmpty()) )
                    new ProcessWindow(cmd).start();
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> dataUpdater()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
     }

    private void cpuUpdate() {
        seriesWorkCPU.getData().clear();
        seriesSpeedCPU.getData().clear();
        seriesIntCPU.getData().clear();

        for (int i = 0; i < 60; i++) {
            seriesWorkCPU.getData().add(new XYChart.Data(i, cpuUsage.getWorkElement(i)));
            seriesSpeedCPU.getData().add(new XYChart.Data(i, cpuUsage.getSpeedElement(i)));
            seriesIntCPU.getData().add(new XYChart.Data(i, cpuUsage.getIntensityElement(i)));
        }

        imgViewCPU.setImage(arrowImg.getImageCPU((int) cpuUsage.getWorkLastElement(), cpuUsage.getAverage(), uBoundCPU, lBoundCPU));
        statBarImgViewCPU.setImage(imgViewCPU.getImage());

        statusBarCpuLabel.setText("CPU: " + String.format("%.2f", cpuUsage.getWorkLastElement()) + "%    ");
        labelCPU.setText(cpuUsage.getCpuModel() + "\nCurrent speed: " + cpuUsage.getMaxSpeed() + " MHz");
        labelWorkCpu.setText("Utilization: " + String.format("%.2f", cpuUsage.getWorkLastElement()) + " (%)");
        labelSpeedCpu.setText("Current speed: " + String.format("%.2f",cpuUsage.getSpeedLastElement()) + " (%/sec");
        labelIntCpu.setText("Current intensity: " + String.format("%.2f", cpuUsage.getIntensityLastElement()) + " (%/sec2)");

        chartWorkCPU.setTitle("Work estimation: " + String.format("%.2f", cpuUsage.getWork()));
        chartSpeedCPU.setTitle("Speed estimation: " + String.format("%.2f", cpuUsage.getSpeed()));
        chartIntCPU.setTitle("Intensity estimation: " + String.format("%.2f", cpuUsage.getIntensity()));

        updateBoundsCPU();

        try {
            cpuUsage.Update();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error while getting CPU info");
            alert.show();
        }
    }

    private void memUpdate() {
        seriesWorkMem.getData().clear();
        seriesSpeedMem.getData().clear();
        seriesIntMem.getData().clear();

        for (int i = 0; i < 60; i++) {
            seriesWorkMem.getData().add(new XYChart.Data(i, memUsage.getWorkElement(i)));
            seriesSpeedMem.getData().add(new XYChart.Data(i, memUsage.getSpeedElement(i)));
            seriesIntMem.getData().add(new XYChart.Data(i, memUsage.getIntensityElement(i)));
        }

        imgViewMem.setImage(arrowImg.getImageMem((int) memUsage.getWorkLastElement(), memUsage.getAverage(), uBoundMem, lBoundMem));
        statBarImgViewMem.setImage(imgViewMem.getImage());

        statusBarMemLabel.setText("Memory: " + String.format("%.2f", memUsage.getWorkLastElement()) + "%   ");
        labelMem.setText("Total memory: " + memUsage.getMgb() + " MB" + "\nIn use: " + memUsage.getWorkLastElement() * memUsage.getMgb() / 100 + " MB");
        labelWorkMem.setText("Utilization: " + String.format("%.2f", memUsage.getWorkLastElement()) + " %");
        labelSpeedMem.setText("Current speed: " + String.format("%.2f",memUsage.getSpeedLastElement()) + " (%/sec");
        labelIntMem.setText("Current intensity: " + String.format("%.2f", memUsage.getIntensityLastElement()) + " (%/sec2)");

        chartWorkMem.setTitle("Work estimation: " + String.format("%.2f", memUsage.getWork()));
        chartSpeedMem.setTitle("Speed estimation: " + String.format("%.2f", memUsage.getSpeed()));
        chartIntMem.setTitle("Intensity estimation: " + String.format("%.2f", memUsage.getIntensity()));

        updateBoundsMemory();

        try {
            memUsage.Update();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error while getting Memory info");
            alert.show();
        }
    }

    private void diskUpdate() {
        seriesWorkDiskWrite.getData().clear();
        seriesWorkDiskRead.getData().clear();
        seriesSpeedDiskWrite.getData().clear();
        seriesSpeedDiskRead.getData().clear();
        seriesIntDiskWrite.getData().clear();
        seriesIntDiskRead.getData().clear();

        for (int i = 0; i < 60; i++) {
            seriesWorkDiskWrite.getData().add(new XYChart.Data(i, diskUsage.getWriteWorkElement(i)));
            seriesWorkDiskRead.getData().add(new XYChart.Data(i, diskUsage.getReadWorkElement(i)));
            seriesSpeedDiskWrite.getData().add(new XYChart.Data(i, diskUsage.getWriteSpeedElement(i)));
            seriesSpeedDiskRead.getData().add(new XYChart.Data(i, diskUsage.getReadSpeedElement(i)));
            seriesIntDiskWrite.getData().add(new XYChart.Data(i, diskUsage.getWriteIntElement(i)));
            seriesIntDiskRead.getData().add(new XYChart.Data(i, diskUsage.getReadIntElement(i)));
        }

        imgViewDiskRead.setImage(arrowImg.getImageDisk((int)diskUsage.getMaxRead() > 0 ? (int)diskUsage.getReadWorkLastElement() / (int)diskUsage.getMaxRead()*100 : 0,
                                                       diskUsage.getAverage(),
                                                       uBoundDisk,
                                                       lBoundDisk));
        statBarImgViewDiskRead.setImage(imgViewDiskRead.getImage());
        imgViewDiskWrite.setImage(arrowImg.getImageDisk((int)diskUsage.getMaxWrite() > 0 ? (int)diskUsage.getWriteWorkLastElement() / (int)diskUsage.getMaxWrite()*100 : 0,
                                                         diskUsage.getAverage(),
                                                         uBoundDisk,
                                                         lBoundDisk));
        statBarImgViewDiskWrite.setImage(imgViewDiskWrite.getImage());

        labelDisk.setText("Disk " + diskUsage.getTitle());
        labelWorkDisk.setText("Read: " + (int)diskUsage.getReadWorkLastElement() + " kB/s\nWrite: " + (int)diskUsage.getWriteWorkLastElement() + " kB/s");
        labelSpeedDisk.setText("Read: " + (int)diskUsage.getReadSpeedLastElement() + " kB/s2 | Write: " + (int)diskUsage.getWriteSpeedLastElement() + " kB/s2");
        labelIntDisk.setText("Read: " + (int)diskUsage.getReadIntLastElement() + " kB/s3 | Write: " + (int)diskUsage.getWriteIntLastElement() + " kB/s3");
        statusBarDiskLabel.setText("Disk: read: " + String.format("%.2f", diskUsage.getReadWorkLastElement() / 1048576.0) + " Mb/s ");
        statusBarDiskLabel2.setText("write: " + String.format("%.2f", diskUsage.getWriteWorkLastElement() / 1048576.0) + " Mb/s ");

        chartWorkDisk.setTitle("Work estimation: (read): " + (int)diskUsage.getWork() + "\t(write): " + (int)diskUsage.getWork2());
        chartSpeedDisk.setTitle("Speed estimation: (read): " + (int)diskUsage.getSpeed() + "\t(write): " + (int)diskUsage.getSpeed2());
        chartIntDisk.setTitle("Intensity estimation: (read): " + (int)diskUsage.getIntensity() + "\t(write): " + (int)diskUsage.getIntensity2());

        updateBoundsDisk();

        try {
            diskUsage.Update();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error while getting Disk i/o info");
            alert.show();
        }
    }

    private void netUpdate() {
        seriesWorkNetSend.getData().clear();
        seriesWorkNetRec.getData().clear();
        seriesSpeedNetSend.getData().clear();
        seriesSpeedNetRec.getData().clear();
        seriesIntNetSend.getData().clear();
        seriesIntNetRec.getData().clear();

        for (int i = 0; i < 60; i++) {
            seriesWorkNetSend.getData().add(new XYChart.Data(i, netUsage.getSendWorkElement(i)));
            seriesWorkNetRec.getData().add(new XYChart.Data(i, netUsage.getRecWorkElement(i)));
            seriesSpeedNetSend.getData().add(new XYChart.Data(i, netUsage.getSendSpeedElement(i)));
            seriesSpeedNetRec.getData().add(new XYChart.Data(i, netUsage.getRecSpeedElement(i)));
            seriesIntNetSend.getData().add(new XYChart.Data(i, netUsage.getSendIntElement(i)));
            seriesIntNetRec.getData().add(new XYChart.Data(i, netUsage.getRecIntElement(i)));
        }

        imgViewNetRec.setImage(arrowImg.getImageNet((int)netUsage.getMaxReceive() > 0 ? (int)netUsage.getRecWorkLastElement() / (int)netUsage.getMaxReceive()*100 : 0,
                netUsage.getAverage(),
                uBoundNet,
                lBoundNet));
        statBarImgViewNetRec.setImage(imgViewNetRec.getImage());
        imgViewNetSend.setImage(arrowImg.getImageNet((int)netUsage.getMaxSend() > 0 ? (int)netUsage.getSendWorkLastElement() / (int)netUsage.getMaxSend()*100 : 0,
                netUsage.getAverage(),
                uBoundNet,
                lBoundNet));
        statBarImgViewNetSend.setImage(imgViewNetSend.getImage());

        labelNet.setText("Net ");
        labelWorkNet.setText("Rec: " + (int)netUsage.getRecWorkLastElement() + " kB/s\nSend: " + (int)netUsage.getSendWorkLastElement() + " kB/s");
        labelSpeedNet.setText("Rec: " + (int)netUsage.getRecSpeedLastElement() + " kB/s2 | Send: " + (int)netUsage.getSendSpeedLastElement() + " kB/s2");
        labelIntNet.setText("Rec: " + (int)netUsage.getRecIntLastElement() + " kB/s3 | Send: " + (int)netUsage.getSendIntLastElement() + " kB/s3");
        statusBarNetLabel.setText("Net: rec: " + String.format("%.2f", netUsage.getRecWorkLastElement() / 1048576.0) + " Mb/s ");
        statusBarNetLabel2.setText("send: " + String.format("%.2f", netUsage.getSendWorkLastElement() / 1048576.0) + " Mb/s ");

        chartWorkNet.setTitle("Work estimation: (rec): " + (int)netUsage.getWork() + "\t(send): " + (int)netUsage.getWork2());
        chartSpeedNet.setTitle("Speed estimation: (rec): " + (int)netUsage.getSpeed() + "\t(send): " + (int)netUsage.getSpeed2());
        chartIntNet.setTitle("Intensity estimation: (rec): " + (int)netUsage.getIntensity() + "\t(send): " + (int)netUsage.getIntensity2());

        updateBoundsNet();

        try {
            netUsage.Update();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error while getting Net sendSpeed/receiving info");
            alert.show();
        }
    }

    private void processesStatUpdate() {
        try {
            ps.Update();
            data = FXCollections.observableArrayList(ps.getProcesses());
            tableDetails.setItems(data);

            columnProcess.setText("Processes: " + ps.getProcessCount());
            columnCPU.setText("CPU: " + String.format("%.2f", ps.getCpuUtil()) + " (%)");
            columnMem.setText("Memory: " + String.format("%.2f", ps.getMemUtil()) + "(kB)");
            statusBarProcLabel.setText("Processes: " + ps.getProcessCount());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dataUpdater() {
        cpuUpdate();
        memUpdate();
        diskUpdate();
        netUpdate();
        processesStatUpdate();
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
        chartRectCPU.updateBounds(uBoundCPU,
                                  lBoundCPU,
                                  chartWorkCPU.getXAxis().getLayoutX(),
                               chartWorkCPU.getYAxis().getLayoutY() + chartWorkCPU.getLayoutY(),
                                  chartWorkCPU.getXAxis().getWidth(),
                                  chartWorkCPU.getYAxis().getZeroPosition());
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
        chartRectMem.updateBounds(uBoundMem,
                                  lBoundMem,
                                  chartWorkMem.getXAxis().getLayoutX(),
                               chartWorkMem.getYAxis().getLayoutY() + chartWorkMem.getLayoutY(),
                                  chartWorkMem.getXAxis().getWidth(),
                                  chartWorkMem.getYAxis().getZeroPosition());
    }

    public void updateBoundsDisk() {
        try {
            uBoundDisk = Integer.parseInt(txFieldUBoundDisk.getText(), 10);
            lBoundDisk = Integer.parseInt(txFieldLBoundDisk.getText(), 10);
        } catch (NumberFormatException e) {
            uBoundDisk = 90;
            lBoundDisk = 30;
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error " + e.getMessage());
            alert.setContentText("Enter only integer value");
            alert.show();
        }
        if (uBoundDisk > 95) uBoundDisk = 95;
        if (uBoundDisk < 50) uBoundDisk = 50;
        if (lBoundDisk < 5) lBoundDisk= 5;
        if (lBoundDisk > 50) lBoundDisk = 50;
        txFieldUBoundDisk.setText(Integer.toString(uBoundDisk));
        txFieldLBoundDisk.setText(Integer.toString(lBoundDisk));
        chartRectDisk.updateBounds(uBoundDisk,
                lBoundDisk,
                chartWorkDisk.getXAxis().getLayoutX(),
                chartWorkDisk.getYAxis().getLayoutY() + chartWorkDisk.getLayoutY(),
                chartWorkDisk.getXAxis().getWidth(),
                chartWorkDisk.getYAxis().getZeroPosition());
    }

    public void updateBoundsNet() {
        try {
            uBoundNet = Integer.parseInt(txFieldUBoundNet.getText(), 10);
            lBoundNet = Integer.parseInt(txFieldLBoundNet.getText(), 10);
        } catch (NumberFormatException e) {
            uBoundNet = 90;
            lBoundNet = 30;
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error " + e.getMessage());
            alert.setContentText("Enter only integer value");
            alert.show();
        }
        if (uBoundNet > 95) uBoundNet = 95;
        if (uBoundNet < 50) uBoundNet = 50;
        if (lBoundNet < 5) lBoundNet= 5;
        if (lBoundNet > 50) lBoundNet = 50;
        txFieldUBoundNet.setText(Integer.toString(uBoundNet));
        txFieldLBoundNet.setText(Integer.toString(lBoundNet));
        chartRectNet.updateBounds(uBoundNet,
                lBoundNet,
                chartWorkNet.getXAxis().getLayoutX(),
                chartWorkNet.getYAxis().getLayoutY() + chartWorkNet.getLayoutY(),
                chartWorkNet.getXAxis().getWidth(),
                chartWorkNet.getYAxis().getZeroPosition());
    }

    public void okBtnOnClick() { updateBoundsCPU(); }

    public void memOkBtnOnClick() {updateBoundsMemory();}

}
