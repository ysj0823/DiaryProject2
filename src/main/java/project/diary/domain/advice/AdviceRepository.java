package project.diary.domain.advice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdviceRepository extends JpaRepository<Advice, Long> {

    @Query(value = "SELECT a.advice_content FROM advice a WHERE a.advice_emotion = :emotion"
            , nativeQuery = true)
    List<String> findByAdviceEmotion(@Param("emotion") String emotion);
}
