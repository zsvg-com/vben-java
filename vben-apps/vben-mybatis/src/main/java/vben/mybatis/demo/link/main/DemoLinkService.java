package vben.mybatis.demo.link.main;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import icu.mhb.mybatisplus.plugln.extend.Joins;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.mybatis.core.domain.SysActor;
import vben.common.mybatis.core.service.BaseMainService;
import vben.mybatis.demo.link.item.DemoLinkItem;
import vben.mybatis.demo.link.item.DemoLinkItemMapper;
import vben.mybatis.demo.link.mid.DemoLinkActor;
import vben.mybatis.demo.link.mid.DemoLinkActorMapper;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DemoLinkService extends BaseMainService<DemoLink> {

    private final DemoLinkMapper mapper;

    private final DemoLinkItemMapper itemMapper;

    private final DemoLinkActorMapper midMapper;

    @PostConstruct
    public void initDao() {
        super.setMapper(mapper);
    }

    @Transactional(readOnly = true)
    public DemoLink selectx(Long id) {
        DemoLink main = select(id);
        //一对多关联查询
        main.setItems(itemMapper.selectList(new LambdaQueryWrapper<DemoLinkItem>().eq(DemoLinkItem::getMaiid, main.getId())));
        //多对多关联查询
        List<SysActor> list = Joins.of(SysActor.class)
            .leftJoin(DemoLinkActor.class, DemoLinkActor::getAid, SysActor::getId)
            .eq(DemoLinkActor::getMid,id).end()
            .joinList(SysActor.class);
        main.setActors(list);
        return main;
    }

    public Long insertx(DemoLink main) {
        insert(main);
        for (DemoLinkItem item : main.getItems()) {
            item.setMaiid(main.getId());
            itemMapper.insert(item);
        }
        for (SysActor sysActor : main.getActors()) {
            DemoLinkActor mid = new DemoLinkActor(main.getId(), sysActor.getId());
            midMapper.insert(mid);
        }
        return main.getId();
    }

    public Long updatex(DemoLink main) {
        //先删
        itemMapper.delete(new LambdaQueryWrapper<DemoLinkItem>().eq(DemoLinkItem::getMaiid, main.getId()));
        midMapper.delete(new LambdaQueryWrapper<DemoLinkActor>().eq(DemoLinkActor::getMid, main.getId()));
        //后增
        update(main);
        for (DemoLinkItem item : main.getItems()) {
            itemMapper.insert(item);
        }
        for (SysActor sysActor : main.getActors()) {
            DemoLinkActor mid = new DemoLinkActor(main.getId(), sysActor.getId());
            midMapper.insert(mid);
        }
        return main.getId();
    }

    public Integer deletex(Long[] ids) {
        List<Long> list= Arrays.asList(ids);
        itemMapper.delete(new LambdaQueryWrapper<DemoLinkItem>().in(DemoLinkItem::getMaiid, list));
        midMapper.delete(new LambdaQueryWrapper<DemoLinkActor>().in(DemoLinkActor::getMid, list));
        return delete(ids);
    }
}
