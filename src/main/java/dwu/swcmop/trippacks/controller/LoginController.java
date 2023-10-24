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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dwu.swcmop.trippacks.config.BaseResponseStatus.INVALID_USER;

@RestController //(1)
@RequestMapping("/kakao")
public class LoginController {
    @Autowired
    private UserService userService; //(2)


    // 프론트에서 인가코드 받아오는 url
    @ApiOperation(value = "카카오 로그인", notes = "카카오값(프론트에서 accesstoken으로 저장된 회원정보)를 DB저장 후 JWT발급한다.")
    @GetMapping("/oauth/token") // (3)
    public ResponseEntity getLogin(@RequestParam("kakaoId") Long kakaoId, @RequestParam("kakaoProfileImg") String kakaoProfileImg, @RequestParam("kakaoNickname") String kakaoNickname, @RequestParam("kakaoEmail") String kakaoEmail, @RequestParam("userRole") String userRole) {


        // 카카오 회원 정보 DB 저장 후 JWT 를 생성
        String jwtToken = userService.saveUserAndGetToken(kakaoId, kakaoProfileImg, kakaoNickname, kakaoEmail, userRole);


        //(3)
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        //(4)
        return ResponseEntity.ok().headers(headers).body("success");
    }

//    @ApiOperation(value = "JWT로 회원탈퇴", notes = "JWT로 사용자Id 받아와 DB삭제")
//    @DeleteMapping("/delete")
//    public BaseResponse<String> deleteUser(@RequestParam("Authorization") String accessToken) {
//        try {
//            Long Id = userService.extractId(accessToken);
//            userService.deleteUser(Id);
//
//            String result = "삭제되었습니다.";
//
//            return new BaseResponse<>(result);
//
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }

    @ApiOperation(value = "kakaoId로 회원탈퇴", notes = "kakaoId 받아와 DB삭제")
    @DeleteMapping("/delete/{kakaoId}")
    public BaseResponse<String> deleteKakaoUser(@PathVariable("kakaoId") Long id) {
        try {
            userService.deleteKakaoUser(id);

            String result = "삭제되었습니다.";

            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ApiOperation(value = "UserCode로 사용자 조회", notes = "사용자Id로 사용자정보 조회")
    @GetMapping("find/{userId}")
    public ResponseEntity<Map<String, String>> getUser(@PathVariable("userId") Long id) {

        try {
            User user = userService.findById(id);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("result", "User not found"));
            }

            Map<String, String> result = new HashMap<>();
            result.put("kakaoNickname", user.getKakaoNickname());
            result.put("kakaoProfileImg", user.getKakaoProfileImg());

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @ApiOperation(value = "KakaoId로 사용자 조회", notes = "KakaoId로 사용자정보 조회")
    @GetMapping("/{kakaoId}")
    public BaseResponse<String> getKakaoUser(@PathVariable("kakaoId") Long id) {

        try {
            String user = String.valueOf(userService.findByKakaoId(id));

            if (userService.findByKakaoId(id) == null) {
                return new BaseResponse<>(INVALID_USER);
            }

            return new BaseResponse<>(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


//    // jwt 토큰으로 유저정보 요청하기
//    @ApiOperation(value = "사용자 정보", notes = "HttpServletRequest로 사용자 정보 가져오기.")
//    @GetMapping("/me")
//    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) {
//
//        User user = userService.getUser(request);
//
//        return ResponseEntity.ok().body(user);
//    }

    @ApiOperation(value = "모든 사용자 조회", notes = "DB에 있는 모든 사용자정보를 조회합니다.")
    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @ApiOperation(value = "kakaoNickname로 userCode 조회", notes = "kakaoNickname을 사용하여 해당 유저의 userCode를 조회합니다.")
    @GetMapping("/find-usercode/{kakaoNickname}")
    public ResponseEntity<Long> getUserCodeByNickname(@PathVariable("kakaoNickname") String kakaoNickname) {
        System.out.println(kakaoNickname);

        Long userCode = userService.findUserCodeByKakaoNickname(kakaoNickname);

        if (userCode == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(userCode);
    }
}