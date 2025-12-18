package vben.jpa.demo.single.cate;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.service.BaseCateService;


@Service
@RequiredArgsConstructor
public class DemoSingleCateService extends BaseCateService<DemoSingleCate> {

    private final DemoSingleCateRepo repo;

    @PostConstruct
    public void initDao() {
        super.setRepo(repo);
    }

}

