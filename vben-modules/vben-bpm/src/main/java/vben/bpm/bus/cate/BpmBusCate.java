package vben.bpm.bus.cate;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 流程分类
 */
@Data
public class BpmBusCate {

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
    private List<BpmBusCate> children = new ArrayList<>();

    /**
     * 是否可用
     */
    private Boolean avtag;

    /**
     * 排序号
     */
    private Integer ornum;

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
     * 备注
     */
    private String notes;

}
