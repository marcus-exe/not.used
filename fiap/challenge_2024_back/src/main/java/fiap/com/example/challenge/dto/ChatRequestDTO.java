package fiap.com.example.challenge.dto;

import fiap.com.example.challenge.model.chat.ChatStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class ChatRequestDTO {
    private UUID employeeId;
    private LocalDateTime startTimestamp;
    private ChatStatus status;

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDateTime getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(LocalDateTime startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public ChatStatus getStatus() {
        return status;
    }

    public void setStatus(ChatStatus status) {
        this.status = status;
    }
}
