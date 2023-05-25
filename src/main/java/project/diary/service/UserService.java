package project.diary.service;

import project.diary.domain.user.User;
import project.diary.domain.user.UserRepository;
import project.diary.dto.diary.DiaryUpdateRequestDTO;
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
        Boolean existed = userRepository.existsByUserLoginId(userRequestDto.getUserLoginId());

        if(existed) {
            return null;
        }

        return userRepository.save(User.builder()
                .userId(userRequestDto.getUserId())
                .userNickname(userRequestDto.getUserNickname())
                .userPassword(userRequestDto.getUserPassword())
                .userLoginId(userRequestDto.getUserLoginId())
                .build());

    }

    // 로그인
    public String login(LoginRequestDTO userRequestDto) throws Exception {

        // Get User Info
        User entity = userRepository.findByUserLoginId(userRequestDto.getUserLoginId())
                .orElseThrow(() -> new Exception("존재하지 않는 유저 정보 입니다."));


        // Check User password
        if(!entity.getUserPassword().equals(userRequestDto.getUserPassword())){
            throw new Exception("잘못된 비밀번호 입니다 확인후 로그인해주세요.");
        }

        return jwtFactory.generateAccessToken(
                entity.getUserId(),
                entity.getUserNickname(),
                entity.getUserPassword(),
                entity.getUserLoginId()
        );

    }

//    private void authenticate(String email, String password) {
//
//    }


    // 사용자 정보 수정
    @Transactional
    public void userUpdate(String userLoginId, UserUpdateRequestDto userUpdateRequestDto) throws Exception {

        User user = userRepository.findByUserLoginId(userLoginId)
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
    public void deleteUser(int userId) {
        userRepository.deleteById((long) userId);
    }

    // 마이페이지
    @Transactional(readOnly = true)
    public UserResponseDto myPage(String userLoginId) throws Exception {
        User user = userRepository.findByUserLoginId(userLoginId)
                .orElseThrow(() -> new Exception("존재하지 않는 유저 정보 입니다."));
        return new UserResponseDto(user);
    }

}
