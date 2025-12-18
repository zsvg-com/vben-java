package vben.base.tool.dict.main;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.tool.dict.data.ToolDictDataVo;
import vben.common.core.constant.CacheNames;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.dto.SidName;
import vben.common.jdbc.sqler.Sqler;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ToolDictMainService {

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return dao.findPageData(sqler);
    }

    @Transactional(readOnly = true)
    public List<SidName> findList() {
        return dao.findList();
    }
    /**
     * 根据字典类型查询字典数据
     *
     * @param code 字典代码
     * @return 字典数据集合信息
     */
    @Cacheable(cacheNames = CacheNames.SYS_DICT, key = "#code")
    public List<ToolDictDataVo> findDictData(String code) {
        return dao.findDictData(code);
    }

    public ToolDictMain findById(Long id) {
        return dao.findById(id);
    }

    public void insert(ToolDictMain main) {
        main.setId(IdUtils.getSnowflakeNextId());
        dao.insert(main);
    }

    public void update(ToolDictMain main) {
        dao.update(main);
    }

    public int delete(Long[] ids) {
        for (Long id : ids) {
            dao.deleteById(id);
        }
        return ids.length;
    }

    private final ToolDictMainDao dao;
}
