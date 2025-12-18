package vben.mybatis.demo.link.main;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vben.common.mybatis.core.domain.BaseMainEntity;
import vben.common.mybatis.core.domain.SysOrg;
import vben.mybatis.demo.link.item.DemoLinkItem;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * 关联主表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("demo_link_main")
public class DemoLinkMain extends BaseMainEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    private String catid;

    /**
     * 行项目列表
     */
    @TableField(exist = false)
    private List<DemoLinkItem> items = new ArrayList<>();


    @TableField(exist = false)
    private List<SysOrg> orgs;

}
