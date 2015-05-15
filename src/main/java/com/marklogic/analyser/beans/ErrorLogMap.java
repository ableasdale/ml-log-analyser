package com.marklogic.analyser.beans;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * TODO - Describe
 * <p/>
 * User: Alex
 * Date: 05/05/15
 * Time: 09:36
 */
public class ErrorLogMap {

    private static class LazyHolder {
        private static ConcurrentNavigableMap<String, ErrorLog> INSTANCE = new ConcurrentSkipListMap<String, ErrorLog>();
    }

    public static ConcurrentNavigableMap<String, ErrorLog> getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void setInstance(ConcurrentNavigableMap<String, ErrorLog> map) {
        LazyHolder.INSTANCE = map;
    }

}
