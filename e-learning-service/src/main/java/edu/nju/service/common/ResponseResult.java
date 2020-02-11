package edu.nju.service.common;

import edu.nju.alerp.enums.ExceptionEnum;
import edu.nju.service.enums.ExceptionEnum;
import lombok.Builder;
import lombok.Data;

/**
 * @Description: 回参
 */
@Data
@Builder
public class ResponseResult<T> {

    private String message;

    private int code;

    private T data;

    public static ResponseResult<String> ok() {
        return ok("success", ExceptionEnum.SUCCESS);
    }

    public static <T> ResponseResult<T> ok(T data) {
        return ok(data, ExceptionEnum.SUCCESS);
    }

    public static <T> ResponseResult<T> ok(T data, ExceptionEnum exceptionEnum) {
        return ResponseResult
                .<T>builder()
                .code(exceptionEnum.getCode())
                .message(exceptionEnum.getMessage())
                .data(data)
                .build();
    }

    public static <T> ResponseResult<T> fail() {
        return ResponseResult
                .<T>builder()
                .code(ExceptionEnum.OTHER_ERROR.getCode())
                .message()
                .build();
    }

}

