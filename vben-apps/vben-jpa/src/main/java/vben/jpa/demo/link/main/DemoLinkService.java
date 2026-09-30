package vben.jpa.demo.link.main;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.service.BaseMainService;


@Service
@RequiredArgsConstructor
public class DemoLinkService extends BaseMainService<DemoLink> {

    private final DemoLinkRepo repo;

    @PostConstruct
    public void initDao() {
        super.setRepo(repo);
    }

}

