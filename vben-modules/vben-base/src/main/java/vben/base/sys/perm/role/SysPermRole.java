package vben.base.sys.perm.role;

import lombok.Data;
import vben.common.jdbc.dto.SidName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 权限角色
 */
@Data
public class SysPermRole {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色类型
     */
    private Integer type;

    /**
     * 数据权限
     */
    private Integer scope;

    /**
     * 备注
     */
    private String notes;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 创建人ID
     */
    private String cruid;

    /**
     * 创建人姓名
     */
    private String cruna;

    /**
     * 更新时间
     */
    private Date uptim;

    /**
     * 更新人ID
     */
    private String upuid;

    /**
     * 更新人姓名
     */
    private String upuna;

    /**
     * 可用标记
     */
    private Boolean avtag;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 包含成员
     */
    private List<SidName> orgs = new ArrayList<>();

    /**
     * 包含菜单
     */
    private List<Long> menus = new ArrayList<>();

    /**
     * 包含接口
     */
    private List<Long> apis = new ArrayList<>();
}
