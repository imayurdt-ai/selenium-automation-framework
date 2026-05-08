package com.enterprise.framework.enums;

/**
 * Supported test environments.
 * Each maps to: src/main/resources/environments/{env}.properties
 */
public enum EnvironmentType {
    QA,
    STAGING,
    PROD;

    public static EnvironmentType fromString(String value) {
        try {
            return EnvironmentType.valueOf(value.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Unsupported environment: [" + value + "]. Supported: QA, STAGING, PROD");
        }
    }
}
