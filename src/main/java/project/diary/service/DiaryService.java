package project.diary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.diary.domain.diary.Diary;
import project.diary.domain.diary.DiaryRepository;
import project.diary.domain.user.User;
import project.diary.dto.diary.DiaryRequestDTO;
import project.diary.dto.diary.DiaryUpdateRequestDTO;
import project.diary.dto.user.UserRequestDto;
import project.diary.dto.user.UserUpdateRequestDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    // 저장
    public Diary save(DiaryRequestDTO diaryRequestDTO) {

        return diaryRepository.save(Diary.builder()
                .diary_id(diaryRequestDTO.getDiary_id())
                .diary_user(diaryRequestDTO.getDiary_user())
                .diary_weather(diaryRequestDTO.getDiary_weather())
                .diary_emotion(diaryRequestDTO.getDiary_emotion())
                .diary_title(diaryRequestDTO.getDiary_title())
                .diary_content(diaryRequestDTO.getDiary_content())
                .build());
    }

    // 수정
    @Transactional
    public void diaryUpdate(int diary_id, DiaryUpdateRequestDTO diaryUpdateRequestDTO) throws Exception {

        Diary diary = diaryRepository.findByDiary_id(diary_id)
                .orElseThrow(() -> new Exception("존재하지 않는 다이어리 정보 입니다."));

        diary.diaryUpdate(diaryUpdateRequestDTO);
    }

    // 삭제
    public void deleteDiary(int diary_id) {
        diaryRepository.deleteById(Long.valueOf(diary_id));
    }
}

