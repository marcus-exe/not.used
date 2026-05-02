package fiap.com.example.challenge.dto;

import fiap.com.example.challenge.model.chat.SenderType;

import java.util.UUID;

public class MessageDTO {
    private UUID chatId;
    private SenderType senderType;
    private String content;

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public SenderType getSenderType() {
        return senderType;
    }

    public void setSenderType(SenderType senderType) {
        this.senderType = senderType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
