package vben.jpa.demo.single.main;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.service.BaseMainService;


@Service
@RequiredArgsConstructor
public class DemoSingleMainService extends BaseMainService<DemoSingleMain> {

    private final DemoSingleMainRepo repo;

    @PostConstruct
    public void initDao() {
        super.setRepo(repo);
    }

}

