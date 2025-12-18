package vben.base.mon.job.main;

import org.springframework.stereotype.Component;
import vben.base.mon.job.root.IJob;
import vben.base.mon.job.root.IJobGroup;
import vben.common.core.utils.DateUtils;

@IJobGroup
@Component
public class MonJobDemo
{
    private static boolean usingFlag = false;

    @IJob(code="demo1",cron = "0/10 * 0-23 * * ?", name = "10秒执行一次的DEMO1")
    public void demo1()
    {
        System.out.println("定时任务-demo1:" + DateUtils.now());
    }

    @IJob(code="demo2",cron = "0/20 * 0-23 * * ?", name = "20秒执行一次的DEMO2")
    public void demo2()
    {
        int i=1/0;
        if (!usingFlag)
        {
            usingFlag = true;
            try
            {
                System.out.println("定时任务-demo2:" + DateUtils.now());
            } finally
            {
                usingFlag = false;
            }
        }

    }
}
