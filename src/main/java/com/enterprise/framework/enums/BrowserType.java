package com.enterprise.framework.enums;

/**
 * Supported browser types.
 *
 * INTERN NOTE: Enums prevent typo bugs like "Crhome" silently failing.
 * Usage: BrowserType.fromString("chrome") -> BrowserType.CHROME
 */
public enum BrowserType {
    CHROME,
    FIREFOX,
    EDGE,
    SAFARI;

    public static BrowserType fromString(String value) {
        try {
            return BrowserType.valueOf(value.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Unsupported browser: [" + value + "]. Supported: CHROME, FIREFOX, EDGE, SAFARI");
        }
    }
}
