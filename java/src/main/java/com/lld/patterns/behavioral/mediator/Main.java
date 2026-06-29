package com.lld.patterns.behavioral.mediator;

/**
 * Driver class demonstrating the Mediator pattern in Java.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("=== Mediator Design Pattern: Chat Room Demo   ===");
        System.out.println("=================================================\n");

        // Create the central mediator
        ChatMediator chatRoom = new ChatMediator();

        // Create users
        ChatUser yash = new ChatUser("Yash", chatRoom);
        ChatUser rohan = new ChatUser("Rohan", chatRoom);
        ChatUser ankit = new ChatUser("Ankit", chatRoom);

        // Register users to the mediator
        chatRoom.addUser(yash);
        chatRoom.addUser(rohan);
        chatRoom.addUser(ankit);

        // Send messages
        yash.send("Hey everyone, how are you?");
        rohan.send("Doing great! Working on design patterns.");
    }
}
