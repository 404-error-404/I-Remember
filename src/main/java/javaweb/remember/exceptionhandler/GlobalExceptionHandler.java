//Author:刘行
package javaweb.remember.exceptionhandler;

import javaweb.remember.vo.ResultVo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //格式错误异常
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultVo handleConstraintViolationException(ConstraintViolationException e){
        return new ResultVo(-100,e.getMessage(),null);
    }

    //数据类型错误异常
    @ExceptionHandler(IllegalStateException.class)
    public ResultVo handleIllegalStateException(IllegalStateException e){
        return new ResultVo(-100, e.getMessage(), null);
    }

    //文件读写异常
    @ExceptionHandler(IOException.class)
    public ResultVo handleIOException(IOException e){
        return new ResultVo(-100, e.getMessage(), null);
    }
}
