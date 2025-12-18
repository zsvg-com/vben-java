package vben.bpm.bus.tmpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.bpm.proc.def.BpmProcDef;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.dto.SidName;
import vben.common.jdbc.dto.Stree;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.satoken.utils.LoginHelper;
import vben.bpm.proc.def.BpmProcDefDao;

import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BpmBusTmplService {

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }


    public List<Stree> findTreeList() {
        String sql="SELECT id,pid,name,TYPE FROM ( " +
            " SELECT id,pid,name,'cate' type,ORNUM FROM bpm_bus_cate WHERE avtag= " + Db.True+
            " UNION ALL SELECT id,catid AS pid,name,'tmpl' type,ORNUM FROM bpm_bus_tmpl WHERE avtag= " +Db.True+
            ")t ORDER BY ornum";
//        List<ZflatTree> list = jdbcHelper.getTp().query(sql, new BeanPropertyRowMapper<>(ZflatTree.class));
        return jdbcHelper.findStreeList(sql);
//        return list;
    }

    public List<SidName> findIdNameList(Sqler sqler) {
        return jdbcHelper.findSidNameList(sqler);
    }

    public Long insert(BpmBusTmpl main) {
        main.setId(IdUtils.getSnowflakeNextId());
        main.setCruid(LoginHelper.getUserId());
        saveBpmDef(main);
        tmplDao.insert(main);
        return main.getId();
    }

    public Long update(BpmBusTmpl main) {
        saveBpmDef(main);
        main.setUpuid(LoginHelper.getUserId());
        main.setUptim(new Date());
        tmplDao.update(main);
        return main.getId();
    }

    private void saveBpmDef(BpmBusTmpl main) {
        BpmProcDef def = new BpmProcDef();
        def.setId(IdUtils.getSnowflakeNextId());
        def.setName(main.getName());
        def.setDixml(main.getBpmXml());
        String exxml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>"
            + "\n<process" + main.getBpmXml().split("bpmn2:process")[1]
            .replaceAll("bpmn2:", "")
            .replaceAll("activiti:", "") + "process>";
        def.setExxml(exxml);
        def.setBusid(main.getId());
        bpmProcDefDao.insert(def);
        main.setPrdid(def.getId());
    }

    public int delete(Long[] ids) {
        for (Long str : ids) {
            tmplDao.deleteById(str);
        }
        return ids.length;
    }

    @Transactional(readOnly = true)
    public BpmBusTmpl findById(Long id) {
        return tmplDao.findById(id);
    }


    private final BpmProcDefDao bpmProcDefDao;

    private final BpmBusTmplDao tmplDao;

    private final JdbcHelper jdbcHelper;

}

