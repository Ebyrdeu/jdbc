package dev.ebyrdeu.lib.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private Utils() {
    }

    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public static <T> void log(T value) {
        if (value == null) throw new RuntimeException("Null value provided");

        System.out.println(value);
    }
}
