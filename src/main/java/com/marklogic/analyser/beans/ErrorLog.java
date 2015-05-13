package com.marklogic.analyser.beans;

import com.marklogic.analyser.util.Consts;

import java.util.List;
import java.util.Map;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 05/05/15
 * Time: 14:02
 */
public class ErrorLog {

    private String name;
    private int totalRestarts;
    private List<String> errorLogTxt;
    private List<String> errorLogRestartTxt;
    private Map<String, List<String>> occurrenceMap;
    private Map<String, List<String>> otherMessages;
    private Map<String, List<String>> traceEventMessages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalRestarts() {
        return totalRestarts;
    }

    public void setTotalRestarts(int totalRestarts) {
        this.totalRestarts = totalRestarts;
    }

    public List<String> getErrorLogTxt() {
        return errorLogTxt;
    }

    public List<String> getErrorLogHead() {
        if (errorLogTxt.size() > Consts.MAX_LINES_FOR_LOG_PREVIEW)
            return errorLogTxt.subList(0, Consts.MAX_LINES_FOR_LOG_PREVIEW);
        else return getErrorLogTxt();
    }

    public void setErrorLogTxt(List<String> errorLogTxt) {
        this.errorLogTxt = errorLogTxt;
    }

    public List<String> getErrorLogRestartTxt() {
        return errorLogRestartTxt;
    }

    public void setErrorLogRestartTxt(List<String> errorLogRestartTxt) {
        this.errorLogRestartTxt = errorLogRestartTxt;
    }

    public Map<String, List<String>> getOccurrenceMap() {
        return occurrenceMap;
    }

    public void setOccurrenceMap(Map<String, List<String>> occurrenceMap) {
        this.occurrenceMap = occurrenceMap;
    }

    public Map<String, List<String>> getOtherMessages() {
        return otherMessages;
    }

    public void setOtherMessages(Map<String, List<String>> otherMessages) {
        this.otherMessages = otherMessages;
    }

    public Map<String, List<String>> getTraceEventMessages() {
        return traceEventMessages;
    }

    public void setTraceEventMessages(Map<String, List<String>> traceEventMessages) {
        this.traceEventMessages = traceEventMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorLog errorLog = (ErrorLog) o;

        if (totalRestarts != errorLog.totalRestarts) return false;
        if (!errorLogRestartTxt.equals(errorLog.errorLogRestartTxt)) return false;
        if (!errorLogTxt.equals(errorLog.errorLogTxt)) return false;
        if (!name.equals(errorLog.name)) return false;
        if (!occurrenceMap.equals(errorLog.occurrenceMap)) return false;
        if (!otherMessages.equals(errorLog.otherMessages)) return false;
        if (!traceEventMessages.equals(errorLog.traceEventMessages)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + totalRestarts;
        result = 31 * result + errorLogTxt.hashCode();
        result = 31 * result + errorLogRestartTxt.hashCode();
        result = 31 * result + occurrenceMap.hashCode();
        result = 31 * result + otherMessages.hashCode();
        result = 31 * result + traceEventMessages.hashCode();
        return result;
    }
}