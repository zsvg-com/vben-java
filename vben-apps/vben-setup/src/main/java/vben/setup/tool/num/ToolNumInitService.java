package vben.setup.tool.num;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 部门初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ToolNumInitService {

    public void init() {
        ToolNumEntity num = new ToolNumEntity();
        num.setId("BUS");
        num.setName("流程编号");
        num.setNflag(true);
        num.setNulen(4);
        num.setNumod("YYYYMMDD");
        num.setNupre("BUS");
        num.setOrnum(1);
        insert(num);
    }

    private final ToolNumRepo numRepo;


    private void insert(ToolNumEntity num) {
        num.setAvtag(true);
        num.setUptim(num.getCrtim());
        numRepo.save(num);
    }

}
