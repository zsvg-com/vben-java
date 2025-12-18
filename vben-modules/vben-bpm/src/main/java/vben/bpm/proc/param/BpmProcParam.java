package vben.bpm.proc.param;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class BpmProcParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 参数Key
     */
    private String pakey;

    /**
     * 参数值
     */
    private String paval;

    /**
     * OFF类型
     */
    private String offty;

    /**
     * OFFID
     */
    private Long offid;

    /**
     * 流程ID
     */
    private Long proid;
}
