package vben.setup.sys.notice;

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
public class SysNoticeInitService {

    public void init() {
        List<SysNoticeEntity> list = new ArrayList<>();
        SysNoticeEntity n1 = new SysNoticeEntity();
        n1.setId(1L);
        n1.setName("系统停机公告");
        n1.setCont("系统将于今天晚上20点到22点进行停机维护，请提前做好工作安排");
        n1.setOrnum(1);
        n1.setType(1);
        list.add(n1);

        insert(list);
    }


    private final SysNoticeRepo repo;

    private void insert(List<SysNoticeEntity> list) {
        for (SysNoticeEntity notice : list) {
            notice.setCrtim(new Date());
            notice.setUptim(notice.getCrtim());
            notice.setAvtag(true);
        }
        repo.saveAll(list);
    }

}
