package cn.edu.xmu.seckill.vo;

import cn.edu.xmu.seckill.validator.IsMobile;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/*登陆参数
 * */
@Data
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    @Length(min=32)
    private String password;
}
