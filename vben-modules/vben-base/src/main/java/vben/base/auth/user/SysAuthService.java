package vben.base.auth.user;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.sys.user.SysUserDao;
import vben.base.sys.menu.SysMenu;
import vben.base.sys.menu.SysMenuDao;
import vben.common.core.constant.Constants;
import vben.common.core.constant.SystemConstants;
import vben.common.core.utils.StrUtils;
import vben.common.redis.utils.RedisUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysAuthService {

    public List<RouterVo> getRouters(String userId) {
        List<RouterVo> routerVoList = RedisUtils.getCacheObject("rlist:" + userId);
        if(routerVoList == null) {
            if("u1".equals(userId)) {
                List<SysMenu> menuList = menuDao.findAll();
                routerVoList = buildMenus(menuList);
                RedisUtils.setCacheObject("rlist:" + userId, routerVoList);
                return routerVoList;
            }
            String oids = RedisUtils.getCacheObject("oids:" + userId);
            if(oids == null){
                oids = userDao.findOrgs(userId);
                RedisUtils.setCacheObject("oids:" + userId, oids);
            }
            List<SysMenu> menuList=menuDao.findListByOids(oids);
            routerVoList = buildMenus(menuList);
            RedisUtils.setCacheObject("rlist:" + userId, routerVoList);
        }
        return routerVoList;
    }

    private List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<>();
        for (SysMenu menu : menus) {
            String name = menu.getRouteName() + menu.getId();
            RouterVo router = new RouterVo();
            router.setHidden(!menu.getShtag());
            router.setName(name);
            router.setPath(menu.getPid()==0L?"/"+menu.getPath():menu.getPath());
            router.setComponent(menu.getComp());
            router.setQuery(menu.getParam());
            router.setMeta(new MetaVo(menu.getName(), menu.getIcon(), !menu.getCatag(), menu.getPath()));
            List<SysMenu> cMenus = menu.getChildren();
            if (CollUtil.isNotEmpty(cMenus) && "1".equals(menu.getType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
                if(menu.getPid()==0L){
                    router.setComponent("Layout");
                }else{
                    router.setComponent("ParentView");
                }
            } else if (menu.isMenuFrame()) {
                String frameName = StrUtils.upperFirst(menu.getPath()) + menu.getId();
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComp());
                children.setName(frameName);
                children.setMeta(new MetaVo(menu.getName(), menu.getIcon(), !menu.getCatag(), menu.getPath()));
                children.setQuery(menu.getParam());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getPid().equals(Constants.TOP_PARENT_ID) && menu.isInnerLink()) {
                router.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<>();
                RouterVo children = new RouterVo();
                String routerPath = SysMenu.innerLinkReplaceEach(menu.getPath());
                String innerLinkName = StrUtils.upperFirst(routerPath) + menu.getId();
                children.setPath(routerPath);
                children.setComponent(SystemConstants.INNER_LINK);
                children.setName(innerLinkName);
                children.setMeta(new MetaVo(menu.getName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    private final SysUserDao userDao;

    private final SysMenuDao menuDao;

}
