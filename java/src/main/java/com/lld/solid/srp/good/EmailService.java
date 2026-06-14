package com.lld.solid.srp.good;

/**
 * Concrete implementation: Email sending
 */
public class EmailService implements IEmailService {

    @Override
    public void send(String to, String subject, String body) {
        String message = "To: " + to + "\nSubject: " + subject + "\n\n" + body;
        System.out.println("📧 Sending email:\n" + message);
    }
}
