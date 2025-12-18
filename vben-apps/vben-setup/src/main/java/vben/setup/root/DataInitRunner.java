package vben.setup.root;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 数据初始化
 *
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DataInitRunner implements ApplicationRunner {

    private final DataInitService dataInitService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        dataInitService.init();
        log.info("数据初始化");
    }

}
