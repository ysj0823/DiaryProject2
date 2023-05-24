package project.diary.controller;

import project.diary.domain.user.User;
import project.diary.domain.user.UserRepository;

import project.diary.dto.user.*;
import project.diary.infra.jwt.JwtFactory;
import project.diary.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserRepository userRepository;

    private final UserService userService;
    private final JwtFactory jwtFactory;


    // 회원가입
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<UserResponseDto>> join(@RequestBody UserRequestDto userRequestDto) {
        ResponseEntity<List<UserResponseDto>> result;

        User user = userService.join(userRequestDto);

        if (user == null) {
            result = new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            List<UserResponseDto> userResponseDtoList = new ArrayList<>();
            userResponseDtoList.add(new UserResponseDto(user));
            result = ResponseEntity.ok(userResponseDtoList);
        }

        return result;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) throws Exception {
        // 유저아이디와 비밀번호가 없을 경우
        if (StringUtils.isEmpty(dto.getUser_id()) || StringUtils.isEmpty(dto.getUser_password())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 사용자 정보 가져오기
        User entity = userRepository.findByUser_id(dto.getUser_id())
                .orElseThrow(() -> new Exception("존재하지 않는 유저 정보 입니다."));

        // 비밀번호 일치 확인
        if (!entity.getUser_password().equals(dto.getUser_password())) {
            throw new Exception("잘못된 비밀번호 입니다 확인후 로그인해주세요.");
        }

        // 토큰 생성
        String jwtToken = jwtFactory.generateAccessToken(
                entity.getUser_id(),
                entity.getUser_nickname(),
                entity.getUser_password()
        );

        // 응답 DTO 생성
        LoginResponseDTO responseDTO = LoginResponseDTO.builder()
                .jwtToken(jwtToken)
                .user_id(entity.getUser_id())
                .user_nickname(entity.getUser_nickname())
                .user_password(entity.getUser_password())
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/test")
    private void test(HttpSession httpSession) {
        UserDecodeJWTDTO user = (UserDecodeJWTDTO) httpSession.getAttribute("USER");
        System.out.printf("user");
    }


    // 회원정보 수정
    @PatchMapping("/{user_id}")
    public void userUpdate(@PathVariable String user_id, @RequestBody UserUpdateRequestDto userUpdateRequestDto, @RequestHeader("Authorization") String token) throws Exception {
        // 토큰의 유효성 검사
        UserDecodeJWTDTO decodedToken = jwtFactory.decodeJwt(token);
        if (decodedToken == null) {
            throw new Exception("유효하지 않은 토큰입니다.");
        }

        // 토큰에서 추출한 정보를 사용하여 사용자 정보 업데이트
        userService.userUpdate(user_id, userUpdateRequestDto);
    }


    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // 토큰 무효화
        String token = request.getHeader(JwtFactory.HEADER_ACCESS_TOKEN);
        if (token != null) {
            // 토큰 무효화
            jwtFactory.invalidateToken(token, response);

            return ResponseEntity.ok("로그아웃되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("토큰이 존재하지 않습니다.");
        }
    }

    // 회원탈퇴
    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable String user_id) {
        userService.deleteUser(user_id);
        return ResponseEntity.ok().build();
    }

    // 마이페이지
    @GetMapping("/mypage/{user_id}")
    public ResponseEntity<UserResponseDto> myPage(@PathVariable String user_id) throws Exception {
        UserResponseDto user = userService.myPage(user_id);
        return ResponseEntity.ok(user);
    }

}
