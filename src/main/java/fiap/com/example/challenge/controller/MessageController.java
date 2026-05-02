package fiap.com.example.challenge.controller;

import fiap.com.example.challenge.dto.MessageDTO;
import fiap.com.example.challenge.model.chat.Chat;
import fiap.com.example.challenge.model.chat.Message;
import fiap.com.example.challenge.service.ChatService;
import fiap.com.example.challenge.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/messages")
public class MessageController {
    private final MessageService messageService;
    private final ChatService chatService;

    @Autowired
    public MessageController(MessageService messageService, ChatService chatService) {
        this.messageService = messageService;
        this.chatService = chatService;
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable UUID id) {
        Optional<Message> message = messageService.getMessageById(id);
        return message.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody MessageDTO messageDTO) {
        Chat chat = chatService.getChatById(messageDTO.getChatId())
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        Message message = new Message();
        message.setId(UUID.randomUUID());
        message.setChat(chat); // Link the message to the chat
        message.setSenderType(messageDTO.getSenderType());
        message.setContent(messageDTO.getContent());
        message.setTimestamp(LocalDateTime.now());

        Message savedMessage = messageService.saveMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable UUID id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

}
