package sample;

import org.hyperic.sigar.SigarException;

/**
 * Created by Maksym on 9/30/2016.
 */
public class CPUUsage extends Monitor{

    private String cpuModel;
    private int mgz;

    public CPUUsage() {
        super();
        try {
            cpuModel = sigar.getCpuInfoList()[0].getModel();
            mgz = sigar.getCpuInfoList()[0].getMhz();
        } catch (SigarException e) {
            cpuModel = "";
            mgz = 0;
        }
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public int getMaxSpeed() {
        return mgz;
    }

    public void Update () throws Exception {
        for (int i = 0; i < arrWork.length - 1; i++) {
            arrWork[i] = arrWork[i+1];
            arrSpeed[i] = arrSpeed[i+1];
            arrIntensity[i] = arrIntensity[i+1];
        }
        arrWork[arrWork.length-1] = (sigar.getCpuPerc().getCombined() * 100.0);
        arrSpeed[arrSpeed.length-1] = ((arrWork[arrWork.length-1] - arrWork[arrWork.length-3])) / 2.0;
        arrIntensity[arrIntensity.length-1] = ((arrSpeed[arrSpeed.length-1] - arrSpeed[arrSpeed.length-3])) / 2.0;

        work = findIntegralSum(arrWork, 60, 0);
        speed = findIntegralSum(arrSpeed, 60, 0);
        intensity = findIntegralSum(arrIntensity, 60, 0);

        mgz = sigar.getCpuInfoList()[0].getMhz();
    }
}
