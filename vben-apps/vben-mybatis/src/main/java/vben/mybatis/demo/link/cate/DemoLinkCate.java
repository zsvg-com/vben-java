package vben.mybatis.demo.link.cate;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vben.common.mybatis.core.domain.BaseCateEntity;

import java.io.Serial;

/**
 * 关联分类表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("demo_link_cate")
public class DemoLinkCate extends BaseCateEntity {

    @Serial
    private static final long serialVersionUID = 1L;

}
