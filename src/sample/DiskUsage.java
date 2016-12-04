package sample;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.ArrayList;

/**
 * Created by Maksym on 10/13/2016.
 */
public class DiskUsage extends Monitor{

    private double lastWrite = 0;
    private double lastRead = 0;
    private double beforeLastWrite = 0;
    private double beforeLastRead = 0;
    private double [] read;
    private double [] write;
    private boolean flag = false;
    public DiskUsage() {
        super();
        read = new double[size];
        write = new double[size];
        for (int i = 0; i < size; i++) {
            read[i] = 0;
            write[i] = 0;
        }
    }
    private void getIO() throws SigarException {
        FileSystem[] fsList = sigar.getFileSystemList();
        double tempWrite = 0;
        double tempRead = 0;
        for (int i=0; i<fsList.length; i++) {
            if (fsList[i].getType() == FileSystem.TYPE_LOCAL_DISK) {
                org.hyperic.sigar.DiskUsage usage = sigar.getDiskUsage(fsList[i].getDirName());
                tempWrite += (double)usage.getWriteBytes();
                tempRead += (double)usage.getReadBytes();
            }
        }
        if(flag) {
            lastWrite = Math.abs(tempWrite - beforeLastWrite) ;
            lastRead = Math.abs(tempRead - beforeLastRead) ;
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
            write[i] = write[i+1];
            read[i] = read[i+1];
        }
        write[write.length-1] = lastWrite;
        read[read.length-1] =  lastRead;
    }

    public double getWriteElement(int i) throws IndexOutOfBoundsException {
        return write[i];
    }
    public double getReadElement(int i) throws IndexOutOfBoundsException {
        return read[i];
    }

    public double getWriteLastElement() {
        return write[write.length-1];
    }
    public double getReadLastElement() {
        return read[read.length-1];
    }
    public int getAverageWrite() {
        int avg = 0;
        for (int i = write.length - 1; i > write.length - 3; i--)
            avg += write[i];
        return avg / 2;
    }
    public int getAverageRead() {
        int avg = 0;
        for (int i = read.length - 1; i > read.length - 3; i--)
            avg += read[i];
        return avg / 2;
    }

}
