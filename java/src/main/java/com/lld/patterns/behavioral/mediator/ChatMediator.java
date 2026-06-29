package com.lld.patterns.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Mediator implementation coordinating communication between users.
 */
public class ChatMediator implements IChatMediator {
    private final List<ChatUser> users = new ArrayList<>();

    @Override
    public void addUser(ChatUser user) {
        if (!users.contains(user)) {
            users.add(user);
        }
    }

    @Override
    public void sendMessage(String message, ChatUser from) {
        for (ChatUser user : users) {
            // Broadcast to everyone EXCEPT the sender
            if (user != from) {
                user.receive(message, from.getName());
            }
        }
    }

    public List<ChatUser> getUsers() {
        return new ArrayList<>(users);
    }
}
