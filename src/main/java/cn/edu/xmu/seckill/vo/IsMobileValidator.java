package cn.edu.xmu.seckill.vo;

import cn.edu.xmu.seckill.utils.ValidatorUtil;
import cn.edu.xmu.seckill.validator.IsMobile;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/*手机号码校验规则
* */
public class IsMobileValidator implements ConstraintValidator <IsMobile, String>{
    private boolean required=false;
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required=constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(required){
        return ValidatorUtil.isMobile(value);
        }else {
          if(StringUtils.isEmpty(value)){
              return true;
          }
          else{
              return ValidatorUtil.isMobile(value);
          }
        }
    }
}
