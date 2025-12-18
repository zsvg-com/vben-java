package vben.bpm.root.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BpmRefuseInfoVo implements Serializable {

    private String refno;

    private String refna;

    private String exuid;

    private Date crtim;


}
