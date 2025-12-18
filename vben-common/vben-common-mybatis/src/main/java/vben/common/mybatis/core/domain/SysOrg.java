package vben.common.mybatis.core.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SysOrg implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
    @TableField(fill = FieldFill.INSERT)
    private String id;

    /**
     * 名称
     */
    @TableField(fill = FieldFill.INSERT)
    private String name;

    /**
     * 类型
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer type;


}
