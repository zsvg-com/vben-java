package vben.mybatis.demo.single.main;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.mybatis.core.service.BaseMainService;

@Service
@RequiredArgsConstructor
public class DemoSingleMainService extends BaseMainService<DemoSingleMain> {

    private final DemoSingleMainMapper mapper;

    @PostConstruct
    public void initDao() {
        super.setMapper(mapper);
    }

}
