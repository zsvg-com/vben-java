package vben.setup.bpm.todo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "bpm_todo_main")
@Schema(description = "流程待办")
public class BpmTodoMainEntity {

    @Id
    @Column(length = 32)
    @Schema(description = "主键ID")
    private String id;

    @Column(length = 256)
    @Schema(description = "主题")
    private String name;

    @Column(length = 8)
    @Schema(description = "类型")
    private String type;

    @Column(length = 8)
    @Schema(description = "紧急度")
    private String grade;

    @Column(length = 128)
    @Schema(description = "业务分类")
    private String busca;

    @Column(length = 32)
    @Schema(description = "业务ID")
    private String busid;

    @Column(length = 512)
    @Schema(description = "待办链接")
    private String link;

    @Schema(description = "备注")
    private String notes;

    @Column(updatable = false)
    @Schema(description = "创建时间")
    private Date crtim = new Date();

    @Column(length = 36,updatable = false)
    @Schema(description = "创建人ID")
    private String cruid;

//    @ManyToMany
//    @JoinTable(name = "bpm_todo_user", joinColumns = {@JoinColumn(name = "tid")},
//            inverseJoinColumns = {@JoinColumn(name = "uid")})
//    private List<SysOrg> tamen;

}
