package vben.base.tool.form;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.DateUtils;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.satoken.utils.LoginHelper;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class ToolFormService {

    private final ToolFormDao dao;

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        sqler.selectCUinfo();
        return dao.findPageData(sqler);
    }

    public ToolForm select(Long id) {
        return dao.findById(id);
    }

    public Long insert(ToolForm main) {
        main.setId(IdUtils.getSnowflakeNextId());
        main.setName(DateUtils.now());
        main.setUptim(main.getCrtim());
        main.setCruid(LoginHelper.getUserId());
        main.setUpuid(main.getCruid());
        dao.insert(main);
        return main.getId();
    }

    public Long update(ToolForm main) {
        dao.update(main);
        main.setUptim(new Date());
        main.setUpuid(LoginHelper.getUserId());
        return main.getId();
    }

    public int delete(Long[] ids) {
        for (Long id : ids) {
            dao.deleteById(id);
        }
        return ids.length;
    }

}

