package project.diary.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String user_nickname;
    private String user_password;

}