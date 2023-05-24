package project.diary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.diary.domain.diary.Diary;
import project.diary.domain.diary.DiaryRepository;
import project.diary.dto.diary.DiaryRequestDTO;
import project.diary.dto.diary.DiaryUpdateRequestDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    // 저장
    public Diary save(DiaryRequestDTO diaryRequestDTO) {

        return diaryRepository.save(Diary.builder()
                .diaryId(diaryRequestDTO.getDiaryId())
                .diaryUser(diaryRequestDTO.getDiaryUser())
                .diaryWeather(diaryRequestDTO.getDiaryWeather())
                .diaryEmotion(diaryRequestDTO.getDiaryEmotion())
                .diaryTitle(diaryRequestDTO.getDiaryTitle())
                .diaryContent(diaryRequestDTO.getDiaryContent())
                .build());
    }

    // 수정
    @Transactional
    public void diaryUpdate(int diaryId, DiaryUpdateRequestDTO diaryUpdateRequestDTO) throws Exception {

        Diary diary = diaryRepository.findByDiaryId(diaryId)
                .orElseThrow(() -> new Exception("존재하지 않는 다이어리 정보 입니다."));

        diary.diaryUpdate(diaryUpdateRequestDTO);
    }

    // 삭제
    public void deleteDiary(int diaryId) {
        diaryRepository.deleteById(Long.valueOf(diaryId));
    }
}

