package vben.mybatis.demo.link.main;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import icu.mhb.mybatisplus.plugln.extend.Joins;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.mybatis.core.domain.SysOrg;
import vben.common.mybatis.core.service.BaseMainService;
import vben.mybatis.demo.link.item.DemoLinkItem;
import vben.mybatis.demo.link.item.DemoLinkItemMapper;
import vben.mybatis.demo.link.mid.DemoLinkMainOrg;
import vben.mybatis.demo.link.mid.DemoLinkMainOrgMapper;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DemoLinkMainService extends BaseMainService<DemoLinkMain> {

    private final DemoLinkMainMapper mapper;

    private final DemoLinkItemMapper itemMapper;

    private final DemoLinkMainOrgMapper midMapper;

    @PostConstruct
    public void initDao() {
        super.setMapper(mapper);
    }

    @Transactional(readOnly = true)
    public DemoLinkMain selectx(Long id) {
        DemoLinkMain main = select(id);
        //一对多关联查询
        main.setItems(itemMapper.selectList(new LambdaQueryWrapper<DemoLinkItem>().eq(DemoLinkItem::getMaiid, main.getId())));
        //多对多关联查询
        List<SysOrg> list = Joins.of(SysOrg.class)
            .leftJoin(DemoLinkMainOrg.class,DemoLinkMainOrg::getOid,SysOrg::getId)
            .eq(DemoLinkMainOrg::getMid,id).end()
            .joinList(SysOrg.class);
        main.setOrgs(list);
        return main;
    }

    public Long insertx(DemoLinkMain main) {
        insert(main);
        for (DemoLinkItem item : main.getItems()) {
            item.setMaiid(main.getId());
            itemMapper.insert(item);
        }
        for (SysOrg sysOrg : main.getOrgs()) {
            DemoLinkMainOrg mid = new DemoLinkMainOrg(main.getId(),sysOrg.getId());
            midMapper.insert(mid);
        }
        return main.getId();
    }

    public Long updatex(DemoLinkMain main) {
        //先删
        itemMapper.delete(new LambdaQueryWrapper<DemoLinkItem>().eq(DemoLinkItem::getMaiid, main.getId()));
        midMapper.delete(new LambdaQueryWrapper<DemoLinkMainOrg>().eq(DemoLinkMainOrg::getMid, main.getId()));
        //后增
        update(main);
        for (DemoLinkItem item : main.getItems()) {
            itemMapper.insert(item);
        }
        for (SysOrg sysOrg : main.getOrgs()) {
            DemoLinkMainOrg mid = new DemoLinkMainOrg(main.getId(),sysOrg.getId());
            midMapper.insert(mid);
        }
        return main.getId();
    }

    public Integer deletex(Long[] ids) {
        List<Long> list= Arrays.asList(ids);
        itemMapper.delete(new LambdaQueryWrapper<DemoLinkItem>().in(DemoLinkItem::getMaiid, list));
        midMapper.delete(new LambdaQueryWrapper<DemoLinkMainOrg>().in(DemoLinkMainOrg::getMid, list));
        return delete(ids);
    }
}
