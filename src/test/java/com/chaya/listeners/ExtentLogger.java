package com.chaya.listeners;

// Small helper so tests can log steps into the Extent report.
// Lives on the test side: src/main code cannot use src/test classes.
public class ExtentLogger {

    public static void info(String message) {
        if (TestListener.getTest() != null) TestListener.getTest().info(message);
    }

    public static void pass(String message) {
        if (TestListener.getTest() != null) TestListener.getTest().pass(message);
    }
}
