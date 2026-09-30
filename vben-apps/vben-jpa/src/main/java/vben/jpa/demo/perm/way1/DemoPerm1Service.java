package vben.jpa.demo.perm.way1;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.service.BaseMainService;


@Service
@RequiredArgsConstructor
public class DemoPerm1Service extends BaseMainService<DemoPerm1> {

    private final DemoPerm1Repo repo;

    @PostConstruct
    public void initDao() {
        super.setRepo(repo);
    }

}

