package vben.base.mon.job.main;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;
import vben.base.mon.job.root.MonJobHandler;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

/**
 * 定时任务管理
 */
@RestController
@RequestMapping("mon/job/main")
@RequiredArgsConstructor
public class MonJobMainApi {

    private final MonJobHandler handler;

    private final MonJobMainService service;

    /**
     * 定时任务分页查询
     * @param name 任务名称
     * @return 分页对象
     */
    @SaCheckPermission("monjob:main:query")
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("t.id,t.name,t.reurl,t.cron,t.avtag,t.code,t.crtim,t.notes,t.retyp", "mon_job_main");
        sqler.addLike("t.name", name);
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 定时任务详情查询
     * @param id 任务ID
     * @return 任务对象
     */
    @SaCheckPermission("monjob:main:query")
    @GetMapping("info/{id}")
    public R<MonJobMain> info(@PathVariable Long id) {
        MonJobMain main=service.findById(id);
        return R.ok(main);
    }

    /**
     * 定时任务修改
     * @param main 定时任务对象
     * @return 任务ID
     * @throws SchedulerException 调度异常
     */
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @SaCheckPermission("monjob:main:edit")
    @PutMapping
    public R<Long> put(@RequestBody MonJobMain main) throws SchedulerException {
        MonJobMain dbJob = service.findById(main.getId());
        if(dbJob.getAvtag()){
            String[] arr= dbJob.getReurl().split("/");
            dbJob.setJgroup(arr[0]);
            dbJob.setJid(arr[1]);
            handler.stopJob(dbJob);
        }
        service.update(main);
        if(main.getAvtag()){
            String[] arr2= main.getReurl().split("/");
            main.setJgroup(arr2[0]);
            main.setJid(arr2[1]);
            handler.startJob(main);
        }
        return R.ok(null,main.getId());
    }

    /**
     * 定时任务启动
     * @param ids 任务ID串
     * @throws SchedulerException 调度异常
     */
    @Log(title = "定时任务", businessType = BusinessType.OTHER)
    @SaCheckPermission("monjob:main:run")
    @PostMapping("start")
    public R<Void> start(String ids) throws SchedulerException {
        String[] idArr = ids.split(",");
        for (int i = 0; i < idArr.length; i++) {
            MonJobMain job = service.findById(Long.parseLong(idArr[i]));
            String[] arr= job.getReurl().split("/");
            job.setJgroup(arr[0]);
            job.setJid(arr[1]);
            handler.startJob(job);
        }
        return R.ok();
    }

    /**
     * 定时任务关闭
     * @param ids 任务ID串
     * @throws SchedulerException 调度异常
     */
    @Log(title = "定时任务", businessType = BusinessType.OTHER)
    @SaCheckPermission("monjob:main:run")
    @PostMapping("stop")
    public R<Void> stop(String ids) throws SchedulerException {
        String[] idArr = ids.split(",");
        for (int i = 0; i < idArr.length; i++) {
            MonJobMain job = service.findById(Long.parseLong(idArr[i]));
            String[] arr= job.getReurl().split("/");
            job.setJgroup(arr[0]);
            job.setJid(arr[1]);
            handler.stopJob(job);
        }
        return R.ok();
    }

    /**
     * 定时任务立即执行一次
     * @param id 任务ID
     * @throws InterruptedException 中断异常
     * @throws SchedulerException 调度异常
     */
    @Log(title = "定时任务", businessType = BusinessType.OTHER)
    @SaCheckPermission("monjob:main:run")
    @PostMapping("once")
    public R<Void> once(Long id) throws InterruptedException, SchedulerException {
        MonJobMain main=service.findById(id);
        String[] arr= main.getReurl().split("/");
        main.setJgroup(arr[0]);
        main.setJid(arr[1]);
        handler.onceJob(main);
        return R.ok();
    }

}
