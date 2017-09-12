package sample;

import org.hyperic.sigar.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maksym on 12/5/2016.
 */
public class ProcessStat extends Monitor{
    private long [] allPid;
    private ArrayList processes = null;
    private Map<Long, Long[]> prevCpuMap;
    private Map<Long, Long[]> curCpuMap;
    private int cpuCoreCount;
    private double cpuUtil;
    private double memUtil;

    public ProcessStat() {

        cpuUtil = 0.0;
        memUtil = 0.0;

        try {
            cpuCoreCount = sigar.getCpuList().length;

            prevCpuMap = new HashMap<Long, Long[]>();
            curCpuMap = new HashMap<Long, Long[]>();
            allPid = getPidsSnapshot();
            for (int i = 0; i < allPid.length; i++) {
                prevCpuMap.put(allPid[i], new Long[] {0L, 0L});
            }

            getProcStat();

        } catch (SigarException e) {
            System.out.println(e.getMessage());
        }
    }

    public double getCpuUtil() {
        return cpuUtil;
    }

    public double getMemUtil() {
        return memUtil;
    }

    public ArrayList<CProcessInfo> getProcesses() {
        return processes;
    }

    private  static long [] getPidsSnapshot() throws SigarException {
        return sigar.getProcList();
    }

    private void getProcStat() {
        try {
            allPid = getPidsSnapshot();
        } catch (SigarException e) {
            e.printStackTrace();
        }

        processes = new ArrayList<CProcessInfo>();

        cpuUtil = 0.0;
        memUtil = 0.0;

        for (int i = 0; i < allPid.length; i++) {
            try {
                processes.add(getProcessInfo(allPid[i]));
            } catch (SigarException e) {
                System.out.println(e.getMessage());
            }
        }

        prevCpuMap.clear();
        prevCpuMap.putAll(curCpuMap);
        curCpuMap.clear();
    }

    private CProcessInfo getProcessInfo(long pid) throws SigarException {
        String unknown = " - ";
        CProcessInfo pi = new CProcessInfo();

        String pathName = ProcUtil.getDescription(sigar, pid);
        String []tokens = pathName.split("[\\\\|/]");
        pi.setProcess(tokens[tokens.length-1]);

        pi.setPid(pid);

        try {
            ProcCpu ps = sigar.getProcCpu(pid);

            double percent;
            long total = ps.getTotal();
            long lastTime = ps.getLastTime();

            if (prevCpuMap.containsKey(pid)) {
                if (prevCpuMap.get(pid)[0] != 0) {
                    percent = getPercent(prevCpuMap.get(pid), new Long[] {total, lastTime});
                    pi.setCpu(String.format("%.2f", percent));
                    cpuUtil += percent;
                }
                else
                    pi.setCpu("0");
            } else {
                pi.setCpu("0");
            }
            curCpuMap.put(pid, new Long[] {total, lastTime});

        } catch (SigarException e) {
            pi.setCpu(unknown);
        }
        try {
            ProcMem mem = sigar.getProcMem(pid);
            long processMem = mem.getResident() / 1024;
            memUtil += processMem;
            pi.setMem(String.format("%,d", processMem));
        } catch (SigarException e) {
            pi.setMem(unknown);
        }

        pi.setCmd(pathName);

        return pi;
    }

    private double getPercent(Long[] prev, Long[] cur) {
        double deltaTotal =  (double)(cur[0] - prev[0]);
        double deltaLastTime = (double)(cur[1] - prev[1]);
        return  deltaTotal/ deltaLastTime / (double) cpuCoreCount * 100.0;
    }

    public int getProcessCount() {
        return allPid.length;
    }

    @Override
    public void Update() throws Exception {
        getProcStat();
    }

}
