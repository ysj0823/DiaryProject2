package project.diary.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryRepository diaryRepository;
    private final DiaryService diaryService;
    private final JwtFactory jwtFactory;

    @GetMapping("/calendar")
    public ResponseEntity<List<DiaryResponseDTO>> getCalendar(@RequestHeader("Authorization") String token) throws Exception {
        // 토큰의 유효성 검사
        UserDecodeJWTDTO decodedToken = jwtFactory.decodeJwt(token);
        if (decodedToken == null) {
            // 토큰이 유효하지 않을 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 토큰에서 추출한 정보를 사용하여 캘린더 데이터 조회
        String userNickname = decodedToken.getUserNickname();
        List<Diary> diaries = diaryRepository.findByDiaryUser(userNickname);

        // 조회 결과를 DTO로 변환하여 반환
        List<DiaryResponseDTO> responseDTOs = diaries.stream()
                .map(DiaryResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
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
        DiaryService diaryService = new DiaryService(diaryRepository);
        DiaryResponseDTO diary = diaryService.diaryPage(diaryId);
        return ResponseEntity.ok(diary);
    }


}
