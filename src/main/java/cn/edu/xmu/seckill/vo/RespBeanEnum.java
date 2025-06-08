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
    MOBILE_NOT_EXIT(500213, "手机号码不存在"),
    PASSWORD_UPDATE_FAIL(500214, "密码更新失败"),
    //秒杀模块
    EMPTY_STOCK(500501, "库存不够"),
    HAS_SECKILL(500502, "该商品每人限购一次"),
    SESSION_ERROR(500503, "该用户不存在"),
    REQUEST_ILLEGAL(500504, "请求非法"),
    CAPTCHA_ERROR(500505, "验证码错误"),
    ACCESS_LIMIT_REACHED(500506, "访问过于频繁"),
    //订单
    ORDER_NOT_EXIST(50021, "该订单信息不存在");
    ;
    private final Integer code;
    private final String message;
}
