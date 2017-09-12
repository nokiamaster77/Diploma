package sample;

import org.hyperic.sigar.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maksym on 12/14/2016.
 */
public class SingleProcessStat {
    private static Sigar sigar = new Sigar();

    private int mgz;
    private double mgb;
    private long mgbLong;
    private int cpuCoreCount;

    private long [] allPid;
    private ArrayList<Long> processFamilyID;

    private ArrayList processes = null;
    private Map<Long, Long[]> prevCpuMap;
    private Map<Long, Long[]> curCpuMap;

    private double cpuUtil;
    private double memUtil;

    private double cpuUtilTotal = 0.0;
    private double memUtilTotal = 0.0;
    private double cpuDerUtilTotal = 0.0;
    private double memDerUtilTotal = 0.0;
    private double cpuDer2UtilTotal = 0.0;
    private double memDer2UtilTotal = 0.0;

    private String title;
    private int time;

    private double [] cpu;
    private double [] mem;
    private double [] cpuDer;
    private double [] memDer;
    private double [] cpuDer2;
    private double [] memDer2;

    private static int n = 1;

    public SingleProcessStat(String title, int time) {
        processFamilyID = new ArrayList<Long>();
        processes = new ArrayList<CProcessInfo>();
        this.time = time;
        this.title = title;

        cpu = new double[this.time];
        mem = new double[this.time];
        cpuDer = new double[this.time];
        memDer = new double[this.time];
        cpuDer2 = new double[this.time];
        memDer2 = new double[this.time];

        for (int i = 0; i < this.time; i++) {
            cpu[i] = 0;
            mem[i] = 0;
            cpuDer[i] = 0;
            memDer[i] = 0;
            cpuDer2[i] = 0;
            memDer2[i] = 0;
        }

        try {
            cpuCoreCount = sigar.getCpuList().length;
            mgz = sigar.getCpuInfoList()[0].getMhz();
            mgbLong = sigar.getMem().getTotal() / 1048576;
            mgb = (double)sigar.getMem().getTotal();


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

    // implements Simpson method
    private double findIntegralSum(double[] arr, int start, int end) throws Exception {
        if ((end >= 0) && (end < arr.length) && (end <= start-2) && (start > 1) && (start <= arr.length)) {
            double sum = 0;
            // h = (b-a)/2N
            double h = 1.0;
            double rez = 0.0;
            for (int i = end+2; i <= start-2; i += 2) {
                if (arr[i] < 0) {

                }
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

    private void findCpuUtilTotal() {
        try {
            cpuUtilTotal = findIntegralSum(cpu, 60, 0);
        } catch (Exception e) {
            cpuUtilTotal = 0;
        }

    }

    private void findMemUtilTotal() {
        try {
            memUtilTotal = findIntegralSum(mem, 60, 0);
        } catch (Exception e) {
            memUtilTotal = 0;
        }
    }

    private void findMemDerUtilTotal() {
        try {
            memDerUtilTotal = findIntegralSum(memDer, 60, 0);
        } catch (Exception e) {
            memDerUtilTotal = 0;
        }
    }

    private void findCpuDerUtilTotal() {
        try {
            cpuDerUtilTotal = findIntegralSum(cpuDer, 60, 0);
        } catch (Exception e) {
            cpuDerUtilTotal = 0;
        }
    }

    private void findMemDer2UtilTotal() {
        try {
            memDer2UtilTotal = findIntegralSum(memDer2, 60, 0);
        } catch (Exception e) {
            memDer2UtilTotal = 0;
        }
    }

    private void findCpuDer2UtilTotal() {
        try {
            cpuDer2UtilTotal = findIntegralSum(cpuDer2, 60, 0);
        } catch (Exception e) {
            cpuDer2UtilTotal = 0;
        }
    }

    public double getCpuUtilTotal() {
        return cpuUtilTotal;
    }

    public double getMemUtilTotal() {
        return memUtilTotal;
    }

    public double getCpuDerUtilTotal() {
        return cpuDerUtilTotal;
    }

    public double getMemDerUtilTotal() {
        return memDerUtilTotal;
    }

    public double getCpuDer2UtilTotal() {
        return cpuDer2UtilTotal;
    }

    public double getMemDer2UtilTotal() {
        return memDer2UtilTotal;
    }

    public double getCpuElement(int i) throws IndexOutOfBoundsException {
        return cpu[i];
    }

    public double getMemElement(int i) throws IndexOutOfBoundsException {
        return mem[i];
    }

    public double getCpuLastElement() {
        return cpu[cpu.length-1];
    }

    public double getMemLastElement() {
        return mem[mem.length-1];
    }

    public double getDerCpuElement(int i) throws IndexOutOfBoundsException{
        return cpuDer[i];
    }

    public double getDerMemElement(int i) throws IndexOutOfBoundsException{
        return memDer[i];
    }

    public double getDer2CpuElement(int i) throws IndexOutOfBoundsException{
        return cpuDer2[i];
    }

    public double getDer2MemElement(int i) throws IndexOutOfBoundsException{
        return memDer2[i];
    }

    public double getCpuUtil() {
        return cpuUtil;
    }

    public double getMemUtil() {
        return memUtil;
    }

    public String getTitle() {
        return title;
    }

    public int getMgz() {
        return mgz;
    }

    public long getMgb() {
        return mgbLong;
    }

    public int getTime() {
        return time;
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
            updateMgz();
        } catch (SigarException e) {
            e.printStackTrace();
        }
        processes.clear();
        processFamilyID.clear();

        cpuUtil = 0.0;
        memUtil = 0.0;

        for (int i = 0; i < allPid.length; i++) {
            try {
                String pathName = ProcUtil.getDescription(sigar, allPid[i]);
                if (pathName.equals(this.title)) {
                    processFamilyID.add(allPid[i]);
                    processes.add(getProcessInfo(allPid[i]));
                }

            } catch (SigarException e) {
                //e.printStackTrace();
            }
        }
        prevCpuMap.clear();
        prevCpuMap.putAll(curCpuMap);
        curCpuMap.clear();
    }


    private CProcessInfo getProcessInfo(long pid) throws SigarException {
        String unknown = "-";

        CProcessInfo pi = new CProcessInfo();
        String pathName = ProcUtil.getDescription(sigar, pid);

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
            long resident = mem.getResident();
            //System.out.println("Pid: " + pid  + " Resident: " + resident + "Total: " + mgb + "\n");
            double processMem = ((double)resident) / mgb * 100.0;
            memUtil += processMem;
            pi.setMem(String.format("%.2f", processMem));
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
        return processFamilyID.size();
    }

    private void updateMgz() {
        try {
            mgz = sigar.getCpuInfoList()[0].getMhz();
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }

    private void updateChartData() {
        for (int i = 0; i < this.time-1; i++) {
            cpu[i] = cpu[i+1];
            mem[i] = mem[i+1];
            cpuDer[i] = cpuDer[i+1];
            memDer[i] = memDer[i+1];
            cpuDer2[i] = cpuDer2[i+1];
            memDer2[i] = memDer2[i+1];
        }
        cpu[this.time-1] = getCpuUtil();
        mem[this.time-1] = getMemUtil();
        cpuDer[cpuDer.length-1] = (cpu[cpu.length-1] - cpu[cpu.length-3]) / 2.0;
        memDer[memDer.length-1] = (mem[mem.length-1] - mem[mem.length-3]) / 2.0;
        cpuDer2[cpuDer2.length-1] = (cpuDer[cpuDer.length-1] - cpuDer[cpuDer.length-3]) / 2.0;
        memDer2[memDer2.length-1] = (memDer[memDer.length-1] - memDer[memDer.length-3]) / 2.0;

        findCpuUtilTotal();
        findMemUtilTotal();
        findCpuDerUtilTotal();
        findMemDerUtilTotal();
        findCpuDer2UtilTotal();
        findMemDer2UtilTotal();

    }

    public void Update() throws Exception {
        getProcStat();
        updateChartData();
    }
}
