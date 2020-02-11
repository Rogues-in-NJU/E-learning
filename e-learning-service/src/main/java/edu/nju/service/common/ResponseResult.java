package edu.nju.service.common;

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

}

