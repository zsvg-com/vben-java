package vben.base.mon.job.root;

import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import vben.base.mon.job.main.MonJobMain;
import vben.base.mon.job.main.MonJobMainService;

import java.util.List;

@Configuration
@Slf4j
public class MonJobListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private MonJobHandler handler;

    @Autowired
    private MonJobMainService monJobMainService;


    //初始启动quartz
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<MonJobMain> dbJobList = monJobMainService.getJobList();
        try {
            handler.startAllJobs(dbJobList);
//            log.info("----SYS定时调度任务开启----");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
