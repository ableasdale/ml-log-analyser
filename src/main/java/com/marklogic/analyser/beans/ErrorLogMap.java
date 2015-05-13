package com.marklogic.analyser.beans;

import com.marklogic.analyser.beans.ErrorLog;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 05/05/15
 * Time: 09:36
 */
public class ErrorLogMap {

    private static class LazyHolder {
        private static Map<String, ErrorLog> INSTANCE = new HashMap<String, ErrorLog>();
    }

    public static Map<String, ErrorLog> getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void setInstance(Map<String, ErrorLog> map) {
        LazyHolder.INSTANCE = map;
    }

}
