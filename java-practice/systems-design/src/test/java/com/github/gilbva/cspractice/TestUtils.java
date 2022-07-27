package com.github.gilbva.cspractice;

public class TestUtils {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
