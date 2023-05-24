package project.diary.infra.jwt;


import project.diary.dto.user.UserDecodeJWTDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFactory {


    public static final String REFRESH_TOKEN = "refreshToken";

    public static final String HEADER_ACCESS_TOKEN = "Authorization";

    private static String secret = "[B@2893de87";

    private static String DOMAIN_URL = "http://capstone.com";

    private static String LOGIN_ID = "LOGIN_ID";

    private static String USER_NICKNAME = "USER_NICKNAME";

    private static String USER_PASSWORD = "USER_PASSWORD";

    private static String USER_ID = "USER_ID";

    private Algorithm generateSign() {
        return Algorithm.HMAC256("jwtProperty.getKey()");
    }


    public String generateAccessToken(String user_id, String user_nickname, String user_password) {

        return JWT.create()
                .withIssuer(DOMAIN_URL)
                .withIssuedAt(Date.from(new Date().toInstant()))
                .withExpiresAt(Date.from(LocalDateTime.now().plusHours(30).atZone(ZoneId.systemDefault()).toInstant()))
                .withClaim(USER_ID, String.valueOf(user_id))
                .withClaim(USER_NICKNAME, user_nickname)
                .withClaim(USER_PASSWORD, user_password)
                .sign(generateSign());
    }

    /**
     * Decode JWT Token
     *
     * @param token : req token
     * @return : AuthUser
     */

    public UserDecodeJWTDTO decodeJwt(final String token) throws Exception {

        DecodedJWT decodedJWT = isValidToken(token)
                .orElseThrow(() -> new Exception());

        String user_id = decodedJWT.getClaim(USER_ID).asString();
        String user_nickname = decodedJWT.getClaim(USER_NICKNAME).asString();
        String user_password = decodedJWT.getClaim(USER_PASSWORD).asString();


        return UserDecodeJWTDTO.builder()
                .user_id(user_id)
                .user_nickname(user_nickname)
                .user_password(user_password)
                .build();
    }

    /**
     * Validation Token
     *
     * @param token : req Token
     * @return : JWT
     */
    private Optional<DecodedJWT> isValidToken(final String token) throws Exception {

        Algorithm algorithm = this.generateSign();
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = null;

        try {
            decodedJWT = verifier.verify(token);
        } catch (Exception e) {
            throw new Exception("잘못된 정보의 TOKEN 입니다.");
        }

        return Optional.of(decodedJWT);
    }


    private Set<String> validTokens = new HashSet<>();

    public void invalidateToken(String token, HttpServletResponse response) {
        if (token != null) {
            // 현재 유효한 토큰 리스트에서 전달받은 토큰을 삭제합니다.
            validTokens.remove(token);

            // 토큰을 무효화하기 위해 클라이언트에게 빈 토큰 값을 전달합니다.
            String emptyToken = "";

            log.info("Token invalidated: {}", token);
        }
    }

    public void addValidToken(String token) {
        validTokens.add(token);
    }

    public void removeValidToken(String token) {
        validTokens.remove(token);
    }

    public Set<String> getValidTokens() {
        return validTokens;
    }
}
