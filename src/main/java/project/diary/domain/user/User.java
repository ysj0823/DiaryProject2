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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String userNickname;
    private String userPassword;
    private String userLoginId;


    @Builder
    public User(int userId, String userNickname, String userPassword, String userLoginId) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userPassword = userPassword;
        this.userLoginId = userLoginId;

    }

    public void userUpdate(UserUpdateRequestDto userUpdateRequestDto) {
        this.userNickname = userUpdateRequestDto.getUserNickname();
        this.userPassword = userUpdateRequestDto.getUserPassword();
    }

}