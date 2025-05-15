package cn.edu.xmu.seckill.exception;

import cn.edu.xmu.seckill.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GlobalException extends RuntimeException{
    private RespBeanEnum respBeanEnum;
}
