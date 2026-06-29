package com.lld.patterns.behavioral;

import com.lld.patterns.behavioral.mediator.ChatMediator;
import com.lld.patterns.behavioral.mediator.ChatUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Mediator Design Pattern Tests")
class MediatorTest {

    private ChatMediator chatRoom;
    private ChatUser user1;
    private ChatUser user2;
    private ChatUser user3;

    @BeforeEach
    void setUp() {
        chatRoom = new ChatMediator();
        user1 = new ChatUser("Yash", chatRoom);
        user2 = new ChatUser("Rohan", chatRoom);
        user3 = new ChatUser("Ankit", chatRoom);

        chatRoom.addUser(user1);
        chatRoom.addUser(user2);
        chatRoom.addUser(user3);
    }

    @Test
    @DisplayName("Users should be successfully registered in mediator")
    void testUserRegistration() {
        assertEquals(3, chatRoom.getUsers().size());
        assertTrue(chatRoom.getUsers().contains(user1));
        assertTrue(chatRoom.getUsers().contains(user2));
        assertTrue(chatRoom.getUsers().contains(user3));
    }

    @Test
    @DisplayName("Message broadcast should reach all other users except the sender")
    void testMessageBroadcast() {
        user1.send("Hello Room!");

        // User1 is the sender, so they shouldn't receive their own message
        assertTrue(user1.getReceivedMessages().isEmpty());

        // User2 should receive the message from Yash
        List<String> user2Msgs = user2.getReceivedMessages();
        assertEquals(1, user2Msgs.size());
        assertTrue(user2Msgs.get(0).contains("received from [Yash]: \"Hello Room!\""));

        // User3 should receive the message from Yash
        List<String> user3Msgs = user3.getReceivedMessages();
        assertEquals(1, user3Msgs.size());
        assertTrue(user3Msgs.get(0).contains("received from [Yash]: \"Hello Room!\""));
    }

    @Test
    @DisplayName("Multiple messages should be routed correctly")
    void testMultipleMessages() {
        user1.send("Hey Rohan");
        user2.send("Hi Yash");

        // User 1 gets 1 message from User 2
        assertEquals(1, user1.getReceivedMessages().size());
        assertTrue(user1.getReceivedMessages().get(0).contains("received from [Rohan]: \"Hi Yash\""));

        // User 2 gets 1 message from User 1
        assertEquals(1, user2.getReceivedMessages().size());
        assertTrue(user2.getReceivedMessages().get(0).contains("received from [Yash]: \"Hey Rohan\""));

        // User 3 gets both messages
        assertEquals(2, user3.getReceivedMessages().size());
    }

    @Test
    @DisplayName("Unregistered users should not receive broadcasted messages")
    void testUnregisteredUserDoesNotReceiveMessages() {
        ChatUser unregisteredUser = new ChatUser("Stranger", chatRoom);
        // Note: unregisteredUser is NOT added to chatRoom

        user1.send("Secret Message");

        // Unregistered user should not have received anything
        assertTrue(unregisteredUser.getReceivedMessages().isEmpty());
    }
}
