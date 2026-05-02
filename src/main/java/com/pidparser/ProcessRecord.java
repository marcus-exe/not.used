package com.pidparser;

/**
 * Represents a single process record from the input file.
 */
public class ProcessRecord {
    private String timestamp;
    private String event;
    private String uid;
    private String pid;
    private String processName;

    public ProcessRecord(String timestamp, String event, String uid, String pid, String processName) {
        this.timestamp = timestamp.trim();
        this.event = event.trim();
        this.uid = uid.trim();
        this.pid = pid.trim();
        this.processName = processName.trim();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getEvent() {
        return event;
    }

    public String getUid() {
        return uid;
    }

    public String getPid() {
        return pid;
    }

    public String getProcessName() {
        return processName;
    }

    @Override
    public String toString() {
        return "ProcessRecord{" +
                "timestamp='" + timestamp + '\'' +
                ", event='" + event + '\'' +
                ", uid='" + uid + '\'' +
                ", pid='" + pid + '\'' +
                ", processName='" + processName + '\'' +
                '}';
    }
}

