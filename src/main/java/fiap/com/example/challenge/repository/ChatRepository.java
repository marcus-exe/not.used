package fiap.com.example.challenge.repository;


import fiap.com.example.challenge.model.chat.Chat;
import fiap.com.example.challenge.model.chat.ChatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    List<Chat> findByEmployeeId(UUID employeeId);

    List<Chat> findByEmployeeIdAndStatus(UUID userId, ChatStatus status);

    List<Chat> findByStartTimestampAfter(LocalDateTime startTimestamp);

}
