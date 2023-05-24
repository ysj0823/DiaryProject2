package project.diary.domain.user;


import project.diary.dto.user.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class User {

    @Id
    private String user_id;
    private String user_nickname;
    private String user_password;


    @Builder
    public User(String user_id, String user_nickname, String user_password) {
        this.user_id = user_id;
        this.user_nickname = user_nickname;
        this.user_password = user_password;

    }

    public void userUpdate(UserUpdateRequestDto userUpdateRequestDto) {
        this.user_nickname = userUpdateRequestDto.getUser_nickname();
        this.user_password = userUpdateRequestDto.getUser_password();
    }

}