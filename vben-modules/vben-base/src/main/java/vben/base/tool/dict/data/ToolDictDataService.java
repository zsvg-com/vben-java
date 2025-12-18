package vben.base.tool.dict.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ToolDictDataService {

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return dao.findPageData(sqler);
    }

    @Transactional(readOnly = true)
    public ToolDictData findById(Long id) {
        return dao.findById(id);
    }

    public void insert(ToolDictData main) {
        main.setId(IdUtils.getSnowflakeNextId());
        dao.insert(main);
    }

    public void update(ToolDictData main) {
        dao.update(main);
    }

    public int delete(Long[] ids) {
        for (Long id : ids) {
            dao.deleteById(id);
        }
        return ids.length;
    }

    private final ToolDictDataDao dao;
}
