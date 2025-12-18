package vben.base.mon.job.root;


import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vben.base.mon.job.main.MonJobMain;
import vben.base.mon.job.main.MonJobMainDao;

import java.util.Date;
import java.util.List;

//任务调度处理
@Component
@RequiredArgsConstructor
public class MonJobHandler {
    // 任务调度
    @Autowired
    private Scheduler scheduler;

    //开始执行所有任务
    public void startAllJobs(List<MonJobMain> jobList) throws SchedulerException {
        for (int i = 0; i < jobList.size(); i++) {
            if (jobList.get(i).getAvtag()) {
                String[] arr= jobList.get(i).getReurl().split("/");
                jobList.get(i).setJgroup(arr[0]);
                jobList.get(i).setJid(arr[1]);
                initJob(scheduler, jobList.get(i));
            }
        }
        scheduler.start();
    }

    private void initJob(Scheduler scheduler, MonJobMain job) throws SchedulerException {
        // 通过JobBuilder构建JobDetail实例，JobDetail规定只能是实现Job接口的实例
        // JobDetail 是具体Job实例
        JobDetail jobDetail = JobBuilder.newJob(MonJobFactory.class).withIdentity(job.getJid(), job.getJgroup()).build();
        // 基于表达式构建触发器
        jobDetail.getJobDataMap().put("job", job);
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
        // CronTrigger表达式触发器 继承于Trigger
        // TriggerBuilder 用于构建触发器实例
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(job.getJid(), job.getJgroup())
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    //禁用任务
    public void stopJob(MonJobMain job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getJid(), job.getJgroup());
        scheduler.deleteJob(jobKey);
        job.setAvtag(false);
        dao.update(job);
    }

    //启用任务
    public void startJob(MonJobMain job) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJid(), job.getJgroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (null == trigger) {
            JobDetail jobDetail = JobBuilder.newJob(MonJobFactory.class)
                    .withIdentity(job.getJid(), job.getJgroup()).build();
            jobDetail.getJobDataMap().put("job", job);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJid(), job.getJgroup())
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey, trigger);
        }
        job.setAvtag(true);
        dao.update(job);
    }

    //立即执行一次
    public void onceJob(MonJobMain job) throws SchedulerException, InterruptedException {
        if (job.getAvtag()) {
            JobKey jobKey = JobKey.jobKey(job.getJid(), job.getJgroup());
            scheduler.triggerJob(jobKey);
        } else {
            JobDetail jobDetail = JobBuilder.newJob(MonJobFactory.class)
                    .withIdentity(job.getJid(), job.getJgroup()).build();
            jobDetail.getJobDataMap().put("job", job);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJid(), job.getJgroup())
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
            JobKey jobKey = JobKey.jobKey(job.getJid(), job.getJgroup());
            scheduler.pauseJob(jobKey);
            scheduler.triggerJob(jobKey);
            Thread.sleep(100);
            scheduler.deleteJob(jobKey);
        }

    }

    //获取Job信息
    public String getJobInfo(String name, String group) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time:%s,state:%s", cronTrigger.getCronExpression(),
                scheduler.getTriggerState(triggerKey).name());
    }

    //修改某个任务的执行时间
    public boolean modifyJob(String name, String group, String cron) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldTime = cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(cron)) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                    .withSchedule(cronScheduleBuilder).build();
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }
        return date != null;
    }

    // 暂停所有任务
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    //暂停某个任务
    public void pauseJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.pauseJob(jobKey);
    }

    //恢复所有任务
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    //恢复某个任务
    public void resumeJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.resumeJob(jobKey);
    }

    // 删除某个任务
    public void deleteJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.deleteJob(jobKey);
    }

    private final MonJobMainDao dao;


}
