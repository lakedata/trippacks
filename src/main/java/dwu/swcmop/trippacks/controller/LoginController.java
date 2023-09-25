package dwu.swcmop.trippacks.controller;

import dwu.swcmop.trippacks.entity.User;
import dwu.swcmop.trippacks.config.jwt.JwtProperties;
import dwu.swcmop.trippacks.model.oauth.OauthToken;
import dwu.swcmop.trippacks.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController //(1)
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UserService userService; //(2)

    // 프론트에서 인가코드 받아오는 url
    @ApiOperation(value = "카카오로그인", notes = "액세스 토큰을 얻어 jwt발급한다.")
    @GetMapping("/oauth/token") // (3)
    public ResponseEntity getLogin(@RequestParam("code") String code) { //(4)
        System.out.println("code : " + code);

        // 넘어온 인가 코드를 통해 access_token 발급 //(5)
        OauthToken oauthToken = userService.getAccessToken(code);

        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장 후 JWT 를 생성
        String jwtToken = userService.SaveUserAndGetToken(oauthToken.getAccess_token());

        //(3)
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        //(4)
        return ResponseEntity.ok().headers(headers).body("success");
    }

    // jwt 토큰으로 유저정보 요청하기
    @ApiOperation(value = "사용자 정보", notes = "HttpServletRequest로 사용자 정보 가져오기.")
    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) {

        User user = userService.getUser(request);

        return ResponseEntity.ok().body(user);
    }

    @ApiOperation(value = "로그아웃", notes = "카카오 로그아웃한다.")
    @GetMapping(value = "/logout")
    public Map<String, Object> logout(@RequestHeader Map<String, Object> requestHeader) {
        System.out.println(requestHeader);
        System.out.println(requestHeader.get("authorization"));
        System.out.println("로그아웃 시도");
        return requestHeader;
    }

}