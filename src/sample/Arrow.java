package sample;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

/**
 * Created by Maksym on 10/4/2016.
 */
public class Arrow {
    private Image [] img;
    private Image [] imgTray;

    public Arrow() {
        img = new Image[45];
        imgTray = new Image[4];
        try {
            img[0] = new Image("sample/GreenDown.png");
            img[1] = new Image("sample/GreenRight.png");
            img[2] = new Image("sample/GreenUp.png");
            img[3] = new Image("sample/YellowDown.png");
            img[4] = new Image("sample/YellowRight.png");
            img[5] = new Image("sample/YellowUp.png");
            img[6] = new Image("sample/RedDown.png");
            img[7] = new Image("sample/RedRight.png");
            img[8] = new Image("sample/RedUp.png");

            img[9] = new Image("sample/icons/GreenDownCPU.png");
            img[10] = new Image("sample/icons/GreenRightCPU.png");
            img[11] = new Image("sample/icons/GreenUpCPU.png");
            img[12] = new Image("sample/icons/YellowDownCPU.png");
            img[13] = new Image("sample/icons/YellowRightCPU.png");
            img[14] = new Image("sample/icons/YellowUpCPU.png");
            img[15] = new Image("sample/icons/RedDownCPU.png");
            img[16] = new Image("sample/icons/RedRightCPU.png");
            img[17] = new Image("sample/icons/RedUpCPU.png");

            img[18] = new Image("sample/icons/GreenDownMem.png");
            img[19] = new Image("sample/icons/GreenRightMem.png");
            img[20] = new Image("sample/icons/GreenUpMem.png");
            img[21] = new Image("sample/icons/YellowDownMem.png");
            img[22] = new Image("sample/icons/YellowRightMem.png");
            img[23] = new Image("sample/icons/YellowUpMem.png");
            img[24] = new Image("sample/icons/RedDownMem.png");
            img[25] = new Image("sample/icons/RedRightMem.png");
            img[26] = new Image("sample/icons/RedUpMem.png");

            img[27] = new Image("sample/icons/GreenDownDisk.png");
            img[28] = new Image("sample/icons/GreenRightDisk.png");
            img[29] = new Image("sample/icons/GreenUpDisk.png");
            img[30] = new Image("sample/icons/YellowDownDisk.png");
            img[31] = new Image("sample/icons/YellowRightDisk.png");
            img[32] = new Image("sample/icons/YellowUpDisk.png");
            img[33] = new Image("sample/icons/RedDownDisk.png");
            img[34] = new Image("sample/icons/RedRightDisk.png");
            img[35] = new Image("sample/icons/RedUpDisk.png");

            img[36] = new Image("sample/icons/GreenDownNet.png");
            img[37] = new Image("sample/icons/GreenRightNet.png");
            img[38] = new Image("sample/icons/GreenUpNet.png");
            img[39] = new Image("sample/icons/YellowDownNet.png");
            img[40] = new Image("sample/icons/YellowRightNet.png");
            img[41] = new Image("sample/icons/YellowUpNet.png");
            img[42] = new Image("sample/icons/RedDownNet.png");
            img[43] = new Image("sample/icons/RedRightNet.png");
            img[44] = new Image("sample/icons/RedUpNet.png");

            imgTray[0] = img[9];
            imgTray[1] = img[18];
            imgTray[2] = img[27];
            imgTray[3] = img[36];

        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error while loading images!");
            alert.showAndWait();
        }
    }

    public Image getImgTrayCPU() {
        return imgTray[0];
    }

    public Image getImgTrayMem() {
        return imgTray[1];
    }

    public Image getImgTrayDisk() {
        return imgTray[2];
    }

    public Image getImgTrayNet() {
        return imgTray[3];
    }

    public Image getImageCPU(int lastElem, int avg, int uBound, int lBound ) {
        if (lastElem > uBound) {
            if (Math.abs(lastElem - avg) <= 5 ) {
                imgTray[0] = getRedRightCPU();
                return getRedRight();
            } else if (avg - lastElem  > 5) {
                imgTray[0] = getRedDownCPU();
                return getRedDown();
            } else {
                imgTray[0] = getRedUpCPU();
                return getRedUp();
            }
        } else if (lastElem > lBound) {
            if (Math.abs(lastElem - avg) <= 5 ) {
                imgTray[0] = getYellowRightCPU();
                return getYellowRight();
            } else if (avg - lastElem  > 5) {
                imgTray[0] = getYellowDownCPU();
                return getYellowDown();
            } else {
                imgTray[0] = getYellowUpCPU();
                return getYellowUp();
            }
        } else {
            if (Math.abs(lastElem - avg) <= 5 ) {
                imgTray[0] = getGreenRightCPU();
                return getGreenRight();
            } else if (avg - lastElem  > 5) {
                imgTray[0] = getGreenDownCPU();
                return getGreenDown();
            } else {
                imgTray[0] = getGreenUpCPU();
                return getGreenUp();
            }
        }
    }

    public Image getImageMem(int lastElem, int avg, int uBound, int lBound ) {
        if (lastElem > uBound) {
            if (Math.abs(lastElem - avg) <= 5 ) {
                imgTray[1] = getRedRightMem();
                return getRedRight();
            } else if (avg - lastElem  > 5) {
                imgTray[1] = getRedDownMem();
                return getRedDown();
            } else {
                imgTray[1] = getRedUpMem();
                return getRedUp();
            }
        } else if (lastElem > lBound) {
            if (Math.abs(lastElem - avg) <= 5 ) {
                imgTray[1] = getYellowRightMem();
                return getYellowRight();
            } else if (avg - lastElem  > 5) {
                imgTray[1] = getYellowDownMem();
                return getYellowDown();
            } else {
                imgTray[1] = getYellowUpMem();
                return getYellowUp();
            }
        } else {
            if (Math.abs(lastElem - avg) <= 5 ) {
                imgTray[1] = getGreenRightMem();
                return getGreenRight();
            } else if (avg - lastElem  > 5) {
                imgTray[1] = getGreenDownMem();
                return getGreenDown();
            } else {
                imgTray[1] = getGreenUpMem();
                return getGreenUp();
            }
        }
    }

