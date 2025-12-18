package vben.mybatis.demo.link.cate;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.mybatis.core.service.BaseCateService;

@Service
@RequiredArgsConstructor
public class DemoLinkCateService extends BaseCateService<DemoLinkCate> {

    private final DemoLinkCateMapper mapper;

    @PostConstruct
    public void initDao() {
        super.setMapper(mapper);
    }

}
