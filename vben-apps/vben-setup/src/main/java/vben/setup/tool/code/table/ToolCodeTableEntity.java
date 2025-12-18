package vben.setup.tool.code.table;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vben.common.jpa.entity.BaseMainEntity;
import vben.setup.tool.code.field.ToolCodeFieldEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Schema(description = "代码生成-表格")
@Table(name = "tool_code_table")
public class ToolCodeTableEntity extends BaseMainEntity {

    @Column(length = 64)
    @Schema(description = "实体类")
    private String bunam;//业务名

    @Schema(description = "排序号")
    private Integer ornum;

    @Column(length = 64)
    @Schema(description = "表描述")
    private String remark;

    @Column(length = 64)
    @Schema(description = "继承基类")
    private String baent;

    @Column(length = 32)
    @Schema(description = "基础包名")
    private String panam;

    @Column(length = 32)
    @Schema(description = "orm类型")
    private String ortyp;

    @Column(length = 32)
    @Schema(description = "编辑页类型")
    private String edtyp;

    @Column(length = 32)
    @Schema(description = "所属门户ID")
    private String porid;

    @Column(length = 32)
    @Schema(description = "上级菜单ID")
    private String pmeid;

    @Schema(description = "每行列数")
    private Integer pecol;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tabid")
    @OrderBy("ornum ASC")
    @Schema(description = "字段信息")
    private List<ToolCodeFieldEntity> fields = new ArrayList<>();

    @Schema(description = "新增按钮")
    private Boolean addbt;

    @Schema(description = "删除按钮")
    private Boolean delbt;

    @Schema(description = "导入按钮")
    private Boolean impbt;

    @Schema(description = "导出按钮")
    private Boolean expbt;

    @Column(length = 32)
    @Schema(description = "路由类型")
    private String rotyp;

    @Column(length = 32)
    @Schema(description = "排序字段")
    private String orfie;

}
