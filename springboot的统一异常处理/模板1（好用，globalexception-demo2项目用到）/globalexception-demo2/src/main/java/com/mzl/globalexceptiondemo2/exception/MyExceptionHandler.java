package com.mzl.globalexceptiondemo2.exception;

import com.mzl.globalexceptiondemo2.utils.ResultEnum;
import com.mzl.globalexceptiondemo2.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @ClassName :   ExceptionHandler
 * @Description: 自定义的异常统一处理类，方便用户更加友好地看到错误信息
 * @Author: mzl
 * @CreateDate: 2020/10/13 21:53
 * @Version: 1.0
 */
@RestControllerAdvice   //捕获并处理异常，并输出到前端的响应body区
//@Slf4j
public class MyExceptionHandler {

    //增加异常日志打印(可以用@Slf4j来直接打印日志)
    public static Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);
    //设置异常错误的页面
    public static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * 异常捕获处理
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Object handle(HttpServletRequest request, Exception e){
        //如果是自定义的异常
        if (e instanceof MyException){
            MyException myException = (MyException) e;
            return ResultUtils.getError(myException.getCode(), myException.getMessage());
        }else {  //如果是自定义的异常
            logger.error("【系统异常】= {}", e);
            return ResultUtils.getError(ResultEnum.SystemException.getCode(), ResultEnum.SystemException.getMsg());
        }
    }

    /**
     * 判断是否是Ajax的请求
     * @param request
     * @return
     */
    public boolean isAjax(HttpServletRequest request){
        return (request.getHeader("X-Request-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Request-With").toString()));
    }

    
    /*
    //备注:
    //这个是正式项目完成之后的错误统一处理(开发情况先用上面的的)
    //我们在开发过程中还是用json格式的会好一些，要不然看错误麻烦
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();
        //判断是否是Ajax的异常请求（如果是Ajax的那么就是返回json格式）
        if(isAjax(req)){
            //如果是自定义的异常
            if(e instanceof MyException){
                MyException myException = (MyException)e;
                return ResultUtil.getError(myException.getCode(),myException.getMessage());
            }else{
                //如果是系统的异常，比如空指针这些异常
                logger.error("【系统异常】={}",e);
                return ResultUtil.getError(ResultEnum.SystemException.getCode(),ResultEnum.SystemException.getMsg());
            }
        }else{
            //如果是系统内部发生异常，那么就返回到错误页面进行友好的提示
            ModelAndView mav = new ModelAndView();
            //这些就是要返回到页面的内容（其实不用都行，反正用户也不懂，没必要在页面显示都可以，先写着吧）
            mav.addObject("exception", e);
            mav.addObject("url", req.getRequestURL());
            mav.setViewName(DEFAULT_ERROR_VIEW);
            return mav;
        }
    }
    */

}
