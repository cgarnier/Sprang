package com.twomoro.sprang.models.config;

/**
 * Created by cgarnier on 07/05/14.
 */
public class Output{
    private String path;
    private boolean oneFile;
    private String configName;

    Output() {
        this.oneFile = true;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isOneFile() {
        return oneFile;
    }

    public void setOneFile(boolean oneFile) {
        this.oneFile = oneFile;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }
}