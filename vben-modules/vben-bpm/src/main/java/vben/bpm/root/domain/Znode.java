package vben.bpm.root.domain;

import lombok.Data;

@Data
public class Znode {

    private Long nodid;//节点ID

    private String facno;//当前节点编号:N1,N2

    private String facna;//当前节点名称

    private String facty;//当前节点类型

    private String tarno;//目标节点编号

    private String tarna;//目标节点名称

    private String exuids;//应处理人

    private String flway;//流转方式

    public Znode(String facno) {
        this.facno = facno;
    }

    public Znode() {

    }
}
