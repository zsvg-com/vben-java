package vben.bpm.org.tree;

import lombok.Data;
import vben.bpm.org.role.BpmOrgRole;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BpmOrgTree {

    /**
     * 主键
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 分类ID
     */
    private String catid;

    /**
     * 可用标记
     */
    private Boolean avtag = true;

    /**
     * 创建人
     */
    private String cruid;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 更新人
     */
    private String upuid;

    /**
     * 更新时间
     */
    private Date uptim;

    /**
     * 备注
     */
    private String notes;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 包含角色
     */
    private List<BpmOrgRole> roles=new ArrayList<>();

}
