package vben.base.pub.org;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.Stree;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;

import java.util.List;

@RestController
@RequestMapping("pub/org")
@RequiredArgsConstructor
public class PubOrgApi {

    /**
     * 组织树状查询
     * @return 组织树状集合
     */
    @GetMapping("tree")
    public R<List<Stree>> tree() {
        Sqler sqler = new Sqler("sys_org");
        sqler.addSelect("pid");
        sqler.addWhere("t.avtag = "+ Db.True);
        sqler.addOrder("t.ornum");
        return R.ok(jdbcHelper.findStreeList(sqler));
    }

    private final JdbcHelper jdbcHelper;

}
