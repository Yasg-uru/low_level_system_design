package com.lld.patterns.creational.prototype;

import java.util.Arrays;

/**
 * Represents an email attachment. Must support deep copying of the data byte array.
 */
public class EmailAttachment {
    private final String filename;
    private final String contentType;
    private final byte[] data;

    public EmailAttachment(String filename, String contentType, byte[] data) {
        this.filename = filename;
        this.contentType = contentType;
        this.data = data;
    }

    public String getFilename() {
        return filename;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getData() {
        return data;
    }

    /**
     * Performs a deep copy of the attachment.
     */
    public EmailAttachment cloneAttachment() {
        return new EmailAttachment(
            this.filename,
            this.contentType,
            this.data != null ? Arrays.copyOf(this.data, this.data.length) : null
        );
    }
}
