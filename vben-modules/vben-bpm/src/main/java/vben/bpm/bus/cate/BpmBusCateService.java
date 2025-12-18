package vben.bpm.bus.cate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.jdbc.dto.Stree;
import vben.common.jdbc.sqler.Sqler;
import vben.common.satoken.utils.LoginHelper;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class BpmBusCateService {

    @Transactional(readOnly = true)
    public List<BpmBusCate> findList(Sqler sqler) {
        return dao.findList(sqler);
    }

    @Transactional(readOnly = true)
    public BpmBusCate findById(Long id) {
        return dao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Stree> findTree(Long id) {
        return dao.findTree(id);
    }

    public void insert(BpmBusCate main) {
        main.setCruid(LoginHelper.getUserId());
        dao.insert(main);
    }

    public void update(BpmBusCate main) {
        main.setUpuid(LoginHelper.getUserId());
        main.setUptim(new Date());
        dao.update(main);
    }

    public int delete(Long[] ids) {
        for (Long str : ids) {
            dao.deleteById(str);
        }
        return ids.length;
    }

    private final BpmBusCateDao dao;

}
