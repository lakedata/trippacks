package dwu.swcmop.trippacks.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    // Common
    INVALID_JWT(false, 2000, "유효하지 않은 JWT입니다."),
    EMPTY_JWT(false, 2001, "JWT를 입력하세요"),
    INVALID_USER(false, 2002, "등록되지 않은 사용자입니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    SAVE_BAG_FAIL(false, 3001, "가방 생성에 실패했습니다."),
    DELETE_FRIEND_FAIL(false, 3002, "친구 삭제에 실패했습니다."),
    FRIEND_IS_EMPTY(false, 3003, "친구가 존재하지 않습니다."),
    ADD_FRIEND_FAIL(false, 3004, "친구 추가에 실패했습니다."),
    EXIST_FRIEND_FAIL(false, 3005, "친구 관계가 이미 존재합니다."),

    ACCEPT_FRIEND_FAIL(false, 3006, "친구 수락에 실패했습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
