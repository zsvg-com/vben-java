package vben.jpa.demo.link.main;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.service.BaseMainService;


@Service
@RequiredArgsConstructor
public class DemoLinkMainService extends BaseMainService<DemoLinkMain> {

    private final DemoLinkMainRepo repo;

    @PostConstruct
    public void initDao() {
        super.setRepo(repo);
    }

}

