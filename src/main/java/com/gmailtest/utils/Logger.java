package com.gmailtest.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public static void info(String message) {
        System.out.println(timestamp() + " [INFO] " + message);
    }

    public static void warn(String message) {
        System.out.println(timestamp() + " [WARN] " + message);
    }

    public static void error(String message) {
        System.err.println(timestamp() + " [ERROR] " + message);
    }

    private static String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
