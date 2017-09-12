package sample;

/**
 * Created by Maksym on 12/5/2016.
 */
@SuppressWarnings("ALL")
public class CProcessInfo {
    private String process;
    private long pid;
    private String cpu;
    private String mem;
    private String cmd;

    public CProcessInfo(String process, long pid, String cpu, String mem, String cmd) {
        this.process = process;
        this.pid = pid;
        this.cpu = cpu;
        this.mem = mem;
        this.cmd = cmd;
    }

    public CProcessInfo() {
        this.process = "";
        this.pid = 0;
        this.cpu = "";
        this.mem = "";
        this.cmd = "";
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
