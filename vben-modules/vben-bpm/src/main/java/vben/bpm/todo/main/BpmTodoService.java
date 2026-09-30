package vben.bpm.todo.main;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.sys.actor.Actor;
import vben.base.sys.actor.ActorDao;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.bpm.proc.task.BpmProcTask;
import vben.bpm.root.domain.Zproc;
import vben.bpm.todo.done.BpmTodoDone;
import vben.bpm.todo.done.BpmTodoDoneDao;
import vben.bpm.todo.user.BpmTodoUser;
import vben.bpm.todo.user.BpmTodoUserDao;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class BpmTodoService {

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public void sendTodos(Zproc zproc, List<BpmProcTask> taskList) {
        for (BpmProcTask task : taskList) {
            if (task.getActag()) {
                BpmTodo todo = new BpmTodo();
//                todo.setId(IdUtils.getSnowflakeNextIdStr());
                todo.setId(task.getId()+"");
                todo.setName(zproc.getProna());
                todo.setLink("/#/page/ofmv?id=" + zproc.getProid());
                todo.setBusid(zproc.getProid()+"");
                todo.setBusca(zproc.getBusty());
                todo.setType("1");
                todo.setCruid(zproc.getInuid());
                bpmTodoDao.insert(todo);

                Actor org = actorDao.findById(task.getExuid());
                if (org.getType() == 2) {
                    BpmTodoUser todoUser = new BpmTodoUser();
                    todoUser.setId(IdUtils.getSnowflakeNextIdStr());
                    todoUser.setTodid(todo.getId());
                    todoUser.setUseid(task.getExuid());
                    bpmTodoUserDao.insert(todoUser);
                } else if (org.getType() == 4) {
                    String sql = "select aid id from sys_post_actor where pid=?";
                    List<String> userIdList = jdbcHelper.findSlist(sql, task.getExuid());
                    for (String uid : userIdList) {
                        BpmTodoUser todoUser = new BpmTodoUser();
                        todoUser.setId(IdUtils.getSnowflakeNextIdStr());
                        todoUser.setTodid(todo.getId());
                        todoUser.setUseid(uid);
                        bpmTodoUserDao.insert(todoUser);
                    }
                }
            }
        }
    }

//    public void sendTodo(Zbpm zbpm, Znode znode) {
//        SysTodo todo = new SysTodo();
//        todo.setId(IdUtils.getSnowflakeNextIdStr());
//        todo.setName(zbpm.getProna());
//        todo.setLink("/#/page/ofmv?id=" + zbpm.getProid());
//        todo.setBusid(zbpm.getProid());
//
//
//        SysTodoUser todoTarget = new SysTodoUser();
//        todoTarget.setId(IdUtils.getSnowflakeNextIdStr());
//        todoTarget.setTodid(todo.getId());
//        todoTarget.setUseid(znode.getExmen());
//        repo.save(todo);
//        targetRepo.save(todoTarget);
//    }

    public void sendTodo(Zproc zproc,String useid) {
        BpmTodo todo = new BpmTodo();
        todo.setId(IdUtils.getSnowflakeNextIdStr());
        todo.setName(zproc.getProna());
        todo.setLink("/#/page/ofmv?id=" + zproc.getProid());
        todo.setBusid(zproc.getProid()+"");
        todo.setCruid(zproc.getInuid());


        BpmTodoUser todoUser = new BpmTodoUser();
        todoUser.setId(IdUtils.getSnowflakeNextIdStr());
        todoUser.setTodid(todo.getId());
        todoUser.setUseid(useid);
        bpmTodoDao.insert(todo);
        bpmTodoUserDao.insert(todoUser);
    }

//    public void doneTodo(Zbpm zbpm) {
//        String sql = "select m.id,t.id as tid from bpm_todo m inner join bpm_todo_user t on t.todid=m.id " +
//                "where t.useid=? and m.busid=?";
//        Map<String, Object> map = jdbcDao.findMap(sql, zbpm.getHauid(), zbpm.getProid());
//        String delsql = "delete from bpm_todo_user where id=?";
//        jdbcDao.update(delsql, map.get("tid"));
//
//        BpmTodoDone done = new BpmTodoDone();
//        done.setId(IdUtils.getSnowflakeNextIdStr());
//        done.setTodid((String)map.get("id"));
//        done.setUseid(zbpm.getHauid());
//        doneRepo.save(done);
//    }

    public void doneTodo(Zproc zproc) {
        String sql = "select t.useid \"useid\",m.id \"todid\",t.id \"tarid\" from bpm_todo m inner join bpm_todo_user t on t.todid=m.id " +
                "where m.busid=?";
        List<Map<String, Object>> mapList = jdbcHelper.getTp().queryForList(sql, zproc.getProid());
        for (Map<String, Object> map : mapList) {
            if (zproc.getHauid().equals(map.get("useid"))) {

                String delsql = "delete from bpm_todo_user where todid=?";
                jdbcHelper.update(delsql, map.get("todid"));

                BpmTodoDone done = new BpmTodoDone();
                done.setId(IdUtils.getSnowflakeNextIdStr());
                done.setTodid((String)map.get("todid"));
                done.setUseid(zproc.getHauid());
                bpmTodoDoneDao.insert(done);
            }

        }
    }

    public void doneTodos(Zproc zproc) {
        String sql = "select t.useid \"useid\",m.id \"todid\",t.id \"tarid\" from bpm_todo m inner join bpm_todo_user t on t.todid=m.id " +
                "where m.busid=?";
        List<Map<String, Object>> mapList = jdbcHelper.getTp().queryForList(sql, zproc.getProid());
        for (Map<String, Object> map : mapList) {
            String delsql = "delete from bpm_todo_user where id=?";
            jdbcHelper.update(delsql, map.get("tarid"));
            if (zproc.getHauid().equals(map.get("useid"))) {
                BpmTodoDone done = new BpmTodoDone();
                done.setId(IdUtils.getSnowflakeNextIdStr());
                done.setTodid((String)map.get("todid"));
                done.setUseid(zproc.getHauid());
                bpmTodoDoneDao.insert(done);
            }
        }
    }

    public void doneTodosByTaskIds(String[] taskIds) {
        for (String taskId : taskIds) {
            String delsql = "delete from bpm_todo where id=?";
            jdbcHelper.update(delsql, taskId);

            String delsql2 = "delete from bpm_todo_user where todid=?";
            jdbcHelper.update(delsql2, taskId);
        }
    }

    public String insert(BpmTodo main) {
        if (main.getId() == null || "".equals(main.getId())) {
            main.setId(IdUtils.getSnowflakeNextIdStr());
        }
        main.setCrtim(new Date());
        bpmTodoDao.insert(main);
        return main.getId();
    }


    public String update(BpmTodo main) {
        bpmTodoDao.update(main);
        return main.getId();
    }

    public int delete(String[] ids) {
        for (String id : ids) {
            bpmTodoDao.deleteById(id);
        }
        return ids.length;
    }

    @Transactional(readOnly = true)
    public BpmTodo findOne(String id) {
        return bpmTodoDao.findById(id);
    }


    private final JdbcHelper jdbcHelper;

    private final ActorDao actorDao;

    private final BpmTodoDao bpmTodoDao;

    private final BpmTodoUserDao bpmTodoUserDao;

    private final BpmTodoDoneDao bpmTodoDoneDao;

}
