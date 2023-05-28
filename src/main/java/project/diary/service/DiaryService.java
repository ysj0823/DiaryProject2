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

import java.util.*;

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



    // 특정 날짜 일기 조회
    @Transactional(readOnly = true)
    public DiaryResponseDTO findByDate(String date) throws Exception {
        Diary diary = diaryRepository.findByDiaryDate(date)
                .orElseThrow(() -> new Exception(" 해당 날짜에 작성한 일기가 없습니다."));
        return new DiaryResponseDTO(diary);
    }


    // 한 달 치 일기 조회
    @Transactional(readOnly = true)
    public List<DiaryResponseDTO> findListByDate(String date) throws Exception {
        List<Diary> diaryList = diaryRepository.findListByDate(date)
                .orElseThrow(() -> new Exception("해당 날짜에 작성한 일기 목록이 없습니다."));

        // DTO 로 변환
        List<DiaryResponseDTO> diaryResponseDTOList = new ArrayList<>();
        for (Diary diary : diaryList) {
            DiaryResponseDTO diaryResponseDTO = new DiaryResponseDTO(diary);
            diaryResponseDTOList.add(diaryResponseDTO);
        }

        return diaryResponseDTOList;
    }


    /*
    행복, 오열, 슬픔, 설렘, 두려움, 즐거움, 그냥저냥, 분노
    */
    // 한 달 치 통계 (감정 : 백분율)
    @Transactional(readOnly = true)
    public Map<String, Integer> getEmotion(String date) {
        Map<String, Integer> emotionData = new HashMap<>();

        int e01 = diaryRepository.findCountByEmotion("E01", date);
        int e02 = diaryRepository.findCountByEmotion("E02", date);
        int e03 = diaryRepository.findCountByEmotion("E03", date);
        int e04 = diaryRepository.findCountByEmotion("E04", date);
        int e05 = diaryRepository.findCountByEmotion("E05", date);
        int e06 = diaryRepository.findCountByEmotion("E06", date);
        int e07 = diaryRepository.findCountByEmotion("E07", date);
        int e08 = diaryRepository.findCountByEmotion("E08", date);

        emotionData.put("행복", e01);
        emotionData.put("오열", e02);
        emotionData.put("슬픔", e03);
        emotionData.put("설렘", e04);
        emotionData.put("두려움", e05);
        emotionData.put("즐거움", e06);
        emotionData.put("그냥저냥", e07);
        emotionData.put("분노", e08);

        return emotionData;
    }

}
