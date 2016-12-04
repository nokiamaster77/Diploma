package sample;

import org.hyperic.sigar.Sigar;

/**
 * Created by Maksym on 10/4/2016.
 */
public class MemoryUsage extends Monitor{

    public MemoryUsage() {
        super();
    }

    public void Update () throws Exception {
        for (int i = 0; i < arr.length - 1; i++)
            arr[i] = arr[i+1];
        double used = sigar.getMem().getUsed(), free = sigar.getMem().getFree();
        arr[arr.length-1] = (int) ((used / (used + free)) * 100);
    }
}
