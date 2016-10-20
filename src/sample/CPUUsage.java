package sample;

import org.hyperic.sigar.Sigar;

/**
 * Created by Maksym on 9/30/2016.
 */
public class CPUUsage {


    final int size = 60;
    private int [] arr;
    private double [] derivative;
    private Sigar sigar = new Sigar();
    private double sum = 0;
    private double []sumArr;

    public CPUUsage() {
        arr = new int[size];
        derivative = new double[size];
        sumArr = new double[size];

        for (int i = 0; i < size; i++) {
            arr[i] = 0;
            derivative[i] = 0;
            sumArr[i] = 0;
        }
    }

    public int getElement(int i) throws IndexOutOfBoundsException {
        return arr[i];
    }

    public double getDerElement(int i) throws IndexOutOfBoundsException {
        return derivative[i];
    }

    public void Update () throws Exception {
        for (int i = 0; i < arr.length - 1; i++) {
            arr[i] = arr[i+1];
            derivative[i] = derivative[i+1];
            sumArr[i] = sumArr[i+1];
        }
        arr[arr.length-1] = (int) (sigar.getCpuPerc().getCombined() * 100);
        derivative[derivative.length-1] = ((double)(arr[arr.length-1] - arr[arr.length-3])) / 2.0;

        if (sum >= 10000) sum = 0;
        sum += arr[arr.length-1];
        sumArr[sumArr.length-1] = sum;

    }

    public int getLastElement() {
        return arr[arr.length-1];
    }

    public double getSumElement(int i)throws IndexOutOfBoundsException {
        return sumArr[i];
    }

    public int getAverage() {
        int avg = 0;
        for (int i = arr.length - 1; i > arr.length - 3; i--)
            avg += arr[i];
        return avg / 2;
    }
}
