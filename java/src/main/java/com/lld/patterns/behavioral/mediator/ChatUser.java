package com.lld.patterns.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Colleague class representing a participant in the chat.
 */
public class ChatUser {
    private final String name;
    private final IChatMediator mediator;
    private final List<String> receivedMessages = new ArrayList<>();

    public ChatUser(String name, IChatMediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    public String getName() {
        return name;
    }

    public void send(String message) {
        System.out.println("\n[" + name + "] sending: \"" + message + "\"");
        mediator.sendMessage(message, this);
    }

    public void receive(String message, String from) {
        String logEntry = "--> [" + name + "] received from [" + from + "]: \"" + message + "\"";
        System.out.println("   " + logEntry);
        receivedMessages.add(logEntry);
    }

    public List<String> getReceivedMessages() {
        return new ArrayList<>(receivedMessages);
    }

    public void clearMessages() {
        receivedMessages.clear();
    }
}
