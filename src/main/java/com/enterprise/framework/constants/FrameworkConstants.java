package com.enterprise.framework.constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Central constants repository.
 *
 * INTERN NOTE: If a value is used in more than one place -> define it here.
 * This is the DRY (Don't Repeat Yourself) principle.
 */
public final class FrameworkConstants {

    private FrameworkConstants() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Paths
    public static final String CONFIG_BASE_PATH = "src/main/resources/environments/";
    public static final String REPORT_PATH      = System.getProperty("user.dir") + "/reports/";
    public static final String SCREENSHOT_PATH  = System.getProperty("user.dir") + "/screenshots/";
    public static final String LOG_PATH         = System.getProperty("user.dir") + "/logs/";
    public static final String TEST_DATA_PATH   = "src/test/resources/testdata/";

    // Report
    public static final String REPORT_NAME      = "Automation_Execution_Report";
    public static final String REPORT_TITLE     = "Enterprise Selenium Framework — SauceDemo";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd_HH-mm-ss";
    public static final String REPORT_FILE_NAME =
        REPORT_PATH + REPORT_NAME + "_" +
        LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT)) + ".html";

    // Wait times (seconds)
    public static final int EXPLICIT_WAIT_SHORT   = 5;
    public static final int EXPLICIT_WAIT_DEFAULT = 10;
    public static final int EXPLICIT_WAIT_LONG    = 20;
    public static final int PAGE_LOAD_TIMEOUT     = 30;
    public static final int SCRIPT_TIMEOUT        = 30;

    // Retry
    public static final int RETRY_COUNT = 1;

    // App constants (SauceDemo)
    public static final String APP_TITLE          = "Swag Labs";
    public static final String INVENTORY_PAGE_URL = "inventory.html";
    public static final String CART_PAGE_URL      = "cart.html";
    public static final String CHECKOUT_STEP1_URL = "checkout-step-one.html";
    public static final String CHECKOUT_STEP2_URL = "checkout-step-two.html";
    public static final String CHECKOUT_DONE_URL  = "checkout-complete.html";
}
