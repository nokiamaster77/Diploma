package sample;

import org.hyperic.sigar.Sigar;

/**
 * Created by Maksym on 11/24/2016.
 */
public abstract class Monitor {
    static protected Sigar sigar = new Sigar();
    protected int [] arr;
    final protected int size = 60;
    protected double [] derivative;
    protected double sum = 0;
    protected double []sumArr;

    public Monitor() {
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

    public abstract void Update () throws Exception;

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
