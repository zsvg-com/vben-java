package vben.base.sys.rece;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SysReceDao {

    public void saveAll(List<SysRece> list) {
        List<Object[]> deleteList = new ArrayList<>();
        List<Object[]> insertLIst = new ArrayList<>();
        for (SysRece rece : list) {
            Object[] arr = new Object[2];
            arr[0] = rece.getUseid();
            arr[1] = rece.getOid();
            deleteList.add(arr);

            Object[] arr2 = new Object[4];
            arr2[0] = rece.getId();
            arr2[1] = rece.getUseid();
            arr2[2] = rece.getOid();
            arr2[3] = rece.getUptim();
            insertLIst.add(arr2);
        }
        String deleteSql = "delete from sys_rece where useid=? and oid=?";
        jdbcHelper.batch(deleteSql, deleteList);
        String insertSql = "insert into sys_rece(id,useid,oid,uptim) values(?,?,?,?)";
        jdbcHelper.batch(insertSql, insertLIst);
    }


    public void insert(SysRece rece) {
        Isqler sqler = new Isqler("sys_rece");
        sqler.add("id", rece.getId());
        sqler.add("name", rece.getUseid());
        sqler.add("depid", rece.getOid());
        sqler.add("tier", rece.getUptim());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(String id) {
        String sql = "delete from sys_rece where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    private final JdbcHelper jdbcHelper;
}
