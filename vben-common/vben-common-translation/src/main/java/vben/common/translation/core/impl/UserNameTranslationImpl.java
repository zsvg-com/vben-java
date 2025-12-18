package vben.common.translation.core.impl;

import lombok.AllArgsConstructor;
import vben.common.core.service.UserService;
import vben.common.translation.annotation.TranslationType;
import vben.common.translation.constant.TransConstant;
import vben.common.translation.core.TranslationInterface;

/**
 * 用户名翻译实现
 *
 * @author Lion Li
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.USER_ID_TO_NAME)
public class UserNameTranslationImpl implements TranslationInterface<String> {

    private final UserService userService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof Long id) {
            return userService.selectUserNameById(id);
        }
        return null;
    }
}
