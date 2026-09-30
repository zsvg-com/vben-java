package vben.setup.demo.single;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vben.common.jpa.entity.BaseCateEntity;

/**
 * 单一树表
 */
@Entity
@Getter
@Setter
@Schema(description = "单一树表")
@Table(name="demo_single_cate")
public class DemoSingleCateEntity extends BaseCateEntity {


}
