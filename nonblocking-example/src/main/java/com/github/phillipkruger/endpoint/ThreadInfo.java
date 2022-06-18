package com.github.phillipkruger.endpoint;

public class ThreadInfo {

    private long id;
    private String name;
    private String state;    
    private int priority;
    private String threadGroup;
    private boolean isAlive;
    private boolean isDaemon;
    
    public ThreadInfo() {
    }

    public ThreadInfo(long id, String name, String state, int priority, String threadGroup, boolean isAlive, boolean isDaemon) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.priority = priority;
        this.threadGroup = threadGroup;
        this.isAlive = isAlive;
        this.isDaemon = isDaemon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getThreadGroup() {
        return threadGroup;
    }

    public void setThreadGroup(String threadGroup) {
        this.threadGroup = threadGroup;
    }

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isIsDaemon() {
        return isDaemon;
    }

    public void setIsDaemon(boolean isDaemon) {
        this.isDaemon = isDaemon;
    }
}
