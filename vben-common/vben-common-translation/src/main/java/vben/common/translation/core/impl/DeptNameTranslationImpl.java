package vben.common.translation.core.impl;

import lombok.AllArgsConstructor;
import vben.common.translation.annotation.TranslationType;
import vben.common.translation.constant.TransConstant;
import vben.common.translation.core.TranslationInterface;
import vben.common.core.service.DeptService;

/**
 * 部门翻译实现
 *
 * @author Lion Li
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.DEPT_ID_TO_NAME)
public class DeptNameTranslationImpl implements TranslationInterface<String> {

    private final DeptService deptService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof String ids) {
            return deptService.selectDeptNameByIds(ids);
        } else if (key instanceof Long id) {
            return deptService.selectDeptNameByIds(id.toString());
        }
        return null;
    }
}
