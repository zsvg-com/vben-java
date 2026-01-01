package vben.base.sys.rece;

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
public class SysReceService {

    //todo 有时间再优化
    public int update(List<SysRece> reces, String userId) {
        for (SysRece rece : reces) {
            rece.setOid(rece.getId());
            rece.setId(IdUtils.getSnowflakeNextIdStr());
            rece.setUseid(userId);
        }

        //1.如果当前数量小于10，则去数据库查询最新的差额记录数
        if (reces.size() < 10) {
            Sqler sqler = new Sqler("t.id,t.useid,t.oid,t.uptim", "sys_rece", 1, 10 - reces.size());
            sqler.addDescOrder("t.uptim");
            sqler.addEqual("t.useid", userId);
            List<SysRece> list;
            if (DbType.MYSQL.equals(Db.Type)) {
                list = jdbcHelper.getTp().query(sqler.getMysqlPagingSql(), new BeanPropertyRowMapper<>(SysRece.class),sqler.getParams());
            } else if(DbType.PGSQL.equals(Db.Type)) {
                list = jdbcHelper.getTp().query(sqler.getPgsqlPagingSql(), new BeanPropertyRowMapper<>(SysRece.class),sqler.getParams());
            } else if(DbType.ORACLE.equals(Db.Type)) {
                list = jdbcHelper.getTp().query(sqler.getOraclePagingLowerCaseSql(), new BeanPropertyRowMapper<>(SysRece.class),sqler.getParams());
            }else{
                list = jdbcHelper.getTp().query(sqler.getSqlserverPagingSql(), new BeanPropertyRowMapper<>(SysRece.class),sqler.getParams());
            }

            //去重
            for (SysRece dbRece : list) {
                boolean flag = false;
                for (SysRece rece : reces) {
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
        jdbcHelper.update("delete from sys_rece where useid=?", userId);

        //3.更新记录
        dao.saveAll(reces);
        return reces.size();
    }

    private final JdbcHelper jdbcHelper;

    private final SysReceDao dao;

}
