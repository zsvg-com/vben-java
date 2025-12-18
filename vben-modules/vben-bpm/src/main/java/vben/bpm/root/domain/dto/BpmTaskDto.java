package vben.bpm.root.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class BpmTaskDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 任务ID
     */
    private Long id;

    /**
     * 节点ID
     */
    private Long nodid;

    /**
     * 节点编号
     */
    private String nodno;

    /**
     * 节点名称
     */
    private String nodna;

    /**
     * 应当处理人姓名
     */
    private String exuna;

    /**
     * 应当处理人ID
     */
    private String exuid;

    /**
     * 任务类型
     */
    private String type;

}
