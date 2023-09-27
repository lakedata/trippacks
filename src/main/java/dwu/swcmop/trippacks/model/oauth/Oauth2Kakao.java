package dwu.swcmop.trippacks.model.oauth;

import java.net.HttpURLConnection;
import java.net.URL;

public class Oauth2Kakao {

    public int kakaoLogout(String access_Token) {
        /**
         * accessToken으로 로그아웃하기
         */
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        int responseCode = 0;
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);
            responseCode = conn.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseCode;
    }
}
