package com.example.demo.dubbo.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.datasource.DataSourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 路径：com.example.demo.dubbo.exception
 * 类名：ExceptionAdvice
 * 功能：全局异常处理类
 * 备注：
 * 创建人：typ
 * 创建时间：2018/11/13 18:28:28
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 方法名：handleMissingServletRequestParameterException
     * 功能：缺少请求参数
     * 描述：400 - Bad Request
     * 创建人：typ
     * 创建时间：2018/11/13 18:28:28
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Map<String,Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少请求参数", e);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",400);
        map.put("message","缺少请求参数!");
        map.put("data",null);
        return map;
    }

    /**
     * 方法名：handleHttpMessageNotReadableException
     * 功能：参数解析失败
     * 描述：400 - Bad Request
     * 创建人：typ
     * 创建时间：2018/11/13 18:28:28
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String,Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数解析失败", e);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",400);
        map.put("message","参数解析失败!");
        map.put("data",null);
        return map;
    }

    /**
     * 方法名：handleMethodArgumentNotValidException
     * 功能：参数验证失败
     * 描述：400 - Bad Request
     * 创建人：typ
     * 创建时间：2018/11/13 18:28:28
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数验证失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        log.info("参数验证失败:"+message);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",400);
        map.put("message","参数验证失败!");
        map.put("data",null);
        return map;
    }

    /**
     * 方法名：handleBindException
     * 功能：参数绑定失败
     * 描述： 400 - Bad Request
     * 创建人：typ
     * 创建时间：2018/11/13 18:28:28
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Map<String,Object> handleBindException(BindException e) {
        log.error("参数绑定失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        log.info("参数绑定失败:"+String.format("%s:%s", field, code));
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",400);
        map.put("message","参数绑定失败!");
        map.put("data",null);
        return map;
    }

    /**
     * 方法名：handleServiceException
     * 功能：参数验证失败
     * 描述：400 - Bad Request
     * 创建人：typ
     * 创建时间：2018/11/13 18:28:28
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String,Object> handleServiceException(ConstraintViolationException e) {
        log.error("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        log.error("参数验证失败"+violation.getMessage());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",400);
        map.put("message","参数验证失败!");
        map.put("data",null);
        return map;
    }

    /**
     * 方法名：handleValidationException
     * 功能：参数验证失败
     * 描述：400 - Bad Request
     * 创建人：typ
     * 创建时间：2018/11/13 18:28:28
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Map<String,Object> handleValidationException(ValidationException e) {
        log.error("参数验证失败", e);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",400);
        map.put("message","参数验证失败!");
        map.put("data",null);
        return map;
    }

    /**
     * 方法名：noHandlerFoundException
     * 功能：请求的方法找不到
     * 描述：404 - Not Found
     * 创建人：typ
     * 创建时间：2018/11/13 18:28:28
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Map<String,Object> noHandlerFoundException(NoHandlerFoundException e) {
        log.error("Not Found", e);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",404);
        map.put("message","Not Found!");
        map.put("data",null);
        return map;
    }

    /**
     * 方法名：handleHttpRequestMethodNotSupportedException
     * 功能：不支持当前请求方法
     * 描述：405 - Method Not Allowed
     * 创建人：typ
     * 创建时间：2018/11/13 18:28:28
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map<String,Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",405);
        map.put("message","不支持当前请求方法!");
        map.put("data",null);
        return map;
    }

    /**
     * 方法名：handleHttpMediaTypeNotSupportedException
     * 功能：不支持当前媒体类型
     * 描述：415 - Unsupported Media Type
     * 创建人：typ
     * 创建时间：2018/11/13 18:28:28
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Map<String,Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("不支持当前媒体类型", e);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",415);
        map.put("message","不支持当前媒体类型!");
        map.put("data",null);
        return map;
    }

    /**
     * 方法名：handleException
     * 功能：操作数据或库出现异常
     * 描述：500-内部服务器错误
     * 创建人：typ
     * 创建时间：2018/11/13 18:28:28
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataSourceException.class)
    public Map<String,Object> handleException(DataSourceException e) {
        log.error("操作数据库出现异常:", e);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",500);
        map.put("message","服务器异常!");
        map.put("data",null);
        return map;
    }

    /**
     * 方法名：handleCustomException
     * 功能：自己声明异常的情况
     * 描述：
     * 创建人：typ
     * 创建时间：2018/11/13 18:28:28
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ExceptionUtil.class)
    public Map<String,Object> handleCustomException(ExceptionUtil e) {
        log.error("自定义异常", e);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",e.getResult().getCode());
        map.put("message",e.getResult().getMessage());
        map.put("data",e.getResult().getData());
        return map;
    }*/

    /**
     * 方法名：defaultErrorHandler
     * 功能：获取其它异常。包括500
     * 描述：
     * 创建人：typ
     * 创建时间：2018/11/13 18:28:28
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
   @ExceptionHandler(value = Exception.class)
    public Map<String,Object> defaultErrorHandler(Exception e){
        log.error("Exception", e);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",500);
        map.put("message", "服务器异常!");
        map.put("data",null);
        return map;
    }
}