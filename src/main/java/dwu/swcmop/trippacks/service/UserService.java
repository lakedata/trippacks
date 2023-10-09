package dwu.swcmop.trippacks.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dwu.swcmop.trippacks.entity.User;
import dwu.swcmop.trippacks.config.jwt.JwtProperties;
//import dwu.swcmop.trippacks.model.oauth.Oauth2Kakao;
import dwu.swcmop.trippacks.model.oauth.OauthToken;
import dwu.swcmop.trippacks.model.oauth.KakaoProfile;
import dwu.swcmop.trippacks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository; //(1)
//    Oauth2Kakao oauth2Kakao;

    public OauthToken getAccessToken(String code) {

        //(2)RsTemplate이용해 URL형식으로 PSOT
        RestTemplate rt = new RestTemplate();

        //(3)헤더만들기
        //(3)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //(4)
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "b791159adc4e18ab175997922e03859a");//{클라이언트 앱 키}
        params.add("redirect_uri", "http://localhost:3000/auth");//{리다이렉트 uri}
        params.add("code", code);
        params.add("client_secret", "SvMeE9hOht1CwtY23mlES55AY66dq7pP"); // 생략 가능!{시크릿 키}

        //(5)
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //(6)
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //(7)
        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return oauthToken; //(8)
    }

    public String SaveUserAndGetToken(String token) { //(1)
        KakaoProfile profile = findProfile(token);

        User user = userRepository.findByKakaoEmail(profile.getKakao_account().getEmail());
        if (user == null) {
            user = User.builder()
                    .kakaoId(profile.getId())
                    .kakaoProfileImg(profile.getKakao_account().getProfile().getProfile_image_url())
                    .kakaoNickname(profile.getKakao_account().getProfile().getNickname())
                    .kakaoEmail(profile.getKakao_account().getEmail())
                    .userRole("ROLE_USER").build();

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

    public User getUser(HttpServletRequest request) {
        Long userCode = (Long) request.getAttribute("userCode");

        User user = userRepository.findByUserCode(userCode);

        return user;
    }
}