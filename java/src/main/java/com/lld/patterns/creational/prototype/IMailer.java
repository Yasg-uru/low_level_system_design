package com.lld.patterns.creational.prototype;

import java.util.Map;

/**
 * Interface representing standard mailing service capabilities.
 */
public interface IMailer {
    /**
     * Dispatches an email message using the specified template and variables.
     */
    void sendEmail(EmailTemplate template, String to, Map<String, Object> variables);
}
