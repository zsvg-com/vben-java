package vben.bpm.root.domain;

import lombok.Data;

@Data
public class Zproc {
    /**
     * 待办ID
     */
    private String todid;

    /**
     * 业务类型
     */
    private String busty;

    /**
     * 流程定义ID
     */
    private Long prdid;

    /**
     * 流程实例ID
     */
    private Long proid;

    /**
     * 流程主题
     */
    private String prona;

    /**
     * 当前节点ID
     */
    private Long nodid;

    /**
     * 当前节点编号
     */
    private String facno;

    /**
     * 当前节点名称
     */
    private String facna;

    /**
     * 目标节点编号
     */
    private String tarno;

    /**
     * 目标节点名称
     */
    private String tarna;

    /**
     * 驳回标记，驳回的节点通过后直接返回本节点
     */
    private Boolean retag=true;

    /**
     * 驳回后的流程重新提交时的bpm_proc_param的id
     */
    private Long bacid;

    /**
     * 转办标记，流程重新流经本节点时，直接由转办人员处理
     */
    private Boolean tutag=false;

    /**
     * 转办人员ID
     */
    private String tuuid;

    /**
     * 沟通标记，是否显示意见
     */
    private Boolean cotag=false;

    /**
     * 沟通人员IDS
     */
    private String coids;

    /**
     * 取消沟通的task_id
     */
    private String ccids;

    /**
     * 任务ID
     */
    private Long tasid;

    /**
     * 任务类型
     */
    private String tasty;

    /**
     * 操作：处理意见
     */
    private String opnot;

    /**
     * 操作：紧急程度
     */
    private String opurg;

    /**
     * 操作key:pass, reject
     */
    private String opkey;

    /**
     * 操作名称:通过，驳回到谁，沟通谁
     */
    private String opinf;

    /**
     * 优化过的可解析的的xml
     */
    private String exxml;

    /**
     * 当前处理人ID
     */
    private String hauid;

    /**
     * 流程发起人ID
     */
    private String inuid;

    /**
     * 应处理人ID
     */
    private String exuid;

    /**
     * 附件IDS
     */
    private String atids;

    public Zproc() {
    }

    public Zproc(Long proid) {
        this.proid = proid;
    }

    public Zproc(Long proid, String prona) {
        this.proid = proid;
        this.prona = prona;
    }
}
