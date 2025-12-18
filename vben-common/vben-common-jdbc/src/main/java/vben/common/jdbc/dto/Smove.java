package vben.common.jdbc.dto;

import lombok.Data;

/**
 * 树节点移动对象
 */
@Data
public class Smove {

    /**
     * 拖动节点ID
     */
    private String draid;

    /**
     * 放下时目标节点ID
     */
    private String droid;

    /**
     * 移动类型
     */
    private String type;

}
