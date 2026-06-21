package com.lld.patterns.structural.bridge;

/**
 * Record holding sending options like retries and timeout settings.
 */
public record SendOptions(int retries, int timeoutMs) {}
