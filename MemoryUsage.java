package sample;

import org.hyperic.sigar.SigarException;

/**
 * Created by Maksym on 10/4/2016.
 */
public class MemoryUsage extends Monitor{

    private long mgb;

    public MemoryUsage() {
        super();
        try {
            mgb = sigar.getMem().getTotal() / 1048576;
        } catch (SigarException e) {
            mgb = 0;
        }
    }

    public long getMgb() {
        return mgb;
    }

    public void Update () throws Exception {
        for (int i = 0; i < arrWork.length - 1; i++)
            arrWork[i] = arrWork[i+1];
        double used = sigar.getMem().getUsed(), free = sigar.getMem().getFree();
        arrWork[arrWork.length-1] = (int) ((used / (used + free)) * 100);
        arrSpeed[arrSpeed.length-1] = ((arrWork[arrWork.length-1] - arrWork[arrWork.length-3])) / 2.0;
        arrIntensity[arrIntensity.length-1] = ((arrSpeed[arrSpeed.length-1] - arrSpeed[arrSpeed.length-3])) / 2.0;

        work = findIntegralSum(arrWork, 60, 0);
        speed = findIntegralSum(arrSpeed, 60, 0);
        intensity = findIntegralSum(arrIntensity, 60, 0);
    }
}
