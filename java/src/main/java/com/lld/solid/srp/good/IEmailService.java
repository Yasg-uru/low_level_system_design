package com.lld.solid.srp.good;

/**
 * Responsibility: Send emails
 */
public interface IEmailService {
    void send(String to, String subject, String body);
}
