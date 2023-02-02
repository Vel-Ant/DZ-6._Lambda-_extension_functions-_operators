package ru.netology

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }

    @Test
    fun editMessageTestTrue() {
        ChatService.addMessage(1, Message(0,"How are you?"))

        val result = ChatService.editMessage(1, 1, "How are you, DUDE?")

        assertEquals(true, result)
    }

    @Test
    fun editMessageTestFalse() {
        ChatService.addMessage(1, Message(0,"How are you?"))

        val result = ChatService.editMessage(1, 2, "How are you, DUDE?")

        assertEquals(false, result)
    }

    @Test
    fun readMessageTestTrue() {
        ChatService.addMessage(1, Message(0,"How are you?"))

        val result = ChatService.readMessage(1, 1)

        assertEquals(true, result)
    }

    @Test
    fun readMessageTestFalse() {
        ChatService.addMessage(1, Message(0,"How are you?"))

        val result = ChatService.readMessage(1, 5)

        assertEquals(false, result)
    }

    @Test
    fun getChatsTest() {
        ChatService.addMessage(1, Message(0,"How are you?"))

        val result = ChatService.getChats()

        assertEquals("1=How are you?", result)
    }

    @Test
    fun getMessagesTestFound() {
        ChatService.addMessage(1, Message(0,"How are you?"))

        val result = ChatService.getMessages(1)

        assertEquals("How are you?", result)
    }

    @Test
    fun getMessagesTestMessageNotFound() {
        ChatService.addMessage(1, Message(0,"How are you?"))

        val result = ChatService.getMessages(5)

        assertEquals("Messages not found", result)
    }

    @Test
    fun getUnreadChatsCountTest() {
        ChatService.addMessage(1, Message(0,"Hi"))
        ChatService.addMessage(1, Message(0,"How are you?"))
        ChatService.addMessage(1, Message(0,"What are you doing?"))
        ChatService.addMessage(2, Message(0,"Look this!"))

        val result = ChatService.getUnreadChatsCount()

        assertEquals(2, result)
    }

    @Test
    fun getUnreadChatsCountNull() {
        ChatService.addMessage(1, Message(0,"Hi"))
        ChatService.addMessage(2, Message(0,"Look this!"))
        ChatService.readMessage(1, 1)
        ChatService.readMessage(2, 2)

        val result = ChatService.getUnreadChatsCount()

        assertEquals(0, result)
    }

    @Test
    fun getUnreadMessageChatTest() {
        ChatService.addMessage(1, Message(0,"Hi"))
        ChatService.addMessage(1, Message(0,"How are you?"))

        val result = ChatService.getUnreadMessageChat(1)

        assertEquals("Hi\nHow are you?", result)
    }

    @Test
    fun getUnreadMessageChatTestMessageNotFound() {
        ChatService.addMessage(1, Message(0,"Hi"))
        ChatService.addMessage(1, Message(0,"How are you?"))
        ChatService.readMessage(1, 1)
        ChatService.readMessage(1, 2)

        val result = ChatService.getUnreadMessageChat(1)

        assertEquals("", result)
    }

    @Test
    fun getUnreadMessagesCountTest() {
        ChatService.addMessage(1, Message(0,"Hi"))
        ChatService.addMessage(1, Message(0,"How are you?"))
        ChatService.addMessage(1, Message(0,"What are you doing?"))
        ChatService.addMessage(2, Message(0,"Look this!"))

        val result = ChatService.getUnreadMessagesCount()

        assertEquals("1=3\n2=1", result)
    }

    @Test
    fun getUnreadMessagesChatCountTest() {
        ChatService.addMessage(1, Message(0,"Hi"))
        ChatService.addMessage(1, Message(0,"How are you?"))
        ChatService.addMessage(1, Message(0,"What are you doing?"))

        val result = ChatService.getUnreadMessagesChatCount(1)

        assertEquals(3, result)
    }

    @Test
    fun getLastMessagesTest() {
        ChatService.addMessage(1, Message(0,"Hi"))
        ChatService.addMessage(1, Message(0,"How are you?"))
        ChatService.addMessage(1, Message(0,"What are you doing?"))
        ChatService.addMessage(2, Message(0,"Look this!"))

        val result = ChatService.getLastMessages()

        assertEquals("What are you doing?\nLook this!", result)
    }

    @Test
    fun getLastMessagesTestMessageNotFound() {

        val result = ChatService.getLastMessages()

        assertEquals("", result)
    }

    @Test
    fun deleteMessageTestTrue() {
        ChatService.addMessage(1, Message(0,"Hi"))

        val result = ChatService.deleteMessage(1, 1)

        assertEquals(true, result)
    }

    @Test
    fun deleteMessageTestFalse() {
        ChatService.addMessage(1, Message(0,"Hi"))

        val result = ChatService.deleteMessage(1, 2)

        assertEquals(false, result)
    }

    @Test
    fun deleteChatTrue() {
        ChatService.addMessage(1, Message(0,"Hi"))

        val result = ChatService.deleteChat(1)

        assertEquals(true, result)
    }

    @Test
    fun deleteChatFalse() {
        ChatService.addMessage(1, Message(0,"Hi"))

        val result = ChatService.deleteChat(3)

        assertEquals(false, result)
    }

    @Test
    fun restoreMessageTrue() {
        ChatService.addMessage(1, Message(0,"Hi"))
        ChatService.addMessage(1, Message(0,"How are you?"))
        ChatService.deleteMessage(1, 1)

        val result = ChatService.restoreMessage(1, 1)

        assertEquals(true, result)
    }

    @Test
    fun restoreMessageFalse() {
        ChatService.addMessage(1, Message(0,"Hi"))
        ChatService.deleteMessage(1, 1)

        val result = ChatService.restoreMessage(1, 1)

        assertEquals(false, result)
    }
}