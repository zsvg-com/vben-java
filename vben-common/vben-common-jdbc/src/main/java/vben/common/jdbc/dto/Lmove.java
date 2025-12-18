package vben.common.jdbc.dto;

import lombok.Data;

/**
 * 树节点移动对象
 */
@Data
public class Lmove {

    /**
     * 拖动节点ID
     */
    private Long draid;

    /**
     * 放下时目标节点ID
     */
    private Long droid;

    /**
     * 移动类型
     */
    private String type;

}
