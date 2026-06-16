package com.lld.patterns.creational.prototype;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete Prototype that supports deep copying and fluent builders.
 */
public class EmailTemplate implements IPrototype<EmailTemplate> {
    private String subject;
    private String htmlBody;
    private String textBody;
    private String fromAddress;
    private String replyTo;
    private Map<String, String> headers;
    private List<EmailAttachment> attachments;
    private Map<String, Object> metadata;
    private Date createdAt;
    private int version;

    public EmailTemplate(String subject, String htmlBody, String fromAddress, Map<String, Object> metadata) {
        this.subject = subject;
        this.htmlBody = htmlBody;
        this.textBody = stripHtml(htmlBody);
        this.fromAddress = fromAddress;
        this.metadata = metadata != null ? new HashMap<>(metadata) : new HashMap<>();
        this.createdAt = new Date();
        this.version = 1;
        this.headers = new HashMap<>();
        this.attachments = new ArrayList<>();
    }

    /**
     * Copy constructor used for explicit deep copying.
     */
    private EmailTemplate(EmailTemplate other) {
        this.subject = other.subject;
        this.htmlBody = other.htmlBody;
        this.textBody = other.textBody;
        this.fromAddress = other.fromAddress;
        this.replyTo = other.replyTo;
        
        // Deep copy headers Map
        if (other.headers != null) {
            this.headers = new HashMap<>(other.headers);
        } else {
            this.headers = new HashMap<>();
        }
        
        // Deep copy attachments List
        if (other.attachments != null) {
            this.attachments = new ArrayList<>();
            for (EmailAttachment attachment : other.attachments) {
                this.attachments.add(attachment.cloneAttachment());
            }
        } else {
            this.attachments = new ArrayList<>();
        }
        
        // Deep copy metadata Map
        if (other.metadata != null) {
            this.metadata = new HashMap<>();
            for (Map.Entry<String, Object> entry : other.metadata.entrySet()) {
                this.metadata.put(entry.getKey(), entry.getValue());
            }
        } else {
            this.metadata = new HashMap<>();
        }

        this.createdAt = new Date(other.createdAt.getTime());
        this.version = other.version;
    }

    @Override
    public EmailTemplate clonePrototype() {
        return new EmailTemplate(this);
    }

    // Fluent API mutation step methods
    public EmailTemplate withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailTemplate withBody(String htmlBody, String textBody) {
        this.htmlBody = htmlBody;
        this.textBody = textBody != null ? textBody : stripHtml(htmlBody);
        return this;
    }

    public EmailTemplate withAttachments(List<EmailAttachment> newAttachments) {
        if (this.attachments == null) {
            this.attachments = new ArrayList<>();
        }
        this.attachments.addAll(newAttachments);
        return this;
    }

    public EmailTemplate withHeader(String key, String value) {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        }
        this.headers.put(key, value);
        return this;
    }

    public EmailTemplate bumpVersion() {
        this.version += 1;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public String getTextBody() {
        return textBody;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public List<EmailAttachment> getAttachments() {
        return attachments;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public int getVersion() {
        return version;
    }

    private String stripHtml(String html) {
        if (html == null) return "";
        return html.replaceAll("<[^>]*>", "");
    }
}
