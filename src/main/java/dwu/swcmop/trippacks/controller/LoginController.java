package dwu.swcmop.trippacks.controller;

import dwu.swcmop.trippacks.config.BaseException;
import dwu.swcmop.trippacks.config.BaseResponse;
import dwu.swcmop.trippacks.entity.User;
import dwu.swcmop.trippacks.config.jwt.JwtProperties;
import dwu.swcmop.trippacks.service.UserService;
import dwu.swcmop.trippacks.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static dwu.swcmop.trippacks.config.BaseResponseStatus.INVALID_USER;

@RestController //(1)
@RequestMapping("/kakao")
public class LoginController {
    @Autowired
    private UserService userService; //(2)


    // 프론트에서 인가코드 받아오는 url
    @ApiOperation(value = "카카오 로그인", notes = "access_token을 얻어 db저장 후 jwt발급한다.")
    @GetMapping("/oauth/token") // (3)
    public ResponseEntity getLogin(@RequestParam("code") String code) { //(4)
        System.out.println("code : " + code);

        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장 후 JWT 를 생성
        String jwtToken = userService.SaveUserAndGetToken(code);

        //(3)
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        //(4)
        return ResponseEntity.ok().headers(headers).body("success");
    }

    @ApiOperation(value = "카카오 회원탈퇴", notes = "JWT로 사용자id 받아와 DB삭제")
    @DeleteMapping("/delete")
    public BaseResponse<String> deleteUser(@RequestParam("Authorization") String accessToken) {
        try {
            Long Id = userService.extractId(accessToken);
            userService.deleteUser(Id);

            String result = "삭제되었습니다.";

            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ApiOperation(value = "사용자 조회", notes = "사용자Id로 사용자정보 조회")
    @GetMapping("/{userId}")
    public BaseResponse<String> getUser(@PathVariable("userId") Long id) {

        try {
            String user = String.valueOf(userService.findById(id));

            if (userService.findById(id) == null) {
                return new BaseResponse<>(INVALID_USER);
            }

            return new BaseResponse<>(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // jwt 토큰으로 유저정보 요청하기
    @ApiOperation(value = "사용자 정보", notes = "HttpServletRequest로 사용자 정보 가져오기.")
    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) {

        User user = userService.getUser(request);

        return ResponseEntity.ok().body(user);
    }




}