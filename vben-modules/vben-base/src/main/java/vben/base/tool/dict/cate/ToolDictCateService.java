package vben.base.tool.dict.cate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.SidName;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ToolDictCateService {

    public List<SidName> findList() {
        return dao.findList();
    }


    public ToolDictCate findById(Long id) {
        return dao.findById(id);
    }

    public void insert(ToolDictCate main) {
        main.setId(IdUtils.getSnowflakeNextId());
        dao.insert(main);
    }

    public void update(ToolDictCate main) {
        dao.update(main);
    }

    public int delete(Long[] ids) {
        for (Long id : ids) {
            dao.deleteById(id);
        }
        return ids.length;
    }

    private final ToolDictCateDao dao;
}
