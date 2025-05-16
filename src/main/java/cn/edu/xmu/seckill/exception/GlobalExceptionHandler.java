package cn.edu.xmu.seckill.exception;

import cn.edu.xmu.seckill.vo.RespBean;
import cn.edu.xmu.seckill.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public RespBean handler(Exception e){
        if(e instanceof GlobalException){
            GlobalException globalException = (GlobalException) e;
            return RespBean.error(globalException.getRespBeanEnum());
        }else if(e instanceof BindException){
            BindException bindException = (BindException) e;
           RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
           respBean.setMessage("参数校验异常:" +  bindException.getBindingResult().getAllErrors().get(0).getDefaultMessage());
           return respBean;
        }
        log.info("登录错误");
        return RespBean.error(RespBeanEnum.ERROR);

    }
}
