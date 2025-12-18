package vben.jpa.demo.link.cate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vben.common.jpa.entity.BaseCateEntity;

/**
 * 关联分类表
 */
@Entity
@Getter
@Setter
@Schema(description = "关联分类表")
@Table(indexes = {@Index(columnList = "cruid"), @Index(columnList = "upuid")})
public class DemoLinkCate extends BaseCateEntity {


}
