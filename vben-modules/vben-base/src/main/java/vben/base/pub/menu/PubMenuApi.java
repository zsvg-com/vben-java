package vben.base.pub.menu;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vben.base.sys.menu.SysMenu;
import vben.base.sys.menu.SysMenuDao;
import vben.base.sys.user.SysUserDao;
import vben.common.core.constant.Constants;
import vben.common.core.constant.SystemConstants;
import vben.common.core.domain.R;
import vben.common.core.utils.StrUtils;
import vben.common.redis.utils.RedisUtils;
import vben.common.satoken.utils.LoginHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PubMenuApi {

    private final SysUserDao userDao;

    private final SysMenuDao menuDao;

    @GetMapping("/pub/menus")
    public R<List<RouterVo>> getRouters() {
        String userId=LoginHelper.getUserId();
        List<RouterVo> routerVoList = RedisUtils.getCacheObject("rlist:" + userId);
        if(routerVoList == null) {
            if("u1".equals(userId)) {
                List<SysMenu> menuList = menuDao.findAll();
                routerVoList = buildMenus(menuList);
                RedisUtils.setCacheObject("rlist:" + userId, routerVoList);
                return R.ok(routerVoList);
            }
            String aids = RedisUtils.getCacheObject("aids:" + userId);
            if(aids == null){
                aids = userDao.findAids(userId);
                RedisUtils.setCacheObject("aids:" + userId, aids);
            }
            List<SysMenu> menuList=menuDao.findListByAids(aids);
            routerVoList = buildMenus(menuList);
            RedisUtils.setCacheObject("rlist:" + userId, routerVoList);
        }
        return R.ok(routerVoList);

    }

    private List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<>();
        for (SysMenu menu : menus) {
            String name = menu.getRouteName() + menu.getId();
            RouterVo router = new RouterVo();
            router.setShtag(menu.getShtag());
            router.setName(name);
            router.setPath(menu.getPid()==0L?"/"+menu.getPath():menu.getPath());
            router.setComp(menu.getComp());
            router.setParam(menu.getParam());
            router.setMeta(new MetaVo(menu.getName(), menu.getIcon(), !menu.getCatag(), menu.getPath()));
            List<SysMenu> cMenus = menu.getChildren();
            if (CollUtil.isNotEmpty(cMenus) && "1".equals(menu.getType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
                if(menu.getPid()==0L){
                    router.setComp("Layout");
                }else{
                    router.setComp("ParentView");
                }
            } else if (menu.isMenuFrame()) {
                String frameName = StrUtils.upperFirst(menu.getPath()) + menu.getId();
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComp(menu.getComp());
                children.setName(frameName);
                children.setMeta(new MetaVo(menu.getName(), menu.getIcon(), !menu.getCatag(), menu.getPath()));
                children.setParam(menu.getParam());
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
                children.setComp(SystemConstants.INNER_LINK);
                children.setName(innerLinkName);
                children.setMeta(new MetaVo(menu.getName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

}
