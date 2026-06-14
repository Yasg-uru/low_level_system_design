package com.lld.solid.srp.good;

public interface ILogger {
    void log(String message);
    void error(String message, Exception e);
}
