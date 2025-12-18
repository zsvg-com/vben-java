package vben.mybatis.demo.link.mid;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@TableName("demo_link_main_org")
@NoArgsConstructor
@AllArgsConstructor
public class DemoLinkMainOrg {

    private Long mid;

    private String oid;

}
