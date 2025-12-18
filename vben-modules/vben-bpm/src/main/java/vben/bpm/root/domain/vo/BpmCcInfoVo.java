package vben.bpm.root.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BpmCcInfoVo implements Serializable {

    private Long id;

    private String name;

    private String tasid;
}
