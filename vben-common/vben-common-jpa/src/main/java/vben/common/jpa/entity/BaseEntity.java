package vben.common.jpa.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


//简单Entity基类，仅提供id与name字段，如有租户需求，可加租户ID。
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @Column(length = 32)
    @Schema(description = "主键ID")
    protected Long id;

    @Column(length = 126)
    @Schema(description = "名称")
    protected String name;

}
