package com.lld.patterns.behavioral.mediator;

/**
 * Interface representing the Mediator in a chat room setting.
 */
public interface IChatMediator {
    void sendMessage(String message, ChatUser from);
    void addUser(ChatUser user);
}
