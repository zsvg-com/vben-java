package vben.common.jdbc.dto;

import lombok.Data;

import java.util.List;

@Data
public class Ltree {
    private Long id;

    private String name;

    private Long pid;

    private String type;

    private List<Ltree> children;

}
