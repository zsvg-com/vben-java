package vben.bpm.root.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BpmParamDto implements Serializable {
    /**
     * 任务ID
     */
    private Long id;

    /**
     * 节点ID
     */
    private String paval;
}
