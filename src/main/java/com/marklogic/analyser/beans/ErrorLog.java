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
    private List<String> fragmentMessages;
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

    public List<String> getFragmentMessages() {
        return fragmentMessages;
    }

    public void setFragmentMessages(List<String> fragmentMessages) {
        this.fragmentMessages = fragmentMessages;
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
        if (name != null ? !name.equals(errorLog.name) : errorLog.name != null) return false;
        if (errorLogTxt != null ? !errorLogTxt.equals(errorLog.errorLogTxt) : errorLog.errorLogTxt != null)
            return false;
        if (errorLogRestartTxt != null ? !errorLogRestartTxt.equals(errorLog.errorLogRestartTxt) : errorLog.errorLogRestartTxt != null)
            return false;
        if (fragmentMessages != null ? !fragmentMessages.equals(errorLog.fragmentMessages) : errorLog.fragmentMessages != null)
            return false;
        if (occurrenceMap != null ? !occurrenceMap.equals(errorLog.occurrenceMap) : errorLog.occurrenceMap != null)
            return false;
        if (otherMessages != null ? !otherMessages.equals(errorLog.otherMessages) : errorLog.otherMessages != null)
            return false;
        return !(traceEventMessages != null ? !traceEventMessages.equals(errorLog.traceEventMessages) : errorLog.traceEventMessages != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + totalRestarts;
        result = 31 * result + (errorLogTxt != null ? errorLogTxt.hashCode() : 0);
        result = 31 * result + (errorLogRestartTxt != null ? errorLogRestartTxt.hashCode() : 0);
        result = 31 * result + (fragmentMessages != null ? fragmentMessages.hashCode() : 0);
        result = 31 * result + (occurrenceMap != null ? occurrenceMap.hashCode() : 0);
        result = 31 * result + (otherMessages != null ? otherMessages.hashCode() : 0);
        result = 31 * result + (traceEventMessages != null ? traceEventMessages.hashCode() : 0);
        return result;
    }
}