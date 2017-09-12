package sample;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.SigarException;

/**
 * Created by Maksym on 10/13/2016.
 */
public class DiskUsage extends Monitor{

    private double lastWrite = 0;
    private double lastRead = 0;
    private double beforeLastWrite = 0;
    private double beforeLastRead = 0;
    private double [] writeWork;
    private double [] writeSpeed;
    private double [] writeInt;
    private boolean flag = false;
    private double maxWrite;
    private double maxRead;
    private double work2;
    private double speed2;
    private double intensity2;
    private String diskTitle;

    public DiskUsage() {
        super();
        writeWork = new double[size];
        writeSpeed = new double[size];
        writeInt = new double[size];
        work2 = 0;
        speed2 = 0;
        intensity2 = 0;
        maxWrite = 0;
        maxRead = 0;
        diskTitle = "";
        for (int i = 0; i < size; i++) {
            writeWork[i] = 0;
            writeSpeed[i] = 0;
            writeInt[i] = 0;
        }
    }

    private void getIO() throws SigarException {
        FileSystem[] fsList = sigar.getFileSystemList();
        double tempWrite = 0;
        double tempRead = 0;
        diskTitle = "";
        for (int i=0; i<fsList.length; i++) {
            if (fsList[i].getType() == FileSystem.TYPE_LOCAL_DISK) {
                diskTitle += fsList[i].getDirName() + " ";
                org.hyperic.sigar.DiskUsage usage = sigar.getDiskUsage(fsList[i].getDirName());
                tempWrite += (double)usage.getWriteBytes();
                tempRead += (double)usage.getReadBytes();
            }
        }
        if(flag) {
            lastWrite = Math.abs(tempWrite - beforeLastWrite);
            lastRead = Math.abs(tempRead - beforeLastRead);
            if (lastWrite > maxWrite) maxWrite = lastWrite;
            if (lastRead > maxRead) maxRead = lastRead;
        }
        beforeLastWrite = tempWrite;
        beforeLastRead = tempRead;
    }

    public void Update () throws Exception {
        try {
            getIO();
        } catch(SigarException e) {
            System.out.println(e.getMessage());
        }
        if (!flag) flag = true;
        for (int i = 0; i < size - 1; i++) {
            arrWork[i] = arrWork[i+1];
            arrSpeed[i] = arrSpeed[i+1];
            arrIntensity[i] = arrIntensity[i+1];
            writeWork[i] = writeWork[i+1];
            writeSpeed[i] = writeSpeed[i+1];
            writeInt[i] = writeInt[i+1];
        }
        arrWork[arrWork.length-1] =  lastRead;
        arrSpeed[arrSpeed.length-1] = ((arrWork[arrWork.length-1] - arrWork[arrWork.length-3])) / 2.0;
        arrIntensity[arrIntensity.length-1] = ((arrSpeed[arrSpeed.length-1] - arrSpeed[arrSpeed.length-3])) / 2.0;

        writeWork[writeWork.length-1] = lastWrite;
        writeSpeed[writeSpeed.length-1] = ((writeWork[writeWork.length-1] - writeWork[writeWork.length-3])) / 2.0;
        writeInt[writeInt.length-1] = ((writeSpeed[writeSpeed.length-1] - writeSpeed[writeSpeed.length-3])) / 2.0;

        work = findIntegralSum(arrWork, 60, 0);
        speed = findIntegralSum(arrSpeed, 60, 0);
        intensity = findIntegralSum(arrIntensity, 60, 0);

        work2 = findIntegralSum(writeWork, 60, 0);
        speed2 = findIntegralSum(writeSpeed, 60, 0);
        intensity2 = findIntegralSum(writeInt, 60, 0);
    }

    public double getWriteWorkElement(int i) throws IndexOutOfBoundsException {
        return writeWork[i];
    }

    public double getReadWorkElement(int i) throws IndexOutOfBoundsException {
        return arrWork[i];
    }

    public double getWriteWorkLastElement() {
        return writeWork[writeWork.length-1];
    }

    public double getReadWorkLastElement() {
        return arrWork[arrWork.length-1];
    }

    public double getWriteSpeedElement(int i) throws IndexOutOfBoundsException {
        return writeSpeed[i];
    }

    public double getReadSpeedElement(int i) throws IndexOutOfBoundsException {
        return arrSpeed[i];
    }

    public double getWriteSpeedLastElement() {
        return writeSpeed[writeSpeed.length-1];
    }

    public double getReadSpeedLastElement() {
        return arrSpeed[arrSpeed.length-1];
    }

    public double getWriteIntElement(int i) throws IndexOutOfBoundsException {
        return writeInt[i];
    }

    public double getReadIntElement(int i) throws IndexOutOfBoundsException {
        return arrIntensity[i];
    }

    public double getWriteIntLastElement() {
        return writeInt[writeInt.length-1];
    }

    public double getReadIntLastElement() {
        return arrIntensity[arrIntensity.length-1];
    }

    public int getAverageWrite() {
        int avg = 0;
        for (int i = writeWork.length - 1; i > writeWork.length - 3; i--)
            avg += writeWork[i] / maxWrite;
        return avg / 2;
    }
    public int getAverageRead() {
        int avg = 0;
        for (int i = arrWork.length - 1; i > arrWork.length - 3; i--)
            avg += arrWork[i] / maxRead;
        return avg / 2;
    }

    public double getWork2() {
        return work2;
    }

    public double getSpeed2() {
        return speed2;
    }

    public double getIntensity2() {
        return intensity2;
    }

    public double getMaxWrite() {
        return maxWrite;
    }

    public double getMaxRead() {
        return maxRead;
    }

    public String getTitle() {
        return diskTitle;
    }
}
