package vben.base.sys.perm.role;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuVo {
    private Long id;

    private String name;

    private String icon;

    private String type;

    private Long pid;

    private List<ApiVo> apis=new ArrayList<>();
}
