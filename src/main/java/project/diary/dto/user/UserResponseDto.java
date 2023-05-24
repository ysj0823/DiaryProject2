package project.diary.dto.user;

import project.diary.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private String user_id;
    private String user_nickname;
    private String user_password;

    public UserResponseDto(User entity) {
        this.user_id = entity.getUser_id();
        this.user_nickname = entity.getUser_nickname();
        this.user_password = entity.getUser_password();
    }
}