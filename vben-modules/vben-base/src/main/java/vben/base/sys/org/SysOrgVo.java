package vben.base.sys.org;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysOrgVo {

    private String id;

    private String name;

    private String pid;

    private String type;

    private List<SysOrgVo> children;

    private Date crtim;

    private Date uptim;

    private String notes;

    private Integer ornum;

    private String cruna;

    private String upuna;

}
