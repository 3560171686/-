package cn.edu.xmu.seckill.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    //通用
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常"),
    //登录模块
    LOGIN_ERROR(500210, "用户名或密码错误"),
    MOBILE_ERROR(500211, "手机号码格式不正确"),
    BIND_ERROR(500212,"参数校验异常"),

    //秒杀模块
    EMPTY_STOCK(500501,"库存不够"),
    HAS_SECKILL(500502,"该商品每人限购一次"),
    ;
    private final Integer code;
    private final String message;
}
