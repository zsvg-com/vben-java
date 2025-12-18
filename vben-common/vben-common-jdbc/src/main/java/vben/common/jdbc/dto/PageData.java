package vben.common.jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

//分页数据返回用VO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageData {

    private Integer total;

    private List<Map<String, Object>> rows;

}
