package vben.bpm.proc.cond;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class BpmProcCond implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;


    /**
     * 参数值
     */
    private String cond;
}
