package vben.setup.bpm.todo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "bpm_todo_user")
@Schema(description = "用户待办记录")
public class BpmTodoUserEntity {

    @Id
    @Column(length = 32)
    @Schema(description = "主键ID")
    private String id;

    @Column(length = 32)
    @Schema(description = "待办ID")
    private String todid;

    @Column(length = 36)
    @Schema(description = "用户ID")
    private String useid;
}
