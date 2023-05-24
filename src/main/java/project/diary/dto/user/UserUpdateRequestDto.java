package project.diary.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String userNickname;
    private String userPassword;

}