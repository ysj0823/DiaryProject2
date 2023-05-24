package project.diary.dto.user;

import project.diary.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    private int userId;
    private String userNickname;
    private String userPassword;

    public UserRequestDto(User entity) {
        this.userId = entity.getUserId();
        this.userNickname = entity.getUserNickname();
        this.userPassword = entity.getUserPassword();

    }

}