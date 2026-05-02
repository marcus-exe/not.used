package fiap.com.example.challenge.service;


import fiap.com.example.challenge.model.chat.Chat;
import fiap.com.example.challenge.model.chat.ChatStatus;
import fiap.com.example.challenge.model.chat.Message;
import fiap.com.example.challenge.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


    public Chat createChat(UUID employeeId, LocalDateTime startTimestamp, ChatStatus status) {
        Chat chat = new Chat();
        chat.setId(UUID.randomUUID());  
        chat.setEmployeeId(employeeId);
        chat.setStartTimestamp(startTimestamp);
        chat.setStatus(status);
        return chatRepository.save(chat);
    }

    public Chat addMessageToChat(UUID chatId, Message message) {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            chat.addMessage(message);
            chatRepository.save(chat);
            return chat;
        } else {
            throw new RuntimeException("Chat not found with id: " + chatId);
        }
    }

    public Optional<Chat> getChatById(UUID chatId) {
        return chatRepository.findById(chatId);
    }

    public List<Chat> getChatsByEmployeeId(UUID employeeId) {
        return chatRepository.findByEmployeeId(employeeId);
    }

    public List<Chat> getChatsByEmployeeIdAndStatus(UUID employeeId, ChatStatus status) {
        return chatRepository.findByEmployeeIdAndStatus(employeeId, status);
    }

    public List<Chat> getChatsByStartTimestampAfter(LocalDateTime startTimestamp) {
        return chatRepository.findByStartTimestampAfter(startTimestamp);
    }

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public Chat saveChat(Chat chat) {
        return chatRepository.save(chat);
    }

}
