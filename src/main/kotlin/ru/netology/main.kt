package ru.netology

data class Chat(
    val messages: MutableList<Message> = mutableListOf(),   // Список cообщений
)

data class Message(
    val message_id: Int,                                    // Идентификатор сообщения
    var text: String,                                       // Текст сообщения
    var messageRead: Boolean = false,                       // Сообщение прочитано
    var isDeleted: Boolean = false,                         // Сообщение невидимо
    val date: Long = System.currentTimeMillis()             // Время публикации сообщения в формате unixtime
)

object ChatService {

    private var chats = mutableMapOf<Int, Chat>()
    private var messageId = 0

    // Создаем сообщения
    fun addMessage(user_id: Int, message: Message) {
        chats.getOrPut(user_id) {Chat()}.messages += message.copy(message_id = ++messageId)
    }

    // Редактируем сообщение
    fun editMessage(user_id: Int, message_id: Int, text: String): Boolean {
        return chats[user_id]?.messages?.find { it.message_id == message_id }?.also { it.text = text } != null
    }

    // Читаем сообщение
    fun readMessage(user_id: Int, message_id: Int): Boolean {
        return chats[user_id]?.messages?.find { it.message_id == message_id }?.also { it.messageRead = true } != null
    }

    // Возвращаем список чатов с последним сообщением
    fun getChats() = chats.mapValues { it.value.messages.findLast{ message -> !message.isDeleted }?.text}.asSequence().joinToString("\n")

    // Возвращаем список сообщений чата
    fun getMessages(user_id: Int) = chats[user_id]?.messages?.joinToString("\n") {  it.text } ?: "Messages not found"


    // Показывает сколько чатов не прочитано
    fun getUnreadChatsCount() = chats.values.count { it.messages.find { message ->  !message.messageRead } != null}

    // Возвращаем список непрочитанных сообщений чата
    fun getUnreadMessageChat(user_id: Int) = chats[user_id]?.messages?.filter { !it.messageRead && !it.isDeleted}?.joinToString("\n") { it.text }

    // Показывает кол-во непрочитанных сообщений в каждом чате
    fun getUnreadMessagesCount() = chats.mapValues {it.value.messages.count{ message ->  !message.messageRead }}.asSequence().joinToString("\n")

    // Показывает кол-во непрочитанных сообщений чата
    fun getUnreadMessagesChatCount(user_id: Int) = chats[user_id]?.messages?.count{ !it.messageRead }

    // Возвращаем список последних сообщений
    fun getLastMessages() = chats.values.map { it.messages.findLast{message -> !message.isDeleted }?.text}.joinToString("\n")

    // Удаляем сообщение, если сообщение в чате одно, тогда удаляется весь чат
    fun deleteMessage(user_id: Int, message_id: Int): Boolean {
        return chats[user_id]?.messages?.find { it.message_id == message_id }?.
        also { if (chats[user_id]?.messages?.count() == 1) deleteChat(user_id) else  it.isDeleted = true } != null
    }

    // Удаляем чат
    fun deleteChat(user_id: Int) = chats.remove(user_id) != null

    // Восстанавливаем сообщение
    fun restoreMessage(user_id: Int, message_id: Int): Boolean {
        return chats[user_id]?.messages?.find { it.message_id == message_id }?.also { it.isDeleted = false } != null
    }

    fun clear() {
        chats = mutableMapOf()
        messageId = 0
    }

    override fun toString() = "${chats.forEach(::println)}"
}

fun main(){

    println("\nСоздаем сообщения")
    ChatService.addMessage(1, Message(0,"Hi"))
    ChatService.addMessage(1, Message(0,"How are you?"))
    ChatService.addMessage(1, Message(0,"What are you doing?"))
    ChatService.addMessage(2, Message(0,"Look this!"))
    println(ChatService)

    println("\nРедактируем сообщение")
    ChatService.editMessage(1, 2, "How are you, DUDE?")
    println(ChatService)

    println("\nЧитаем сообщение")
    ChatService.readMessage(1, 1)
    println(ChatService)

    println("\nВозвращаем список чатов с последним сообщением")
    println(ChatService.getChats())

    println("\nВозвращаем список сообщений чата")
    println(ChatService.getMessages(1))

    println("\nПоказывает сколько чатов не прочитано")
    println(ChatService.getUnreadChatsCount())

    println("\nВозвращаем список непрочитанных сообщений чата")
    println(ChatService.getUnreadMessageChat(1))

    println("\nПоказывает кол-во непрочитанных сообщений в каждом чате")
    println(ChatService.getUnreadMessagesCount())

    println("\nПоказывает кол-во непрочитанных сообщений чата")
    println(ChatService.getUnreadMessagesChatCount(1))

    println("\nВозвращаем список последних сообщений")
    println(ChatService.getLastMessages())

    println("\nУдаляем сообщение")
    ChatService.deleteMessage(1, 2)
    ChatService.deleteMessage(2, 4)
    println(ChatService)

    println("\nВосстанавливаем сообщение")
    ChatService.restoreMessage(1, 2)
    println(ChatService)
}

