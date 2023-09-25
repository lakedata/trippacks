package dwu.swcmop.trippacks.config.jwt;

public interface JwtProperties {
    String SECRET = "{}"; //(2) 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 864000000; //(3)10일
    String TOKEN_PREFIX = "Bearer "; //(4)
    String HEADER_STRING = "Authorization"; //(5)
}
