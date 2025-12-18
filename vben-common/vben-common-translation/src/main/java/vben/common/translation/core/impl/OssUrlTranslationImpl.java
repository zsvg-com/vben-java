package vben.common.translation.core.impl;

import lombok.AllArgsConstructor;
import vben.common.translation.annotation.TranslationType;
import vben.common.translation.constant.TransConstant;
import vben.common.translation.core.TranslationInterface;
import vben.common.core.service.OssService;

/**
 * OSS翻译实现
 *
 * @author Lion Li
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.OSS_ID_TO_URL)
public class OssUrlTranslationImpl implements TranslationInterface<String> {

    private final OssService ossService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof String ids) {
            return ossService.selectUrlByIds(ids);
        } else if (key instanceof Long id) {
            return ossService.selectUrlByIds(id.toString());
        }
        return null;
    }
}
