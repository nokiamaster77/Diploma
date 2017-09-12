package sample;

import org.hyperic.sigar.Sigar;

/**
 * Created by Maksym on 11/24/2016.
 */
public abstract class Monitor {
    static protected Sigar sigar = new Sigar();
    protected double [] arrWork;
    final protected int size = 60;
    protected double [] arrSpeed;
    protected double sum = 0;
    protected double [] arrIntensity;
    protected double work;
    protected double speed;
    protected double intensity;

    public Monitor() {
        arrWork = new double[size];
        arrSpeed = new double[size];
        arrIntensity = new double[size];

        work = 0;
        speed = 0;
        intensity = 0;

        for (int i = 0; i < size; i++) {
            arrWork[i] = 0.0;
            arrSpeed[i] = 0.0;
            arrIntensity[i] = 0.0;
        }
    }

    public double getWorkElement(int i) throws IndexOutOfBoundsException {
        return arrWork[i];
    }

    public double getSpeedElement(int i) throws IndexOutOfBoundsException {
        return arrSpeed[i];
    }

    public double getIntensityElement(int i) throws IndexOutOfBoundsException {
        return arrIntensity[i];
    }

    public abstract void Update () throws Exception;

    public double getWorkLastElement() {
        return arrWork[arrWork.length-1];
    }

    public double getSpeedLastElement() {
        return arrSpeed[arrSpeed.length-1];
    }

    public double getIntensityLastElement() {
        return arrIntensity[arrIntensity.length-1];
    }

    public int getAverage() {
        int avg = 0;
        for (int i = arrWork.length - 1; i > arrWork.length - 3; i--)
            avg += (int)arrWork[i];
        return avg / 2;
    }

    protected static double findIntegralSum(double[] arr, int start, int end) throws Exception {
        if ((end >= 0) && (end < arr.length) && (end <= start-2) && (start > 1) && (start <= arr.length)) {
            double sum = 0;
            // h = (b-a)/2N
            double h = 1.0;
            double rez = 0.0;
            for (int i = end+2; i <= start-2; i += 2) {
                sum += arr[i] >= 0.0 ? arr[i] : arr[i] * -1.0;
            }
            rez += sum * 2;
            sum = 0.0;

            for (int i = end+1; i < start-1; i += 2) {
                sum += arr[i] >= 0.0 ? arr[i] : arr[i] * -1.0;
            }
            rez += sum * 4;

            rez += arr[end] >= 0.0 ? arr[end] : arr[end] * -1.0;
            rez += arr[start-1] >= 0.0 ? arr[start-1] : arr[start-1] * -1.0;

            return rez  * h / 3.0;
        }
        throw new Exception("Illegal index parameters");

    }

    public double getWork() {
        return work;
    }

    public double getSpeed() {
        return speed;
    }

    public double getIntensity() {
        return intensity;
    }
}
