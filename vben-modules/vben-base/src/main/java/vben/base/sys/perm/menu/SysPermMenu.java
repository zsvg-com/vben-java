package vben.base.sys.perm.menu;

import lombok.Data;
import vben.common.core.constant.Constants;
import vben.common.core.utils.StrUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 权限菜单
 */
@Data
public class SysPermMenu {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父ID
     */
    private Long pid;

    /**
     * 子菜单
     */
    private List<SysPermMenu> children = new ArrayList<>();

    /**
     * 是否可用
     */
    private Boolean avtag;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 类型
     */
    private String type;

    /**
     * 备注
     */
    private String notes;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 路由参数
     */
    private String param;

    /**
     * 组件路径
     */
    private String comp;

    /**
     * 显示标记
     */
    private Boolean shtag;

    /**
     * 缓存标记
     */
    private Boolean catag;

    /**
     * 外链标记
     */
    private Boolean outag;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 更新时间
     */
    private Date uptim;

    /**
     * 创建人ID
     */
    private String cruid;

    /**
     * 更新人ID
     */
    private String upuid;

    /**
     * 获取路由名称
     */
    public String getRouteName() {
        String routerName = StrUtils.upperFirst(path);
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame()) {
            routerName = StrUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 是否为内链组件
     */
    public boolean isInnerLink() {
        return outag && StrUtils.isUrl(path);
    }

    /**
     * 是否为菜单内部跳转
     */
    public boolean isMenuFrame() {
        return getPid() == 0L && "2".equals(type)&& !outag;
    }

    /**
     * 内链域名特殊字符替换
     */
    public static String innerLinkReplaceEach(String path) {
        return StrUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS, Constants.WWW, ".", ":"},
            new String[]{"", "", "", "/", "/"});
    }

}
