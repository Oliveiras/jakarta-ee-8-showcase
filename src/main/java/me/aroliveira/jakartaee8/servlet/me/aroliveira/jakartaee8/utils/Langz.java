package me.aroliveira.jakartaee8.servlet.me.aroliveira.jakartaee8.utils;

public final class Langz {

    public static <T, U extends T> T defaultIfNull(T o, U def) {
        return (o == null) ? def : o;
    }

    public static String defaultIfBlank(String s, String def) {
        return (s == null || s.isEmpty() || s.matches("\\s*")) ? def : s;
    }

    public static void unsafeSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
