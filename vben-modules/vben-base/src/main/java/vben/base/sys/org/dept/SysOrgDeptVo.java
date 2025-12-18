package vben.base.sys.org.dept;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysOrgDeptVo {

    private String id;

    private String name;

    private String pid;

    private String type;

    private List<SysOrgDeptVo> children;

    private Date crtim;

    private Date uptim;

    private String notes;

    private Integer ornum;

    private String cruna;

    private String upuna;

}
