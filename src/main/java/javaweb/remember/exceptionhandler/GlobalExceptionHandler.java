//Author:刘行
package javaweb.remember.exceptionhandler;

import javaweb.remember.vo.ResultVo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultVo handleConstraintViolationException(ConstraintViolationException e){
        return new ResultVo(-100,e.getMessage(),null);
    }
}
