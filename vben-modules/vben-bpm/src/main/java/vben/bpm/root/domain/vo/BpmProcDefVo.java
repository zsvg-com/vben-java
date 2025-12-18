package vben.bpm.root.domain.vo;


import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vben.bpm.proc.def.BpmProcDef;

import java.io.Serializable;

@Data
@AutoMapper(target = BpmProcDef.class)
public class BpmProcDefVo implements Serializable {

    /**
     * 主键
     */
    private Long id;

    private String dixml;
}
