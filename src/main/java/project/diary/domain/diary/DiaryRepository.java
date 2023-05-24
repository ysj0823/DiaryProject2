package project.diary.domain.diary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import project.diary.domain.diary.Diary;

import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {


    Optional<Diary> findByDiary_id(@Param("diaryId") int diary_id);
}