package vben.bpm.proc.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class BpmProcTaskHiService {

    private final BpmProcTaskHiDao dao;

    public BpmProcTaskHi createTask(BpmProcTask mainTask) {
        BpmProcTaskHi histTask = new BpmProcTaskHi();
        histTask.setId(mainTask.getId());
        histTask.setProid(mainTask.getProid());
        histTask.setState("20");
        histTask.setExuid(mainTask.getExuid());
        histTask.setNodid(mainTask.getNodid());
        histTask.setType(mainTask.getType());
        dao.insert(histTask);
        return histTask;
    }

    public List<BpmProcTaskHi> createTaskList(List<BpmProcTask> mainTaskList) {
        List<BpmProcTaskHi> list=new ArrayList<>();
        for (BpmProcTask mainTask : mainTaskList) {
            if(mainTask.getActag()){
                BpmProcTaskHi histTask = new BpmProcTaskHi();
                histTask.setId(mainTask.getId());
                histTask.setProid(mainTask.getProid());
                histTask.setState("20");
                histTask.setExuid(mainTask.getExuid());
                histTask.setNodid(mainTask.getNodid());
                histTask.setType(mainTask.getType());
                dao.insert(histTask);
                list.add(histTask);
            }
        }
        return list;
    }


    @Transactional(readOnly = true)
    public BpmProcTaskHi findOne(Long id) {
        return dao.findById(id);
    }


    public void update(BpmProcTaskHi task) {
         dao.update(task);
    }


    public void delete(Long id) {
        dao.deleteById(id);
    }

    public void deleteAllByProidNotEnd(Long proid) {

        //repo.deleteAllByProidAndState(proid,"20");
    }


}
