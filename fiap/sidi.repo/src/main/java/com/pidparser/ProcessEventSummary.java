package com.pidparser;

/**
 * Represents aggregated data for a specific event within a process.
 */
public class ProcessEventSummary {
    private String event;
    private String uid;
    private String pid;
    private String processName;
    private int counter;
    private String firstTimestamp;

    public ProcessEventSummary(String event, String uid, String pid, String processName, 
                              int counter, String firstTimestamp) {
        this.event = event;
        this.uid = uid;
        this.pid = pid;
        this.processName = processName;
        this.counter = counter;
        this.firstTimestamp = firstTimestamp;
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

    public int getCounter() {
        return counter;
    }

    public String getFirstTimestamp() {
        return firstTimestamp;
    }

    public String toOutputLine() {
        return String.format("%s| %s| %s| %s | %d | %s",
                event, uid, pid, processName, counter, firstTimestamp);
    }
}

