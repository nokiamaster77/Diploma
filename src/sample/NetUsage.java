package sample;

import org.hyperic.sigar.*;
import sample.Monitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Maksym on 11/30/2016..
 */
public class NetUsage extends Monitor {
    double [] receive;
    double [] send;
    double lastRec = 0, lastSend = 0;

    static Map<String, Long> rxCurrentMap = new HashMap<String, Long>();
    static Map<String, List<Long>> rxChangeMap = new HashMap<String, List<Long>>();
    static Map<String, Long> txCurrentMap = new HashMap<String, Long>();
    static Map<String, List<Long>> txChangeMap = new HashMap<String, List<Long>>();

    public NetUsage() {
        super();
        receive = new double[size];
        send = new double[size];
        for (int i = 0; i < size; i++) {
            receive[i] = 0;
            send[i] = 0;
        }
        try {
            System.out.println(networkInfo());
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }
    public static String networkInfo() throws SigarException {
        String info = sigar.getNetInfo().toString();
        info += "\n"+ sigar.getNetInterfaceConfig().toString();

        return info;
    }
    public void Update() throws Exception {
        newMetricThread();
        for (int i = 0; i < size - 1; i++) {
            receive[i] = receive[i+1];
            send[i] = send[i+1];
        }
        receive[receive.length-1] = lastRec;
        send[send.length-1] =  lastSend;
    }

    double getReceiveElement(int i) throws IndexOutOfBoundsException {
        return receive[i];
    }

    double getSendElement(int i) throws IndexOutOfBoundsException {
        return send[i];
    }

    double getLastReceiveElement() {
        return lastRec;
    }

    double getLastSendElement() {
        return lastSend;
    }

    /*
    public String getDefaultGateway() throws SigarException {
        return sigar.getNetInfo().getDefaultGateway();
    }
*/
    public void newMetricThread() throws SigarException, InterruptedException {
        Long[] m = getMetric();
        /*long totalrx = m[0];
        long totaltx = m[1];*/
        lastRec = (double) m[0];
        lastSend = (double) m[1];
             /*   = System.out.print("totalrx(download): ");
            System.out.println("\t" + Sigar.formatSize(totalrx));
            System.out.print("totaltx(upload): ");
            System.out.println("\t" + Sigar.formatSize(totaltx));
            System.out.println("-----------------------------------");*/
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
                                   Map<String, List<Long>> changeMap, String hwaddr, long current,
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
}
