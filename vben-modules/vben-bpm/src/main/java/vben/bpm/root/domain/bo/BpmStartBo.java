package vben.bpm.root.domain.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class BpmStartBo {

    /**
     * 紧急程度
     */
    @NotNull(message = "紧急程度不能为空")
    private String opurg;//1紧急 2急 3一般

    /**
     * 处理意见
     */
    private String opnot;

    /**
     * 附件IDS
     */
    private String atids;


}
