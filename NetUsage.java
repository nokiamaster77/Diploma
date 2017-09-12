package sample;

import org.hyperic.sigar.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Maksym on 11/30/2016..
 */
public class NetUsage extends Monitor {
    private double [] sendWork;
    private double [] sendSpeed;
    private double [] sendInt;
    private double lastRec = 0.0;
    private double lastSend = 0.0;
    private double maxReceive = 0.0;
    private double maxSend = 0.0;
    private double work2;
    private double speed2;
    private double intensity2;

    static Map<String, Long> rxCurrentMap = new HashMap<String, Long>();
    static Map<String, List<Long>> rxChangeMap = new HashMap<String, List<Long>>();
    static Map<String, Long> txCurrentMap = new HashMap<String, Long>();
    static Map<String, List<Long>> txChangeMap = new HashMap<String, List<Long>>();

    public NetUsage() {
        super();
        sendWork = new double[size];
        sendSpeed = new double[size];
        sendInt = new double[size];
        work2 = 0.0;
        speed2 = 0.0;
        intensity2 = 0.0;
        for (int i = 0; i < size; i++) {
            sendWork[i] = 0;
            sendSpeed[i] = 0;
            sendInt[i] = 0;
        }
        /*try {
            System.out.println(networkInfo());
        } catch (SigarException e) {
            e.printStackTrace();
        }*/
    }

    public int getAverageSend() {
        int avg = 0;
        for (int i = sendWork.length - 1; i > sendWork.length - 3; i--)
            avg += sendWork[i] / maxSend;
        return avg / 2;
    }
    public int getAverageReceive() {
        int avg = 0;
        for (int i = arrWork.length - 1; i > arrWork.length - 3; i--)
            avg += arrWork[i] / maxReceive;
        return avg / 2;
    }

    public static String networkInfo() throws SigarException {
        String info = sigar.getNetInfo().toString();
        info += "\n"+ sigar.getNetInterfaceConfig().toString();
        return info;
    }

    public void Update() throws Exception {
        newMetricThread();
        for (int i = 0; i < size - 1; i++) {
            arrWork[i] = arrWork[i+1];
            arrSpeed[i] = arrSpeed[i+1];
            arrIntensity[i] = arrIntensity[i+1];
            sendWork[i] = sendWork[i+1];
            sendSpeed[i] = sendSpeed[i+1];
            sendInt[i] = sendInt[i+1];
        }
        arrWork[arrWork.length-1] = lastRec;
        arrSpeed[arrSpeed.length-1] = ((arrWork[arrWork.length-1] - arrWork[arrWork.length-3])) / 2.0;
        arrIntensity[arrIntensity.length-1] = ((arrSpeed[arrSpeed.length-1] - arrSpeed[arrSpeed.length-3])) / 2.0;

        sendWork[sendWork.length-1] =  lastSend;
        sendSpeed[sendSpeed.length-1] = ((sendWork[sendWork.length-1] - sendWork[sendWork.length-3])) / 2.0;
        sendInt[sendInt.length-1] = ((sendSpeed[sendSpeed.length-1] - sendSpeed[sendSpeed.length-3])) / 2.0;

        work = findIntegralSum(arrWork, 60, 0);
        speed = findIntegralSum(arrSpeed, 60, 0);
        intensity = findIntegralSum(arrIntensity, 60, 0);

        work2 = findIntegralSum(sendWork, 60, 0);
        speed2 = findIntegralSum(sendSpeed, 60, 0);
        intensity2 = findIntegralSum(sendInt, 60, 0);
    }

    public double getSendWorkElement(int i) throws IndexOutOfBoundsException {
        return sendWork[i];
    }

    public double getRecWorkElement(int i) throws IndexOutOfBoundsException {
        return arrWork[i];
    }

    public double getSendWorkLastElement() {
        return sendWork[sendWork.length-1];
    }

    public double getRecWorkLastElement() {
        return arrWork[arrWork.length-1];
    }

    public double getSendSpeedElement(int i) throws IndexOutOfBoundsException {
        return sendSpeed[i];
    }

    public double getRecSpeedElement(int i) throws IndexOutOfBoundsException {
        return arrSpeed[i];
    }

    public double getSendSpeedLastElement() {
        return sendSpeed[sendSpeed.length-1];
    }

    public double getRecSpeedLastElement() {
        return arrSpeed[arrSpeed.length-1];
    }

    public double getSendIntElement(int i) throws IndexOutOfBoundsException {
        return sendInt[i];
    }

    public double getRecIntElement(int i) throws IndexOutOfBoundsException {
        return arrIntensity[i];
    }

    public double getSendIntLastElement() {
        return sendInt[sendInt.length-1];
    }

    public double getRecIntLastElement() {
        return arrIntensity[arrIntensity.length-1];
    }

    public void newMetricThread() throws SigarException, InterruptedException {
        Long[] m = getMetric();
        lastRec = (double) m[0];
        lastSend = (double) m[1];
        if (lastRec > maxReceive) maxReceive = lastRec;
        if (lastSend > maxSend) maxSend = lastSend;
    }

    public Long[] getMetric() throws SigarException {
        for (String ni : sigar.getNetInterfaceList()) {
            NetInterfaceStat netStat = sigar.getNetInterfaceStat(ni);
            NetInterfaceConfig ifConfig = sigar.getNetInterfaceConfig(ni);
            String hwaddr = null;
            if (!NetFlags.NULL_HWADDR.equals(ifConfig.getHwaddr())) {
                hwaddr = ifConfig.getHwaddr();
            }
            if (hwaddr != null) {
                long rxCurrenttmp = netStat.getRxBytes();
                saveChange(rxCurrentMap, rxChangeMap, hwaddr, rxCurrenttmp, ni);
                long txCurrenttmp = netStat.getTxBytes();
                saveChange(txCurrentMap, txChangeMap, hwaddr, txCurrenttmp, ni);
            }
        }
        long totalrx = getMetricData(rxChangeMap);
        long totaltx = getMetricData(txChangeMap);
        for (List<Long> l : rxChangeMap.values())
            l.clear();
        for (List<Long> l : txChangeMap.values())
            l.clear();
        return new Long[] { totalrx, totaltx };
    }

    private long getMetricData(Map<String, List<Long>> rxChangeMap) {
        long total = 0;
        for (Map.Entry<String, List<Long>> entry : rxChangeMap.entrySet()) {
            int average = 0;
            for (Long l : entry.getValue()) {
                average += l;
            }
            total += average / entry.getValue().size();
        }
        return total;
    }

    private void saveChange(Map<String, Long> currentMap,
                            Map<String, List<Long>> changeMap,
                            String hwaddr,
                            long current,
                            String ni) {
        Long oldCurrent = currentMap.get(ni);
        if (oldCurrent != null) {
            List<Long> list = changeMap.get(hwaddr);
            if (list == null) {
                list = new LinkedList<Long>();
                changeMap.put(hwaddr, list);
            }
            list.add((current - oldCurrent));
        }
        currentMap.put(ni, current);
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

    public double getMaxReceive() {
        return maxReceive;
    }

    public double getMaxSend() {
        return maxSend;
    }
}
