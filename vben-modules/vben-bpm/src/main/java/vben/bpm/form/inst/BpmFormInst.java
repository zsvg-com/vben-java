package vben.bpm.form.inst;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class BpmFormInst implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 表单json数据
     */
    private String json;


    /**
     * 表单定义ID
     */
    private Long fodid;


}
