package vben.mybatis.demo.single.main;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.mybatis.core.service.BaseMainService;

@Service
@RequiredArgsConstructor
public class DemoSingleService extends BaseMainService<DemoSingle> {

    private final DemoSingleMapper mapper;

    @PostConstruct
    public void initDao() {
        super.setMapper(mapper);
    }

}
