package sample;

/**
 * Created by Maksym on 9/30/2016.
 */
public class CPUUsage extends Monitor{

    public CPUUsage() {
        super();
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
}
