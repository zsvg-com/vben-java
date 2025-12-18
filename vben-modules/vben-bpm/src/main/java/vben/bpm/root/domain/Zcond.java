package vben.bpm.root.domain;

import lombok.Data;

@Data
public class Zcond {

    private Long proid;//流程实例ID

    private Long prdid;//流程定义ID

    private String busty;//类别

//    private boolean sttag=false;//开始标记
}
