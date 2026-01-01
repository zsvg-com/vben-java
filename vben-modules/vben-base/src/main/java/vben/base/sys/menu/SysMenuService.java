package vben.base.sys.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.jdbc.dto.Ltree;
import vben.common.jdbc.sqler.Sqler;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysMenuService {

    @Transactional(readOnly = true)
    public SysMenu findById(Long id) {
        return dao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Ltree> findTree(Long id) {
        return dao.findTree(id);
    }

    @Transactional(readOnly = true)
    public List<SysMenuVo> findList(Sqler sqler) {
        return dao.findList(sqler);
    }

    public void insert(SysMenu main) {
        dao.insert(main);
    }

    public void update(SysMenu main) {
        dao.update(main);
    }

    public int delete(Long[] ids) {
        for (Long str : ids) {
            dao.deleteById(str);
        }
        return ids.length;
    }

    private final SysMenuDao dao;

}
