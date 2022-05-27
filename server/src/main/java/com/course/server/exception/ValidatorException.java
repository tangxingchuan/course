package com.course.server.exception;

/**
 * @author TangKe（唐柯）
 * @date 2022/5/27 - 11:13 上午
 */

//校验异常
public class ValidatorException extends RuntimeException {

    public ValidatorException(String message){
        super(message);
    }
}
