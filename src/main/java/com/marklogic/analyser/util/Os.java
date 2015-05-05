package com.marklogic.analyser.util;

public class Os {

    public static boolean isWindows() {
        return Consts.HOST_OS.indexOf("windows") >= 0;
    }

    public static boolean isLinux() {
        return Consts.HOST_OS.indexOf("linux") >= 0;
    }

    public static boolean isMac() {
        return Consts.HOST_OS.startsWith("mac") || Consts.HOST_OS.startsWith("darwin");
    }

    public static boolean isSolaris() {
        return Consts.HOST_OS.indexOf("sunos") >= 0;
    }
}