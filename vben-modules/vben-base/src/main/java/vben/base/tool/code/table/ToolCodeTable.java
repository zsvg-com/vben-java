package vben.base.tool.code.table;

import lombok.Data;
import org.springframework.data.annotation.Id;
import vben.base.tool.code.field.ToolCodeField;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 代码生成-表格
 */
@Data
public class ToolCodeTable {

    /**
     * 主键ID
     */
    @Id
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 可用标记
     */
    private Boolean avtag = true;

    /**
     * 创建人ID
     */
    private String cruid;

    /**
     * 创建人姓名
     */
    private String cruna;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 更新人ID
     */
    private String upuid;

    /**
     * 更新人姓名
     */
    private String upuna;

    /**
     * 更新时间
     */
    private Date uptim;

    /**
     * 备注
     */
    private String notes;

    /**
     * 实体类
     */
    private String bunam;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 表描述
     */
    private String remark;

    /**
     * 继承基类
     */
    private String baent;

    /**
     * 基础包名
     */
    private String panam;

    /**
     * orm类型
     */
    private String ortyp;

    /**
     * 编辑页类型
     */
    private String edtyp;

    /**
     * 上级菜单ID
     */
    private String pmeid;

    /**
     *每行列数
     */
    private Integer pecol;

    /**
     * 字段列表
     */
    private List<ToolCodeField> fields = new ArrayList<>();

    /**
     * 新增按钮
     */
    private Boolean addbt;

    /**
     * 删除按钮
     */
    private Boolean delbt;

    /**
     * 导入按钮
     */
    private Boolean impbt;

    /**
     * 导出按钮
     */
    private Boolean expbt;

    /**
     * 路由类型
     */
    private String rotyp;

    /**
     * 排序字段
     */
    private String orfie;

}
