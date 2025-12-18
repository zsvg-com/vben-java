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
@Table(name = "bpm_todo_done")
@Schema(description = "流程待办完结")
public class BpmTodoDoneEntity {

    @Id
    @Column(length = 32)
    @Schema(description = "主键ID")
    private String id;

    @Column(length = 32)
    @Schema(description = "待办id")
    private String todid;

    @Column(length = 36)
    @Schema(description = "用户id")
    private String useid;

    @Column(updatable = false)
    @Schema(description = "完成时间")
    private Date entim = new Date();
}
