package vben.setup.demo.root;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.setup.sys.api.SysApiEntity;
import vben.setup.sys.api.SysApiRepo;
import vben.setup.sys.menu.SysMenuEntity;
import vben.setup.sys.menu.SysMenuRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * 案例初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class DemoInitService {

    private final SysMenuRepo menuRrepo;

    private final SysApiRepo apiRepo;

    public void init() {
        List<SysMenuEntity> menuList = new ArrayList<>();
        SysMenuEntity m8000 = new SysMenuEntity();
        m8000.setId(8000L);
        m8000.setIcon("hugeicons:star");
        m8000.setName("使用案例");
        m8000.setOrnum(8000);
        m8000.setPath("demo");
        m8000.setPid(0L);
        m8000.setType("1");
        m8000.setComp("Layout");
        menuList.add(m8000);

        SysMenuEntity m8010 = new SysMenuEntity();
        m8010.setId(8010L);
        m8010.setComp("demo/single/main/index");
        m8010.setIcon("pajamas:work-item-requirement");
        m8010.setName("单一主表案例");
        m8010.setOrnum(8010);
        m8010.setPath("single");
        m8010.setPid(8000L);
        menuList.add(m8010);

        SysMenuEntity m8020 = new SysMenuEntity();
        m8020.setId(8020L);
        m8020.setComp("demo/single/cate/index");
        m8020.setIcon("pajamas:work-item-requirement");
        m8020.setName("单一树表案例");
        m8020.setOrnum(8020);
        m8020.setPath("singlec");
        m8020.setPid(8000L);
        menuList.add(m8020);

        SysMenuEntity m8030 = new SysMenuEntity();
        m8030.setId(8030L);
        m8030.setComp("demo/link/index");
        m8030.setIcon("pajamas:work-item-requirement");
        m8030.setName("关联主分子案例");
        m8030.setOrnum(8030);
        m8030.setPath("link");
        m8030.setPid(8000L);
        menuList.add(m8030);

        SysMenuEntity m8040 = new SysMenuEntity();
        m8040.setId(8040L);
        m8040.setComp("demo/perm/way1/index");
        m8040.setIcon("pajamas:work-item-requirement");
        m8040.setName("数据权限案例1");
        m8040.setOrnum(8040);
        m8040.setPath("perm1");
        m8040.setPid(8000L);
        menuList.add(m8040);

        SysMenuEntity m8050 = new SysMenuEntity();
        m8050.setId(8050L);
        m8050.setComp("demo/perm/way2/index");
        m8050.setIcon("pajamas:work-item-requirement");
        m8050.setName("数据权限案例2");
        m8050.setOrnum(8050);
        m8050.setPath("perm2");
        m8050.setPid(8000L);
        menuList.add(m8050);

        insertMenus(menuList);

        List<SysApiEntity> apiList = new ArrayList<>();

        SysApiEntity a801001 = new SysApiEntity();
        a801001.setId(801001L);
        a801001.setOrnum(801001);
        a801001.setName("单一主表-查询");
        a801001.setMenid(8010L);
        a801001.setPerm("demo:single:query");
        apiList.add(a801001);

        SysApiEntity a801002 = new SysApiEntity();
        a801002.setId(801002L);
        a801002.setOrnum(801002);
        a801002.setName("单一主表-新增");
        a801002.setMenid(8010L);
        a801002.setPerm("demo:single:add");
        apiList.add(a801002);

        SysApiEntity a801003 = new SysApiEntity();
        a801003.setId(801003L);
        a801003.setOrnum(801003);
        a801003.setName("单一主表-修改");
        a801003.setMenid(8010L);
        a801003.setPerm("demo:single:edit");
        apiList.add(a801003);

        SysApiEntity a801004 = new SysApiEntity();
        a801004.setId(801004L);
        a801004.setOrnum(801004);
        a801004.setName("单一主表-删除");
        a801004.setMenid(8010L);
        a801004.setPerm("demo:single:delete");
        apiList.add(a801004);

        SysApiEntity a802001 = new SysApiEntity();
        a802001.setId(802001L);
        a802001.setOrnum(802001);
        a802001.setName("单一树表-查询");
        a802001.setMenid(8020L);
        a802001.setPerm("demo:singlec:query");
        apiList.add(a802001);

        SysApiEntity a802002 = new SysApiEntity();
        a802002.setId(802002L);
        a802002.setOrnum(802002);
        a802002.setName("单一树表-新增");
        a802002.setMenid(8020L);
        a802002.setPerm("demo:singlec:add");
        apiList.add(a802002);

        SysApiEntity a802003 = new SysApiEntity();
        a802003.setId(802003L);
        a802003.setOrnum(802003);
        a802003.setName("单一树表-修改");
        a802003.setMenid(8020L);
        a802003.setPerm("demo:singlec:edit");
        apiList.add(a802003);

        SysApiEntity a802004 = new SysApiEntity();
        a802004.setId(802004L);
        a802004.setOrnum(802004);
        a802004.setName("单一树表-删除");
        a802004.setMenid(8020L);
        a802004.setPerm("demo:singlec:delete");
        apiList.add(a802004);

        SysApiEntity a803001 = new SysApiEntity();
        a803001.setId(803001L);
        a803001.setOrnum(803001);
        a803001.setName("关联主表-查询");
        a803001.setMenid(8030L);
        a803001.setPerm("demo:link:query");
        apiList.add(a803001);

        SysApiEntity a803002 = new SysApiEntity();
        a803002.setId(803002L);
        a803002.setOrnum(803002);
        a803002.setName("关联主表-新增");
        a803002.setMenid(8030L);
        a803002.setPerm("demo:link:add");
        apiList.add(a803002);

        SysApiEntity a803003 = new SysApiEntity();
        a803003.setId(803003L);
        a803003.setOrnum(803003);
        a803003.setName("关联主表-修改");
        a803003.setMenid(8030L);
        a803003.setPerm("demo:link:edit");
        apiList.add(a803003);

        SysApiEntity a803004 = new SysApiEntity();
        a803004.setId(803004L);
        a803004.setOrnum(803004);
        a803004.setName("关联主表-删除");
        a803004.setMenid(8030L);
        a803004.setPerm("demo:link:delete");
        apiList.add(a803004);

        SysApiEntity a803011 = new SysApiEntity();
        a803011.setId(803011L);
        a803011.setOrnum(803011);
        a803011.setName("关联树表-查询");
        a803011.setMenid(8030L);
        a803011.setPerm("demo:linkc:query");
        apiList.add(a803011);

        SysApiEntity a803012 = new SysApiEntity();
        a803012.setId(803012L);
        a803012.setOrnum(803012);
        a803012.setName("关联树表-新增");
        a803012.setMenid(8030L);
        a803012.setPerm("demo:linkc:add");
        apiList.add(a803012);

        SysApiEntity a803013 = new SysApiEntity();
        a803013.setId(803013L);
        a803013.setOrnum(803013);
        a803013.setName("关联树表-修改");
        a803013.setMenid(8030L);
        a803013.setPerm("demo:linkc:edit");
        apiList.add(a803013);

        SysApiEntity a803014 = new SysApiEntity();
        a803014.setId(803014L);
        a803014.setOrnum(803014);
        a803014.setName("数据权限案例-删除");
        a803014.setMenid(8030L);
        a803014.setPerm("demo:linkc:delete");
        apiList.add(a803014);

        SysApiEntity a804001 = new SysApiEntity();
        a804001.setId(804001L);
        a804001.setOrnum(804001);
        a804001.setName("数据权限案例1-查询");
        a804001.setMenid(8040L);
        a804001.setPerm("demo:perm1:query");
        apiList.add(a804001);

        SysApiEntity a804002 = new SysApiEntity();
        a804002.setId(804002L);
        a804002.setOrnum(804002);
        a804002.setName("数据权限案例1-新增");
        a804002.setMenid(8040L);
        a804002.setPerm("demo:perm1:add");
        apiList.add(a804002);

        SysApiEntity a804003 = new SysApiEntity();
        a804003.setId(804003L);
        a804003.setOrnum(804003);
        a804003.setName("数据权限案例1-修改");
        a804003.setMenid(8040L);
        a804003.setPerm("demo:perm1:edit");
        apiList.add(a804003);

        SysApiEntity a804004 = new SysApiEntity();
        a804004.setId(804004L);
        a804004.setOrnum(804004);
        a804004.setName("数据权限案例1-删除");
        a804004.setMenid(8040L);
        a804004.setPerm("demo:perm1:delete");
        apiList.add(a804004);

        SysApiEntity a805001 = new SysApiEntity();
        a805001.setId(805001L);
        a805001.setOrnum(805001);
        a805001.setName("数据权限案例2-查询");
        a805001.setMenid(8050L);
        a805001.setPerm("demo:perm2:query");
        apiList.add(a805001);

        SysApiEntity a805002 = new SysApiEntity();
        a805002.setId(805002L);
        a805002.setOrnum(805002);
        a805002.setName("数据权限案例2-新增");
        a805002.setMenid(8050L);
        a805002.setPerm("demo:perm2:add");
        apiList.add(a805002);

        SysApiEntity a805003 = new SysApiEntity();
        a805003.setId(805003L);
        a805003.setOrnum(805003);
        a805003.setName("数据权限案例2-修改");
        a805003.setMenid(8050L);
        a805003.setPerm("demo:perm2:edit");
        apiList.add(a805003);

        SysApiEntity a805004 = new SysApiEntity();
        a805004.setId(805004L);
        a805004.setOrnum(805004);
        a805004.setName("数据权限案例2-删除");
        a805004.setMenid(8050L);
        a805004.setPerm("demo:perm2:delete");
        apiList.add(a805004);

        insertApis(apiList);

    }

    private void insertMenus(List<SysMenuEntity> list) {
        for (SysMenuEntity main : list) {
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

    private void insertApis(List<SysApiEntity> list) {
        for (SysApiEntity api : list) {
            api.setAvtag(true);
            api.setUptim(api.getCrtim());
        }
        apiRepo.saveAll(list);
    }


}
