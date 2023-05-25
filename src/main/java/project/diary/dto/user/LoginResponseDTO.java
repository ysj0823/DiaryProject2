package project.diary.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private String jwtToken;
    private int userId;
    private String userNickname;
    private String userPassword;
    private String userLoginId;
}