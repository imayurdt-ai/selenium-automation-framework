package com.enterprise.framework.enums;

/**
 * Defines HOW to wait for an element before interacting with it.
 *
 * INTERN NOTE: NEVER use Thread.sleep(). Always pass a WaitStrategy.
 *
 * CLICKABLE -> element is visible AND enabled   (use for buttons/links)
 * VISIBLE   -> element is displayed on screen   (use for text/images)
 * PRESENCE  -> element exists in DOM (even hidden) (use for hidden fields)
 * NONE      -> no wait at all                   (use only if already loaded)
 */
public enum WaitStrategy {
    CLICKABLE,
    VISIBLE,
    PRESENCE,
    NONE
}
