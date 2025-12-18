package vben.common.jdbc.sqler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//删除用的SqlHelper 配合JdbcTemplate使用
public class Dsqler extends BaseSqler{
    private final String deleteClause;
    private String whereClause = "";
    private final List<Object> whereParameters = new ArrayList<>();

    public Dsqler(String table)
    {
        this.deleteClause = "DELETE FROM " + table + " ";
    }

    public Dsqler addWhere(String condition, Object... params)
    {
        if (whereClause.isEmpty())
        {
            whereClause = " WHERE " + condition;
        } else
        {
            whereClause += " AND " + condition;
        }
        if (params != null)
        {
            Collections.addAll(whereParameters, params);
        }
        return this;
    }

    public Dsqler add(String key, Object value)
    {
        return this;
    }

    public String getSql() throws SQLException {
        if("".equals(whereClause)){
            throw new SQLException("为防止误删，delete 语句必须要有where条件");
        }
        return deleteClause  + whereClause;
    }

    public Object[] getParams()
    {
        return whereParameters.toArray();
    }


}
