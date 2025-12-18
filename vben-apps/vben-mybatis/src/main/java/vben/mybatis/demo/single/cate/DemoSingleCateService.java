package vben.mybatis.demo.single.cate;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.mybatis.core.service.BaseCateService;

@Service
@RequiredArgsConstructor
public class DemoSingleCateService extends BaseCateService<DemoSingleCate> {

    private final DemoSingleCateMapper mapper;

    @PostConstruct
    public void initDao() {
        super.setMapper(mapper);
    }

}
