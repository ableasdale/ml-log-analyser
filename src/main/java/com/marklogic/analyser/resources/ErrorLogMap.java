package com.marklogic.analyser.resources;

import java.util.HashMap;
import java.util.List;
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
        private static Map<String, List<String>> INSTANCE = new HashMap<String, List<String>>();
    }

    public static Map<String, List<String>> getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void setInstance(Map<String, List<String>> map) {
        LazyHolder.INSTANCE = map;
    }

}
