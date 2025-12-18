package vben.bpm.root.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 流程初始化Vo
 */
@Data
public class BpmInitVo implements Serializable {

    /**
     * 流程说明
     */
    private String intro;


    private List<BpmGoalVo> goals= new ArrayList<>();

//    /**
//     * 目标节点编号
//     */
//    private String tarno;
//
//    /**
//     * 目标节点名称
//     */
//    private String tarna;
//
//    /**
//     * 目标节点处理人名称
//     */
//    private String tamen;



}
