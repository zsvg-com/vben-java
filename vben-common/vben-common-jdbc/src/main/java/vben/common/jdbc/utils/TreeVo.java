package vben.common.jdbc.utils;

import lombok.Data;

import java.util.List;

@Data
public class TreeVo {

    private Long id;

    private String name;

    private Long pid;

    private String type;

    private Integer ornum;

    private List<TreeVo> children;

}
