package com.lld.patterns.creational.prototype;

import java.util.Map;

/**
 * Concrete implementation of the mailer, simulating SMTP delivery.
 */
public class SMTPMailer implements IMailer {
    @Override
    public void sendEmail(EmailTemplate template, String to, Map<String, Object> variables) {
        System.out.println("[SMTP Mailer] Dispatching message to: <" + to + ">");
        System.out.println("              Subject: \"" + template.getSubject() + "\" [v" + template.getVersion() + "]");
        System.out.println("              Payload HTML Length: " + template.getHtmlBody().length() + " bytes");
        if (variables != null && !variables.isEmpty()) {
            System.out.println("              Injected Context: " + variables);
        }
        System.out.println("----------------------------------------------------------------");
    }
}