    public Image getImageDisk(int lastElem, int avg, int uBound, int lBound ) {
        if (lastElem > uBound) {
            if (Math.abs(lastElem - avg) <= 5 ) {
                imgTray[2] = getRedRightDisk();
                return getRedRight();
            } else if (avg - lastElem  > 5) {
                imgTray[2] = getRedDownDisk();
                return getRedDown();
            } else {
                imgTray[2] = getRedUpDisk();
                return getRedUp();
            }
        } else if (lastElem > lBound) {
            if (Math.abs(lastElem - avg) <= 5 ) {
                imgTray[2] = getYellowRightDisk();
                return getYellowRight();
            } else if (avg - lastElem  > 5) {
                imgTray[2] = getYellowDownDisk();
                return getYellowDown();
            } else {
                imgTray[2] = getYellowUpDisk();
                return getYellowUp();
            }
        } else {
            if (Math.abs(lastElem - avg) <= 5 ) {
                imgTray[2] = getGreenRightDisk();
                return getGreenRight();
            } else if (avg - lastElem  > 5) {
                imgTray[2] = getGreenDownDisk();
                return getGreenDown();
            } else {
                imgTray[2] = getGreenUpDisk();
                return getGreenUp();
            }
        }
    }

    public Image getImageNet(int lastElem, int avg, int uBound, int lBound ) {
        if (lastElem > uBound) {
            if (Math.abs(lastElem - avg) <= 5 ) {
                imgTray[3] = getRedRightNet();
                return getRedRight();
            } else if (avg - lastElem  > 5) {
                imgTray[3] = getRedDownNet();
                return getRedDown();
            } else {
                imgTray[3] = getRedUpNet();
                return getRedUp();
            }
        } else if (lastElem > lBound) {
            if (Math.abs(lastElem - avg) <= 5 ) {
                imgTray[3] = getYellowRightNet();
                return getYellowRight();
            } else if (avg - lastElem  > 5) {
                imgTray[3] = getYellowDownNet();
                return getYellowDown();
            } else {
                imgTray[3] = getYellowUpNet();
                return getYellowUp();
            }
        } else {
            if (Math.abs(lastElem - avg) <= 5 ) {
                imgTray[3] = getGreenRightNet();
                return getGreenRight();
            } else if (avg - lastElem  > 5) {
                imgTray[3] = getGreenDownNet();
                return getGreenDown();
            } else {
                imgTray[3] = getGreenUpNet();
                return getGreenUp();
            }
        }
    }

    public Image getGreenDown() {
        return img[0];
    }
    public Image getGreenRight() {
        return img[1];
    }
    public Image getGreenUp() {
        return img[2];
    }
    public Image getYellowDown() {
        return img[3];
    }
    public Image getYellowRight() {
        return img[4];
    }
    public Image getYellowUp() {
        return img[5];
    }
    public Image getRedDown() {
        return img[6];
    }
    public Image getRedRight() {
        return img[7];
    }
    public Image getRedUp() {
        return img[8];
    }

    public Image getGreenDownCPU() {
        return img[9];
    }
    public Image getGreenRightCPU() {
        return img[10];
    }
    public Image getGreenUpCPU() { return img[11]; }
    public Image getYellowDownCPU() {
        return img[12];
    }
    public Image getYellowRightCPU() {
        return img[13];
    }
    public Image getYellowUpCPU() {
        return img[14];
    }
    public Image getRedDownCPU() {
        return img[15];
    }
    public Image getRedRightCPU() {
        return img[16];
    }
    public Image getRedUpCPU() {
        return img[17];
    }

    public Image getGreenDownMem() {
        return img[18];
    }
    public Image getGreenRightMem() {
        return img[19];
    }
    public Image getGreenUpMem() {
        return img[20];
    }
    public Image getYellowDownMem() {
        return img[21];
    }
    public Image getYellowRightMem() {
        return img[22];
    }
    public Image getYellowUpMem() {
        return img[23];
    }
    public Image getRedDownMem() {
        return img[24];
    }
    public Image getRedRightMem() {
        return img[25];
    }
    public Image getRedUpMem() {
        return img[26];
    }

    public Image getGreenDownDisk() {
        return img[27];
    }
    public Image getGreenRightDisk() {
        return img[28];
    }
    public Image getGreenUpDisk() {
        return img[29];
    }
    public Image getYellowDownDisk() {
        return img[30];
    }
    public Image getYellowRightDisk() {
        return img[31];
    }
    public Image getYellowUpDisk() {
        return img[32];
    }
    public Image getRedDownDisk() {
        return img[33];
    }
    public Image getRedRightDisk() {
        return img[34];
    }
    public Image getRedUpDisk() {
        return img[35];
    }

    public Image getGreenDownNet() {
        return img[36];
    }
    public Image getGreenRightNet() {
        return img[37];
    }
    public Image getGreenUpNet() {
        return img[38];
    }
    public Image getYellowDownNet() {
        return img[39];
    }
    public Image getYellowRightNet() {
        return img[40];
    }
    public Image getYellowUpNet() {
        return img[41];
    }
    public Image getRedDownNet() {
        return img[42];
    }
    public Image getRedRightNet() {
        return img[43];
    }
    public Image getRedUpNet() {
        return img[44];
    }

}
