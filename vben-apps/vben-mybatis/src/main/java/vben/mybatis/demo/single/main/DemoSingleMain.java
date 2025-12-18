package vben.mybatis.demo.single.main;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vben.common.mybatis.core.domain.BaseMainEntity;

import java.io.Serial;

/**
 * 单一主表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("demo_single_main")
public class DemoSingleMain extends BaseMainEntity {

    @Serial
    private static final long serialVersionUID = 1L;

}
