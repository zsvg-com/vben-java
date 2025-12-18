package vben.base.sys.org.rece;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.root.DbType;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysOrgReceService {

    //todo 有时间再优化
    public int update(List<SysOrgRece> reces,String userId) {
        for (SysOrgRece rece : reces) {
            rece.setOid(rece.getId());
            rece.setId(IdUtils.getSnowflakeNextIdStr());
            rece.setUseid(userId);
        }

        //1.如果当前数量小于10，则去数据库查询最新的差额记录数
        if (reces.size() < 10) {
            Sqler sqler = new Sqler("t.id,t.useid,t.oid,t.uptim", "sys_org_rece", 1, 10 - reces.size());
            sqler.addDescOrder("t.uptim");
            sqler.addEqual("t.useid", userId);
            List<SysOrgRece> list;
            if (DbType.MYSQL.equals(Db.Type)) {
                list = jdbcHelper.getTp().query(sqler.getMysqlPagingSql(), new BeanPropertyRowMapper<>(SysOrgRece.class),sqler.getParams());
            } else if(DbType.PGSQL.equals(Db.Type)) {
                list = jdbcHelper.getTp().query(sqler.getPgsqlPagingSql(), new BeanPropertyRowMapper<>(SysOrgRece.class),sqler.getParams());
            } else if(DbType.ORACLE.equals(Db.Type)) {
                list = jdbcHelper.getTp().query(sqler.getOraclePagingLowerCaseSql(), new BeanPropertyRowMapper<>(SysOrgRece.class),sqler.getParams());
            }else{
                list = jdbcHelper.getTp().query(sqler.getSqlserverPagingSql(), new BeanPropertyRowMapper<>(SysOrgRece.class),sqler.getParams());
            }

            //去重
            for (SysOrgRece dbRece : list) {
                boolean flag = false;
                for (SysOrgRece rece : reces) {
                    if (dbRece.getOid().equals(rece.getOid())) {
                        flag = true;
                    }
                }
                if (!flag) {
                    reces.add(dbRece);
                }
            }
        }

        //2.清空当前用户的最近使用记录
        jdbcHelper.update("delete from sys_org_rece where useid=?", userId);

        //3.更新记录
        dao.saveAll(reces);
        return reces.size();
    }

    private final JdbcHelper jdbcHelper;

    private final SysOrgReceDao dao;

}
