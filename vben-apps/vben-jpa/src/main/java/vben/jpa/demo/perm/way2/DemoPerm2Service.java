package vben.jpa.demo.perm.way2;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.service.BaseMainService;


@Service
@RequiredArgsConstructor
public class DemoPerm2Service extends BaseMainService<DemoPerm2> {

    private final DemoPerm2Repo repo;

    @PostConstruct
    public void initDao() {
        super.setRepo(repo);
    }

}

