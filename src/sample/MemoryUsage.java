package sample;

import org.hyperic.sigar.Sigar;

/**
 * Created by Maksym on 10/4/2016.
 */
public class MemoryUsage {

    final int size = 60;
    private int [] arr;
    private Sigar sigar = new Sigar();

    public MemoryUsage() {
        arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = 0;
    }

    public int getElement(int i) throws IndexOutOfBoundsException {
        return arr[i];
    }

    public void Update () throws Exception {
        for (int i = 0; i < arr.length - 1; i++)
            arr[i] = arr[i+1];
        double used = sigar.getMem().getUsed(), free = sigar.getMem().getFree();
        arr[arr.length-1] = (int) ((used / (used + free)) * 100);
    }

    public int getLastElement() {
        return arr[arr.length-1];
    }

    public int getAverage() {
        int avg = 0;
        for (int i = arr.length - 1; i > arr.length - 3; i--)
            avg += arr[i];
        return avg / 2;
    }

}
