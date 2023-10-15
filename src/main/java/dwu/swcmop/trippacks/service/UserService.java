package dwu.swcmop.trippacks.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dwu.swcmop.trippacks.config.BaseException;
import dwu.swcmop.trippacks.entity.User;
import dwu.swcmop.trippacks.config.jwt.JwtProperties;
import dwu.swcmop.trippacks.model.oauth.KakaoProfile;
import dwu.swcmop.trippacks.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

import static dwu.swcmop.trippacks.config.BaseResponseStatus.EMPTY_JWT;
import static dwu.swcmop.trippacks.config.BaseResponseStatus.INVALID_JWT;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository; //(1)


    public String saveUserAndGetToken(Long kakaoId, String kakaoProfileImg, String kakaoNickname, String kakaoEmail, String userRole) {
        User user = userRepository.findByKakaoEmail(kakaoEmail);

        if (user == null) {
            user = User.builder()
                    .kakaoId(kakaoId)
                    .kakaoProfileImg(kakaoProfileImg)
                    .kakaoNickname(kakaoNickname)
                    .kakaoEmail(kakaoEmail)
                    .userRole(userRole)
                    .build();

            userRepository.save(user);
        }

        return createToken(user); //(2)
    }

    public String createToken(User user) { //(2-1)

        //(2-2)
        String jwtToken = JWT.create()

                //(2-3)
                .withSubject(user.getKakaoEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))

                //(2-4)
                .withClaim("id", user.getUserCode())
                .withClaim("nickname", user.getKakaoNickname())

                //(2-5)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return jwtToken; //(2-6)
    }

    //(1-1)동의항목 가져오기
    public KakaoProfile findProfile(String token) {

        //(1-2) POST 방식으로 key=value 데이터 요청
        RestTemplate rt = new RestTemplate();

        //(1-3)HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //(1-5)HttpHeader 와 HttpBody 정보를 하나의 오브젝트에 담음
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        //(1-6) Http 요청 (POST 방식) 후, response 변수에 응답을 받음
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        //(1-7)JSON 응답을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;
    }

    public void deleteUser(Long id) throws BaseException {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                userRepository.delete(user.get());
            } else {
                throw new BaseException(EMPTY_JWT);
            }
        } catch (ExpiredJwtException exception) {
            // 예외가 발생한 경우에 대한 처리
            throw new BaseException(INVALID_JWT);
        }
    }

    public void deleteKakaoUser(Long id) throws BaseException {
        try {
            User user = userRepository.findByKakaoId(id);

            userRepository.delete(user);

        } catch (ExpiredJwtException exception) {
            // 예외가 발생한 경우에 대한 처리
            throw new BaseException(INVALID_JWT);
        }
    }

    public Long extractId(String token) {// "Bearer " 접두어를 제거해서 넣음
        Long userCode = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                .getClaim("id").asLong();

        return userCode;
    }


    public User getUser(HttpServletRequest request) {
        Long userCode = (Long) request.getAttribute("userCode");

        User user = userRepository.findByUserCode(userCode);

        return user;
    }

    public User findById(Long id) {
        return userRepository.findByUserCode(id);
    }

    public User findByKakaoId(Long id) {
        return userRepository.findByKakaoId(id);
    }


}