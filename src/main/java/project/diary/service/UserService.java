package project.diary.service;

import project.diary.domain.user.User;
import project.diary.domain.user.UserRepository;
import project.diary.dto.user.LoginRequestDTO;
import project.diary.dto.user.UserRequestDto;

import project.diary.dto.user.UserResponseDto;
import project.diary.dto.user.UserUpdateRequestDto;
import project.diary.infra.jwt.JwtFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtFactory jwtFactory;

    // 회원가입
    public User join(UserRequestDto userRequestDto) {
        Boolean existed = userRepository.existsByUser_id(userRequestDto.getUser_id());

        if(existed) {
            return null;
        }

        return userRepository.save(User.builder()
                .user_id(userRequestDto.getUser_id())
                .user_nickname(userRequestDto.getUser_nickname())
                .user_password(userRequestDto.getUser_password())
                .build());

    }

    // 로그인
    public String login(LoginRequestDTO userRequestDto) throws Exception {

        // Get User Info
        User entity = userRepository.findByUser_id(userRequestDto.getUser_id())
                .orElseThrow(() -> new Exception("존재하지 않는 유저 정보 입니다."));


        // Check User password
        if(!entity.getUser_password().equals(userRequestDto.getUser_password())){
            throw new Exception("잘못된 비밀번호 입니다 확인후 로그인해주세요.");
        }

        return jwtFactory.generateAccessToken(
                entity.getUser_id(),
                entity.getUser_nickname(),
                entity.getUser_password()
        );

    }

    private void authenticate(String email, String password) {

    }


    // 사용자 정보 수정
    @Transactional
    public void userUpdate(String user_id, UserUpdateRequestDto userUpdateRequestDto) throws Exception {

        User user = userRepository.findByUser_id(user_id)
                .orElseThrow(() -> new Exception("존재하지 않는 유저 정보 입니다."));

        user.userUpdate(userUpdateRequestDto);
    }

    // 로그아웃
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(JwtFactory.HEADER_ACCESS_TOKEN);
        if (token != null) {
            jwtFactory.invalidateToken(token, response);
        }
    }

    // 회원탈퇴
    public void deleteUser(String user_id) {
        userRepository.deleteById(Long.valueOf(user_id));
    }

    // 마이페이지
    @Transactional(readOnly = true)
    public UserResponseDto myPage(String user_id) throws Exception {
        User user = userRepository.findByUser_id(user_id)
                .orElseThrow(() -> new Exception("존재하지 않는 유저 정보 입니다."));
        return new UserResponseDto(user);
    }

}
