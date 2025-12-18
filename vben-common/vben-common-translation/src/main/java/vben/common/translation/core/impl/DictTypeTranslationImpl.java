package vben.common.translation.core.impl;

import lombok.AllArgsConstructor;
import vben.common.core.service.DictService;
import vben.common.core.utils.StrUtils;
import vben.common.translation.annotation.TranslationType;
import vben.common.translation.constant.TransConstant;
import vben.common.translation.core.TranslationInterface;

/**
 * 字典翻译实现
 *
 * @author Lion Li
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.DICT_TYPE_TO_LABEL)
public class DictTypeTranslationImpl implements TranslationInterface<String> {

    private final DictService dictService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof String dictValue && StrUtils.isNotBlank(other)) {
            return dictService.getDictLabel(other, dictValue);
        }
        return null;
    }
}
