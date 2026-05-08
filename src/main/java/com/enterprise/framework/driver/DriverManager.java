package com.enterprise.framework.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * DriverManager - ThreadLocal WebDriver for parallel-safe execution.
 *
 * INTERN NOTE: ThreadLocal<T> gives each thread its own private variable.
 *
 *   Thread 1 (test A) -> has its own Chrome driver
 *   Thread 2 (test B) -> has its own Firefox driver
 *   They NEVER interfere with each other.
 *
 * Think of it like a hotel: each guest (thread) gets their own room (driver).
 * The front desk (DriverManager) assigns rooms but guests never share.
 *
 * CRITICAL RULE: Always call removeDriver() in @AfterMethod!
 * Forgetting this causes memory leaks in long test runs.
 */
public final class DriverManager {

    private static final Logger log = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {}

    /**
     * Assigns a WebDriver to the current thread.
     * Called in BaseTest @BeforeMethod.
     */
    public static void setDriver(WebDriver driver) {
        if (driver == null) throw new IllegalArgumentException("WebDriver cannot be null");
        driverThreadLocal.set(driver);
        log.debug("Driver assigned to thread: [{}]", Thread.currentThread().getName());
    }

    /**
     * Retrieves the WebDriver for the current thread.
     * Used everywhere: page objects, base classes, utilities.
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null)
            throw new IllegalStateException(
                "Driver not initialized for thread: [" + Thread.currentThread().getName() + "]. " +
                "Ensure @BeforeMethod calls DriverManager.setDriver().");
        return driver;
    }

    /**
     * Removes driver reference from this thread.
     * MUST be called in @AfterMethod to prevent memory leaks.
     */
    public static void removeDriver() {
        driverThreadLocal.remove();
        log.debug("Driver removed from thread: [{}]", Thread.currentThread().getName());
    }

    /** Returns true if a driver exists for the current thread. */
    public static boolean isDriverInitialized() {
        return driverThreadLocal.get() != null;
    }
}
