package vben.bpm.root.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 流程初始化Vo
 */
@Data
public class BpmGoalVo implements Serializable {

    /**
     * 目标节点编号
     */
    private String tarno;


    /**
     * 目标节点名称
     */
    private String tarna;

    /**
     * 目标节点处理人名称
     */
    private String tamen;



}
