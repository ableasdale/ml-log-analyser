package com.marklogic.analyser.beans;

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
    private List<String> errorLogTxt;
    private Map<String, Integer> occurrenceMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getErrorLogTxt() {
        return errorLogTxt;
    }


    public List<String> getErrorLogHead() {
        if (errorLogTxt.size() > 10000) return errorLogTxt.subList(0, 9999);
        else return getErrorLogTxt();
    }

    public void setErrorLogTxt(List<String> errorLogTxt) {
        this.errorLogTxt = errorLogTxt;
    }

    public Map<String, Integer> getOccurrenceMap() {
        return occurrenceMap;
    }

    public void setOccurrenceMap(Map<String, Integer> occurrenceMap) {
        this.occurrenceMap = occurrenceMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorLog)) return false;

        ErrorLog errorLog = (ErrorLog) o;

        if (errorLogTxt != null ? !errorLogTxt.equals(errorLog.errorLogTxt) : errorLog.errorLogTxt != null)
            return false;
        if (name != null ? !name.equals(errorLog.name) : errorLog.name != null) return false;
        if (occurrenceMap != null ? !occurrenceMap.equals(errorLog.occurrenceMap) : errorLog.occurrenceMap != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (errorLogTxt != null ? errorLogTxt.hashCode() : 0);
        result = 31 * result + (occurrenceMap != null ? occurrenceMap.hashCode() : 0);
        return result;
    }
}