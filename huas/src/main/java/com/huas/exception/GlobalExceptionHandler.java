package com.huas.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huas.utils.ConstantUtils;

/**
 * @ControllerAdvice 增强Controller的注解 可以实现全局异常捕获
 */
//@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     *  @ExceptionHandler 指明要捕获那个异常
     *  不加@ResponseBody  会使用视图解析器跳转页面
     *  形参处是Exception 简单来说就是会把捕获到的异常通过形参传入方法中
     */
    @ExceptionHandler(ErrorReturnPageException.class)
    public String errorReturnPageException(Exception e){
//        打印错误信息
          ConstantUtils.getRequest().setAttribute("errorMsg", e.getMessage());
//        跳转500页面
        return "forward:/error/500";
    }

    /**
     * 捕获  ErrorReturnResultException 异常
     * 通过 @ResponseBody 注解响应数据 会以json的格式响应
     */
    @ExceptionHandler(ErrorReturnResultException.class)
    @ResponseBody
    public Result errorReturnResultException(final Exception e) {
        ErrorReturnResultException exception = (ErrorReturnResultException) e;
        /**
         * Result 中可以写入自定义的异常状态码
         */
        return new Result(5001, exception.getMessage());
    }

    /**
     * 捕获  RuntimeException 异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result runtimeExceptionHandler(final Exception e) {
        RuntimeException exception = (RuntimeException) e;
        /**
         * Result 中可以写入自定义的异常状态码
         */
        return new Result(4004, exception.getMessage());
    }
}