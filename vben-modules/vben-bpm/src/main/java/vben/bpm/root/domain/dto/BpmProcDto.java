package vben.bpm.root.domain.dto;

import lombok.Data;

/**
 * 流转用信息
 */
@Data
public class BpmProcDto {

    /**
     * 流程实例ID
     */
    private Long proid;

    /**
     * 流程定义ID
     */
    private Long prdid;

    /**
     * 流程业务类型
     */
    private String busty;

    private boolean sherr=true;



//    /**
//     * 当前执行节点编号
//     */
//    private String facno;
//
//    /**
//     * 当前节点名称
//     */
//    private String facna;
//
//    /**
//     * 目标执行节点编号
//     */
//    private String tarno;



    public BpmProcDto() {

    }

//    public void setFacAndTar(Znode znode) {
//        this.facno = znode.getFacno();
//        this.facna = znode.getFacna();
//        this.tarno = znode.getTarno();
//    }

//    public BpmProcDto(Long proid, Long prdid, String busty) {
//        this.proid = proid;
//        this.prdid = prdid;
//        this.busty = busty;
//    }


}
