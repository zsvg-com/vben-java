package vben.base.sys.perm.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.jdbc.dto.Ltree;
import vben.common.jdbc.sqler.Sqler;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysPermMenuService {

    @Transactional(readOnly = true)
    public SysPermMenu findById(Long id) {
        return dao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Ltree> findTree(Long id) {
        return dao.findTree(id);
    }

    @Transactional(readOnly = true)
    public List<SysPermMenuVo> findList(Sqler sqler) {
        return dao.findList(sqler);
    }

    public void insert(SysPermMenu main) {
        dao.insert(main);
    }

    public void update(SysPermMenu main) {
        dao.update(main);
    }

    public int delete(Long[] ids) {
        for (Long str : ids) {
            dao.deleteById(str);
        }
        return ids.length;
    }

    private final SysPermMenuDao dao;

}
