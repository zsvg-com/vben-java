package vben.common.core.validate.enumd;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vben.common.core.utils.StrUtils;
import vben.common.core.utils.reflect.ReflectUtils;

/**
 * 自定义枚举校验注解实现
 *
 * @author 秋辞未寒
 * @date 2024-12-09
 */
public class EnumPatternValidator implements ConstraintValidator<EnumPattern, String> {

    private EnumPattern annotation;

    @Override
    public void initialize(EnumPattern annotation) {
        ConstraintValidator.super.initialize(annotation);
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtils.isNotBlank(value)) {
            String fieldName = annotation.fieldName();
            for (Object e : annotation.type().getEnumConstants()) {
                if (value.equals(ReflectUtils.invokeGetter(e, fieldName))) {
                    return true;
                }
            }
        }
        return false;
    }

}
