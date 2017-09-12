package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main extends Application {
    Stage stage;
    static {
        String OS = System.getProperty("os.name").toLowerCase();
        if(OS.indexOf("win") >= 0) {
            try {
                NativeUtils.loadLibraryFromJar("/lib/sigar-x86-winnt.dll");
            } catch (java.lang.UnsatisfiedLinkError e) {
                try {
                    NativeUtils.loadLibraryFromJar("/lib/sigar-amd64-winnt.dll");
                } catch (IOException e2) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Unable load WinX64.dll");
                    alert.showAndWait();
                }
            }
            catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Unable load WinX86.dll");
                alert.showAndWait();
            }
        } else if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {
            try {
                NativeUtils.loadLibraryFromJar("/lib/libsigar-amd64-linux.so");
            } catch (java.lang.UnsatisfiedLinkError e) {
                try {
                    NativeUtils.loadLibraryFromJar("/lib/libsigar-x86-linux.so");
                } catch (IOException e2) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Unable load LinuxAMD64.so");
                    alert.showAndWait();
                }
            }
            catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Unable load LinuxAMD64.so");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {



        this.stage = primaryStage;

        // instructs the javafx system not to exit implicitly when the last application window is shut.
        Platform.setImplicitExit(false);

        // sets up the tray icon (using awt code run on the swing thread).
        javax.swing.SwingUtilities.invokeLater(this::addAppToTray);

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.getStylesheets().add(Main.class.getResource("modena.css").toExternalForm());
        primaryStage.setTitle("Resource monitor");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }



    BufferedImage imageCPU = SwingFXUtils.fromFXImage( Controller.arrowImg.getImgTrayCPU(), null);
    BufferedImage imageMem = SwingFXUtils.fromFXImage( Controller.arrowImg.getImgTrayMem(), null);
    BufferedImage imageDisk = SwingFXUtils.fromFXImage( Controller.arrowImg.getImgTrayDisk(), null);
    BufferedImage imageNet = SwingFXUtils.fromFXImage( Controller.arrowImg.getImgTrayNet(), null);

    SystemTray tray = SystemTray.getSystemTray();
    TrayIcon trayIconCPU = new TrayIcon(imageCPU);
    TrayIcon trayIconMem = new TrayIcon(imageMem);
    TrayIcon trayIconDisk = new TrayIcon(imageDisk);
    TrayIcon trayIconNet = new TrayIcon(imageNet);

    public void updateTrayIcons() {
        imageCPU = SwingFXUtils.fromFXImage( Controller.arrowImg.getImgTrayCPU(), null);
        imageMem = SwingFXUtils.fromFXImage( Controller.arrowImg.getImgTrayMem(), null);
        imageDisk = SwingFXUtils.fromFXImage( Controller.arrowImg.getImgTrayDisk(), null);
        imageNet = SwingFXUtils.fromFXImage( Controller.arrowImg.getImgTrayNet(), null);

        trayIconCPU.setImage(imageCPU);
        trayIconMem.setImage(imageMem);
        trayIconDisk.setImage(imageDisk);
        trayIconNet.setImage(imageNet);
    }
    private void addAppToTray() {
        try {
            // ensure awt toolkit is initialized.
            Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support.
            if (!SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                Platform.exit();
            }

            trayIconCPU.setImageAutoSize(true);
            trayIconMem.setImageAutoSize(true);
            trayIconDisk.setImageAutoSize(true);
            trayIconNet.setImageAutoSize(true);

            // if the user double-clicks on the tray icon, show the main app stage.
            trayIconCPU.addActionListener(event -> Platform.runLater(this::showStage));
            trayIconMem.addActionListener(event -> Platform.runLater(this::showStage));
            trayIconDisk.addActionListener(event -> Platform.runLater(this::showStage));
            trayIconNet.addActionListener(event -> Platform.runLater(this::showStage));

            // if the user selects the default menu item (which includes the app name),
            // show the main app stage.
            CheckboxMenuItem cpu = new CheckboxMenuItem("CPU", true);
            CheckboxMenuItem mem = new CheckboxMenuItem("Memory", true);
            CheckboxMenuItem disk = new CheckboxMenuItem("Disk", true);
            CheckboxMenuItem net = new CheckboxMenuItem("Net", true);
            //java.awt.MenuItem openItem = new java.awt.MenuItem("hello, world");
            /*cpu.addItemListener(new ItemListener() { public void itemStateChanged(ItemEvent e)
                                    { if (e.)
                                        statusLabel.setText("Apple Checkbox: checked"););
              */
            // to really exit the application, the user must go to the system tray icon
            // and select the exit option, this will shutdown JavaFX and remove the
            // tray icon (removing the tray icon will also shut down AWT).
           MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(event -> {
                tray.remove(trayIconCPU);
                tray.remove(trayIconMem);
                tray.remove(trayIconDisk);
                tray.remove(trayIconNet);
                Platform.exit();

            });

            // setup the popup menu for the application.
            final PopupMenu popupCPU = new PopupMenu();
            popupCPU.add(cpu);
            popupCPU.add(mem);
            popupCPU.add(disk);
            popupCPU.add(net);
            popupCPU.addSeparator();
            popupCPU.add(exitItem);
            trayIconCPU.setPopupMenu(popupCPU);

            // add the application tray icon to the system tray.
            tray.add(trayIconCPU);
            tray.add(trayIconMem);
            tray.add(trayIconDisk);
            tray.add(trayIconNet);

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> updateTrayIcons()));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

        } catch (java.awt.AWTException e){ //| IOException e) {
            System.out.println("Unable to init system tray");
            //e.printStackTrace();
        }
    }

    /**
     * Shows the application stage and ensures that it is brought ot the front of all stages.
     */
    private void showStage() {
        if (stage != null) {
            stage.show();
            stage.toFront();
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
