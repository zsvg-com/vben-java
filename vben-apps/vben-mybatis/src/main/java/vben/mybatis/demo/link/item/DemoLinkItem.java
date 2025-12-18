package vben.mybatis.demo.link.item;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 关联子表
 */
@Data
@TableName("demo_link_item")
public class DemoLinkItem {

    /**
     * 主键ID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 主表ID
     */
    private Long maiid;

    /**
     * 子项目名称
     */
    private String name;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 备注
     */
    private String notes;


}
