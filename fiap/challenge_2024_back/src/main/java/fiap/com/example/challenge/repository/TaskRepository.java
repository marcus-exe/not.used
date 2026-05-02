package fiap.com.example.challenge.repository;

import fiap.com.example.challenge.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {
    @Query(value = "SELECT resolution FROM Task WHERE LOWER(symptom) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(description) LIKE LOWER(CONCAT('%', :term, '%')) ORDER BY LENGTH(REPLACE(LOWER(symptom), LOWER(:term), '')) ASC, LENGTH(REPLACE(LOWER(description), LOWER(:term), '')) ASC LIMIT 3", nativeQuery = true)
    List<String> findTop3BySymptomOrDescription(@Param("term") String term);

    @Query(value = "SELECT resolution FROM Task ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<String> findRandomResolutions();
}
