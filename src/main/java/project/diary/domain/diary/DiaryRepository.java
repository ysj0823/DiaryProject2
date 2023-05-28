package project.diary.domain.diary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {


    Optional<Diary> findByDiaryId(int diaryId);


    Optional<Diary> findByDiaryDate(@Param("date") String date);

    // 날짜로 일기 조회
    @Query(value = "SELECT * FROM diary d WHERE d.diary_date LIKE :date%", nativeQuery = true)
    Optional<List<Diary>> findListByDate(@Param("date") String date);

    // 날짜로 감정 개수 조회
    @Query(value = "SELECT COUNT(*) FROM diary d WHERE d.diary_date LIKE :date% " +
            "AND d.diary_emotion = :emotion", nativeQuery = true)
    int findCountByEmotion(@Param("emotion") String emotion, @Param("date") String date);
}