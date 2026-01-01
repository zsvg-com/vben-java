package vben.base.sys.menu;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysMenuVo {

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
     * 类型
     */
    private String type;

    /**
     * 子菜单
     */
    private List<SysMenuVo> children;

    /**
     * 备注
     */
    private String notes;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 创建时间
     */
    private Date crtim;

    /**
     * 更新时间
     */
    private Date uptim;

    /**
     * 创建人姓名
     */
    private String cruna;

    /**
     * 更新人姓名
     */
    private String upuna;

    /**
     * 是否显示
     */
    private Boolean shtag;

    /**
     * 是否缓存
     */
    private Boolean catag;

    /**
     * 是否缓存
     */
    private Boolean avtag;

    /**
     * 是否为iframe
     */
    private Boolean frtag;

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
     * 图标
     */
    private String icon;

}
