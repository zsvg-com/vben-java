package vben.jpa.demo.link.cate;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.service.BaseCateService;


@Service
@RequiredArgsConstructor
public class DemoLinkCateService extends BaseCateService<DemoLinkCate> {

    private final DemoLinkCateRepo repo;

    @PostConstruct
    public void initDao() {
        super.setRepo(repo);
    }

}

