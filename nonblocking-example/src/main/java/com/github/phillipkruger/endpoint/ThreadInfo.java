package com.github.phillipkruger.endpoint;

import java.util.Map;

public class ThreadInfo {

    private Map<String,String> info;

    public ThreadInfo() {
    }

    public ThreadInfo(Map<String, String> info) {
        this.info = info;
    }

    public Map<String, String> getInfo() {
        return info;
    }

    public void setInfo(Map<String, String> info) {
        this.info = info;
    }
}
