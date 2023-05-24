package project.diary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.diary.domain.diary.Diary;
import project.diary.domain.diary.DiaryRepository;
import project.diary.dto.diary.DiaryRequestDTO;
import project.diary.dto.diary.DiaryResponseDTO;
import project.diary.dto.diary.DiaryUpdateRequestDTO;

import java.util.Optional;

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

    // 이미 있는 데이터 조회
    @Transactional(readOnly = true)
    public  DiaryResponseDTO diaryPage(int diaryId) throws Exception {
        Diary diary = diaryRepository.findByDiaryId(diaryId)
                .orElseThrow(() -> new Exception("존재하지 않는 일기 정보 입니다."));
        return new DiaryResponseDTO(diary);
    }
}

