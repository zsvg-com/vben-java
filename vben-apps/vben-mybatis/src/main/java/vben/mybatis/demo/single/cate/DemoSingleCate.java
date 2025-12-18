package vben.mybatis.demo.single.cate;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vben.common.mybatis.core.domain.BaseCateEntity;

import java.io.Serial;

/**
 * 单一树表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("demo_single_cate")
public class DemoSingleCate extends BaseCateEntity {

    @Serial
    private static final long serialVersionUID = 1L;

}
