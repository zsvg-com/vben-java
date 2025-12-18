package vben.common.translation.core.impl;

import lombok.AllArgsConstructor;
import vben.common.translation.annotation.TranslationType;
import vben.common.translation.constant.TransConstant;
import vben.common.translation.core.TranslationInterface;
import vben.common.core.service.UserService;

/**
 * 用户名称翻译实现
 *
 * @author may
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.USER_ID_TO_NICKNAME)
public class NicknameTranslationImpl implements TranslationInterface<String> {

    private final UserService userService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof Long id) {
            return userService.selectNicknameByIds(id.toString());
        } else if (key instanceof String ids) {
            return userService.selectNicknameByIds(ids);
        }
        return null;
    }
}
