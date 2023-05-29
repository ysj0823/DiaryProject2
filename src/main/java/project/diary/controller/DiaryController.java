package project.diary.controller;

import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.diary.domain.advice.AdviceRepository;
import project.diary.domain.diary.Diary;
import project.diary.domain.diary.DiaryRepository;
import project.diary.domain.user.User;
import project.diary.dto.diary.DiaryRequestDTO;
import project.diary.dto.diary.DiaryResponseDTO;
import project.diary.dto.diary.DiaryUpdateRequestDTO;
import project.diary.dto.user.UserDecodeJWTDTO;
import project.diary.dto.user.UserRequestDto;
import project.diary.dto.user.UserResponseDto;
import project.diary.dto.user.UserUpdateRequestDto;
import project.diary.infra.jwt.JwtFactory;
import project.diary.service.DiaryService;

import java.util.*;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryRepository diaryRepository;
    private final AdviceRepository adviceRepository;
    private final DiaryService diaryService;
    private final JwtFactory jwtFactory;


    @GetMapping("/token")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        try {
            // 토큰의 유효성 검사
            UserDecodeJWTDTO decodedToken = jwtFactory.decodeJwt(token);
            if (decodedToken != null) {
                // 토큰이 유효할 경우
                return ResponseEntity.ok().build();
            } else {
                // 토큰이 유효하지 않을 경우
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
            }
        } catch (Exception e) {
            // 예외가 발생한 경우
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생했습니다.");
        }
    }


    // 저장
    @PostMapping("/calendar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<DiaryResponseDTO>> save(@RequestBody DiaryRequestDTO diaryRequestDTO,
                                                       HttpServletRequest request) {

        UserDecodeJWTDTO user = (UserDecodeJWTDTO)request.getSession().getAttribute("USER");
        String userNickname = user.getUserNickname();
        log.info(userNickname);
        diaryRequestDTO.setDiaryUser(userNickname);
        ResponseEntity<List<DiaryResponseDTO>> result;


        Diary diary = diaryService.save(diaryRequestDTO);

        if (diary == null) {
            result = new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            List<DiaryResponseDTO> diaryResponseDTOArrayList = new ArrayList<>();
            diaryResponseDTOArrayList.add(new DiaryResponseDTO(diary));
            result = ResponseEntity.ok(diaryResponseDTOArrayList);
        }

        return result;
    }

    // 수정
    @PatchMapping("/calendar/{diaryId}")
    public void diaryUpdate(@PathVariable int diaryId, @RequestBody DiaryUpdateRequestDTO diaryUpdateRequestDTO, @RequestHeader("Authorization") String token) throws Exception {
        // 토큰의 유효성 검사
        UserDecodeJWTDTO decodedToken = jwtFactory.decodeJwt(token);
        if (decodedToken == null) {
            throw new Exception("유효하지 않은 토큰입니다.");
        }

        // 토큰에서 추출한 정보를 사용하여 내용 수정 업데이트
        diaryService.diaryUpdate(diaryId, diaryUpdateRequestDTO);
    }

    // 삭제
    @DeleteMapping("/calendar/{diaryId}")
    public ResponseEntity<?> deleteDiary(@PathVariable int diaryId) {
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.ok().build();
    }

    // 이미 있는 데이터 조회
    @GetMapping("/calendar/{diaryId}")
    public ResponseEntity<DiaryResponseDTO> diaryPage(@PathVariable int diaryId) throws Exception {
        DiaryResponseDTO diary = diaryService.diaryPage(diaryId);
        return ResponseEntity.ok(diary);
    }


    // 캘린더 조회 (한 달 치 일기)
    @GetMapping("/calendar/{year_month}")
    public ResponseEntity<List<DiaryResponseDTO>> getCalendar(@PathVariable String year_month) throws Exception{
        List<DiaryResponseDTO> diaryResponseDTOList = diaryService.findListByDate(year_month);
        return ResponseEntity.ok(diaryResponseDTOList);
    }


    // 특정 날짜 일기 조회 -> 날짜로 조회
    @GetMapping("/calendar/{date}")
    public ResponseEntity<DiaryResponseDTO> getDiary(@PathVariable String date) throws Exception{
        DiaryResponseDTO diaryResponseDTO = diaryService.findByDate(date);
        return ResponseEntity.ok(diaryResponseDTO);
    }


//    // 감정 통계 데이터
//    // model 에 담아서 전달
//    @GetMapping("/emotion/{date}")
//    public void getStatistics(@PathVariable String date, Model model) {
//
//        // 8가지 감정 key : value
//        Map<String, Integer> emotionsData = diaryService.getEmotion(date);
//
//        // emotionData 오름차순 정렬
//        List<Map.Entry<String, Integer>> entryList =
//                new ArrayList<Map.Entry<String, Integer>>(emotionsData.entrySet());
//
//        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
//            @Override
//            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//                return o1.getValue().compareTo(o2.getValue());
//            }
//        });
//
//        // 가장 적은 감정
//        String leastEmotion = emotionsData.keySet().iterator().next();
//
//        // 가장 많은 감정
//        Iterator<String> iterator = emotionsData.keySet().iterator();
//        String mostEmotion = "";
//        while (iterator.hasNext()) {
//            mostEmotion = iterator.next();
//        }
//
//        // 조언
//        List<String> adviceData = adviceRepository.findByAdviceEmotion(mostEmotion);
//
//        model.addAttribute("emotionsData", emotionsData);
//        model.addAttribute("mostEmotion", mostEmotion);
//        model.addAttribute("leastEmotion", leastEmotion);
//        model.addAttribute("adviceData", adviceData);
//    }

}