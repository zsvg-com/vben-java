package vben.setup.demo.single;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import vben.common.jpa.entity.BaseMainEntity;

/**
 * 单一主表案
 */
@Entity
@Getter
@Setter
@Schema(description = "单一主表")
public class DemoSingleMain extends BaseMainEntity {



}
