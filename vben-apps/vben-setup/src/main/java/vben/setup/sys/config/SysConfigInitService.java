package vben.setup.sys.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 部门初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SysConfigInitService {

    public void init() {
        List<SysConfigEntity> list = new ArrayList<>();
        SysConfigEntity c1 = new SysConfigEntity();
        c1.setId(1L);
        c1.setName("用户管理-账号初始密码");
        c1.setKenam("sys.user.initPassword");
        c1.setKeval("123456");
        c1.setIntag(true);
        list.add(c1);

        SysConfigEntity c2 = new SysConfigEntity();
        c2.setId(2L);
        c2.setName("账号自助-是否开启用户注册功能");
        c2.setKenam("sys.account.registerUser");
        c2.setKeval("false");
        c2.setIntag(true);
        list.add(c2);

        insert(list);
    }


    private final SysConfigRepo repo;

    private void insert(List<SysConfigEntity> list) {
        for (SysConfigEntity config : list) {
            config.setCrtim(new Date());
            config.setUptim(config.getCrtim());
            config.setAvtag(true);
        }
        repo.saveAll(list);
    }


}
