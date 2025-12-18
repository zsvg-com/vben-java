package vben.base.mon.job.root;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.util.ReflectionUtils;
import vben.base.mon.job.log.MonJobLog;
import vben.base.mon.job.log.MonJobLogDao;
import vben.base.mon.job.main.MonJobMain;
import vben.common.core.utils.DateUtils;
import vben.common.core.utils.IdUtils;
import vben.common.core.utils.SpringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

public class MonJobFactory implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        MonJobMain job = (MonJobMain) context.getMergedJobDataMap().get("job");
        Method method = ReflectionUtils.findMethod(SpringUtils.getBean(job.getJgroup()).getClass(), job.getJid());
        MonJobLog log = new MonJobLog();
        log.setId(IdUtils.getSnowflakeNextIdStr());
        MonJobLogDao syJobMlogDao= SpringUtils.getBean("monJobLogDao");
        log.setName(job.getName());
        long start = System.currentTimeMillis();
        try {
            log.setSttim(DateUtils.now());
            method.invoke(SpringUtils.getBean(job.getJgroup()));
            log.setRet("成功");
            log.setMsg("用时：" + (System.currentTimeMillis() - start) / 1000 + "秒");
            log.setEntim(DateUtils.now());
            syJobMlogDao.insert(log);
        } catch (Exception e) {
            System.err.println("定时任务运行失败");
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            log.setRet("失败");
            String message = sw.toString();
            if (message.length() >= 5000)
            {
                log.setMsg(message.substring(0, 5000));
            } else
            {
                log.setMsg(message);
            }
            log.setEntim(DateUtils.now());
            syJobMlogDao.insert(log);
        }
    }


}
