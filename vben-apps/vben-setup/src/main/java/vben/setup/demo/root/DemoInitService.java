package vben.setup.demo.root;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.setup.sys.perm.api.SysPermApiEntity;
import vben.setup.sys.perm.api.SysPermApiRepo;
import vben.setup.sys.perm.menu.SysPermMenuEntity;
import vben.setup.sys.perm.menu.SysPermMenuRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * 案例初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class DemoInitService {

    private final SysPermMenuRepo menuRrepo;

    private final SysPermApiRepo apiRepo;

    public void init() {
        List<SysPermMenuEntity> menuList = new ArrayList<>();
        SysPermMenuEntity m8000 = new SysPermMenuEntity();
        m8000.setId(8000L);
        m8000.setIcon("hugeicons:star");
        m8000.setName("使用案例");
        m8000.setOrnum(8000);
        m8000.setPath("demo");
        m8000.setPid(0L);
        m8000.setType("1");
        m8000.setComp("Layout");
        menuList.add(m8000);

        SysPermMenuEntity m8010 = new SysPermMenuEntity();
        m8010.setId(8010L);
        m8010.setComp("demo/single/main/index");
        m8010.setIcon("pajamas:work-item-requirement");
        m8010.setName("单一主表案例");
        m8010.setOrnum(8010);
        m8010.setPath("single/main");
        m8010.setPid(8000L);
        menuList.add(m8010);

        SysPermMenuEntity m8020 = new SysPermMenuEntity();
        m8020.setId(8020L);
        m8020.setComp("demo/single/cate/index");
        m8020.setIcon("pajamas:work-item-requirement");
        m8020.setName("单一树表案例");
        m8020.setOrnum(8020);
        m8020.setPath("single/cate");
        m8020.setPid(8000L);
        menuList.add(m8020);

        SysPermMenuEntity m8030 = new SysPermMenuEntity();
        m8030.setId(8030L);
        m8030.setComp("demo/link/index");
        m8030.setIcon("pajamas:work-item-requirement");
        m8030.setName("关联主分子案例");
        m8030.setOrnum(8030);
        m8030.setPath("link");
        m8030.setPid(8000L);
        menuList.add(m8030);

        insertMenus(menuList);

        List<SysPermApiEntity> apiList = new ArrayList<>();

        SysPermApiEntity a801001 = new SysPermApiEntity();
        a801001.setId(801001L);
        a801001.setOrnum(801001);
        a801001.setName("单一主表-查询");
        a801001.setMenid(8010L);
        a801001.setPerm("single:main:query");
        apiList.add(a801001);

        SysPermApiEntity a801002 = new SysPermApiEntity();
        a801002.setId(801002L);
        a801002.setOrnum(801002);
        a801002.setName("单一主表-新增");
        a801002.setMenid(8010L);
        a801002.setPerm("single:main:add");
        apiList.add(a801002);

        SysPermApiEntity a801003 = new SysPermApiEntity();
        a801003.setId(801003L);
        a801003.setOrnum(801003);
        a801003.setName("单一主表-修改");
        a801003.setMenid(8010L);
        a801003.setPerm("single:main:edit");
        apiList.add(a801003);

        SysPermApiEntity a801004 = new SysPermApiEntity();
        a801004.setId(801004L);
        a801004.setOrnum(801004);
        a801004.setName("单一主表-删除");
        a801004.setMenid(8010L);
        a801004.setPerm("single:main:remove");
        apiList.add(a801004);

        SysPermApiEntity a802001 = new SysPermApiEntity();
        a802001.setId(802001L);
        a802001.setOrnum(802001);
        a802001.setName("单一树表-查询");
        a802001.setMenid(8020L);
        a802001.setPerm("single:cate:query");
        apiList.add(a802001);

        SysPermApiEntity a802002 = new SysPermApiEntity();
        a802002.setId(802002L);
        a802002.setOrnum(802002);
        a802002.setName("单一树表-新增");
        a802002.setMenid(8020L);
        a802002.setPerm("single:cate:add");
        apiList.add(a802002);

        SysPermApiEntity a802003 = new SysPermApiEntity();
        a802003.setId(802003L);
        a802003.setOrnum(802003);
        a802003.setName("单一树表-修改");
        a802003.setMenid(8020L);
        a802003.setPerm("single:cate:edit");
        apiList.add(a802003);

        SysPermApiEntity a802004 = new SysPermApiEntity();
        a802004.setId(802004L);
        a802004.setOrnum(802004);
        a802004.setName("单一树表-删除");
        a802004.setMenid(8020L);
        a802004.setPerm("single:cate:remove");
        apiList.add(a802004);

        SysPermApiEntity a803001 = new SysPermApiEntity();
        a803001.setId(803001L);
        a803001.setOrnum(803001);
        a803001.setName("关联主表-查询");
        a803001.setMenid(8030L);
        a803001.setPerm("link:main:query");
        apiList.add(a803001);

        SysPermApiEntity a803002 = new SysPermApiEntity();
        a803002.setId(803002L);
        a803002.setOrnum(803002);
        a803002.setName("关联主表-新增");
        a803002.setMenid(8030L);
        a803002.setPerm("link:main:add");
        apiList.add(a803002);

        SysPermApiEntity a803003 = new SysPermApiEntity();
        a803003.setId(803003L);
        a803003.setOrnum(803003);
        a803003.setName("关联主表-修改");
        a803003.setMenid(8030L);
        a803003.setPerm("link:main:edit");
        apiList.add(a803003);

        SysPermApiEntity a803004 = new SysPermApiEntity();
        a803004.setId(803004L);
        a803004.setOrnum(803004);
        a803004.setName("关联主表-删除");
        a803004.setMenid(8030L);
        a803004.setPerm("link:main:remove");
        apiList.add(a803004);

        SysPermApiEntity a803011 = new SysPermApiEntity();
        a803011.setId(803011L);
        a803011.setOrnum(803011);
        a803011.setName("关联树表-查询");
        a803011.setMenid(8030L);
        a803011.setPerm("link:cate:query");
        apiList.add(a803011);

        SysPermApiEntity a803012 = new SysPermApiEntity();
        a803012.setId(803012L);
        a803012.setOrnum(803012);
        a803012.setName("关联树表-新增");
        a803012.setMenid(8030L);
        a803012.setPerm("link:cate:add");
        apiList.add(a803012);

        SysPermApiEntity a803013 = new SysPermApiEntity();
        a803013.setId(803013L);
        a803013.setOrnum(803013);
        a803013.setName("关联树表-修改");
        a803013.setMenid(8030L);
        a803013.setPerm("link:cate:edit");
        apiList.add(a803013);

        SysPermApiEntity a803014 = new SysPermApiEntity();
        a803014.setId(803014L);
        a803014.setOrnum(803014);
        a803014.setName("关联树表-删除");
        a803014.setMenid(8030L);
        a803014.setPerm("link:cate:remove");
        apiList.add(a803014);

        insertApis(apiList);

    }

    private void insertMenus(List<SysPermMenuEntity> list) {
        for (SysPermMenuEntity main : list) {
            main.setAvtag(true);
            if(main.getCatag()==null){
                main.setCatag(false);
            }
            if(main.getType()==null){
                main.setType("2");
            }
            if(main.getShtag()==null){
                main.setShtag(true);
            }
            if(main.getOutag()==null){
                main.setOutag(false);
            }
            main.setUptim(main.getCrtim());
        }
        menuRrepo.saveAll(list);
    }

    private void insertApis(List<SysPermApiEntity> list) {
        for (SysPermApiEntity api : list) {
            api.setAvtag(true);
            api.setUptim(api.getCrtim());
        }
        apiRepo.saveAll(list);
    }


}
