package vben.base.sys.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jdbc.sqler.Usqler;

import java.lang.reflect.Field;

@Component
@RequiredArgsConstructor
public class SysConfigDao {

    private static final String table = "sys_config";

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public SysConfig findById(Long id) {
        String sql = "select * from "+table+" where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysConfig.class), id);
    }

    public String findValue(String kenam) {
        String sql = "select keval from "+table+" where kenam = ?";
        return jdbcHelper.findString(sql,kenam);
    }

    @SneakyThrows
    public void insert(SysConfig main) {
        Isqler sqler = new Isqler(table);
        Class<?> clazz = main.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            sqler.add(field.getName(), field.get(main));// 注意：这里需要传入对象实例
        }
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    @SneakyThrows
    public void update(SysConfig main) {
        Usqler sqler = new Usqler(table);
        sqler.addWhere("id=?", main.getId());
        Class<?> clazz = main.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(!field.getName().equals("id")) {
                field.setAccessible(true);
                sqler.add(field.getName(), field.get(main));// 注意：
            }
        }
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from "+table+" where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
