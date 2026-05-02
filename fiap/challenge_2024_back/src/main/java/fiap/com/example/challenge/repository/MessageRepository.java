package fiap.com.example.challenge.repository;

import fiap.com.example.challenge.model.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

}
