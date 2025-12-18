package vben.common.jdbc.dto;

import lombok.Data;

import java.util.List;

@Data
public class Stree {
    private String id;

    private String name;

    private String pid;

    private String type;

    private List<Stree> children;

}
