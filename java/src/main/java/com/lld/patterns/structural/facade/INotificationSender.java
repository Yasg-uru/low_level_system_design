package com.lld.patterns.structural.facade;

public interface INotificationSender {
    void send(String to, String subject, String body) throws Exception;
}
