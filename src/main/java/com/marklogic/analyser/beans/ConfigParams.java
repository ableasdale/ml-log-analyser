package com.marklogic.analyser.beans;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 15/05/15
 * Time: 10:52
 */
public class ConfigParams {

    private boolean firstRun = true;

    public boolean isFirstRun() {
        return firstRun;
    }

    public void setFirstRun(boolean firstRun) {
        this.firstRun = firstRun;
    }

    private static class LazyHolder {
        private static ConfigParams INSTANCE = new ConfigParams();
    }

    public static ConfigParams getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void setInstance(ConfigParams cfg) {
        LazyHolder.INSTANCE = cfg;
    }
}
