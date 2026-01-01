package vben.base.sys.dept;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysDeptVo {

    private String id;

    private String name;

    private String pid;

    private String type;

    private List<SysDeptVo> children;

    private Date crtim;

    private Date uptim;

    private String notes;

    private Integer ornum;

    private String cruna;

    private String upuna;

}
