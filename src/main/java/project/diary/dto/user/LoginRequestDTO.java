package project.diary.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDTO {

    private String userPassword;
    private String userLoginId;

}