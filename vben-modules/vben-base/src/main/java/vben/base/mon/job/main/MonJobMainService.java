package vben.base.mon.job.main;

import cn.hutool.core.lang.ClassScanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.mon.job.root.IJob;
import vben.base.mon.job.root.IJobGroup;
import vben.common.core.utils.IdUtils;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
@RequiredArgsConstructor
public class MonJobMainService {

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return dao.findPageData(sqler);
    }

    public List<MonJobMain> getJobList(){
        List<MonJobMain> scanJobList = getScanJobList();
        List<MonJobMain> dbJobList = dao.findAll();
        for (int j = 0; j < dbJobList.size(); j++) {
            boolean flag = false;
            for (int i = 0; i < scanJobList.size(); i++) {
                if(dbJobList.get(j).getReurl().equals(scanJobList.get(i).getReurl())){
                    flag=true;
                }
            }
//            if(!flag) {
//                repo.deleteById(dbJobList.get(j).getCode());//删除已移除的JOB
//            }
        }

        for (int i = 0; i < scanJobList.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < dbJobList.size(); j++) {
                if(dbJobList.get(j).getReurl().equals(scanJobList.get(i).getReurl())){
                    scanJobList.get(i).setCron(dbJobList.get(j).getCron());
                    scanJobList.get(i).setAvtag(dbJobList.get(j).getAvtag());
                    scanJobList.get(i).setCode(dbJobList.get(j).getCode());
                    flag=true;
                }
            }
            if(!flag){
                dao.insert(scanJobList.get(i));//增加新配置的JOB
            }
        }
        return scanJobList;
    }

    private List<MonJobMain> getScanJobList()
    {
        List<MonJobMain> list = new ArrayList<MonJobMain>();
        Set<Class<?>> classes = ClassScanner.scanPackage("vben");
        for (Class<?> clazz : classes)
        {
            IJobGroup group = clazz.getAnnotation(IJobGroup.class);
            if (group != null)
            {
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods)
                {
                    IJob xjob = method.getAnnotation(IJob.class);
                    if (xjob != null)
                    {
                        MonJobMain job = new MonJobMain();
                        job.setId(IdUtils.getSnowflakeNextId());
                        job.setCron(xjob.cron());
                        job.setJgroup(StrUtils.lowerFirst(clazz.getSimpleName()));
                        job.setName(xjob.name());
                        job.setJid(method.getName());
                        job.setReurl(job.getJgroup()+"/"+job.getJid());
                        job.setAvtag(false);
                        job.setRetyp(0);
                        if(StrUtils.isBlank(xjob.code())){
                            job.setCode(job.getReurl());
                        }else{
                            job.setCode(xjob.code());
                        }
                        boolean flag=false;
                        for (int i = 0; i < list.size(); i++) {
                            if(list.get(i).getCode().equals(job.getCode())){
                                flag=true;
                                log.warn("存在相同key的job，已屏蔽");
                                break;
                            }
                        }
                        if(!flag){
                            list.add(job);
                        }

                    }
                }
            }
        }
        return list;
    }

    public void insert(MonJobMain main) {
        dao.insert(main);
    }

    public void update(MonJobMain main) {
        dao.update(main);
    }

    public MonJobMain findById(Long id) {
        return dao.findById(id);
    }

    private final MonJobMainDao dao;

}
