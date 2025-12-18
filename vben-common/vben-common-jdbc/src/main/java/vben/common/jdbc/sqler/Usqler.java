package vben.common.jdbc.sqler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//更新用的SqlHelper 配合JdbcTemplate使用
public class Usqler extends BaseSqler
{
    private final String updateClause ;
    private String keyClause = "";
    private String whereClause = "";
    private final List<Object> parameters = new ArrayList<>(); //参数列表
    private final List<Object> whereParameters = new ArrayList<>();


    public Usqler(String table)
    {
        this.updateClause = "UPDATE " + table + " SET ";
    }

    public Usqler add(String key, Object value)
    {
        if ("true".equals(value))
        {
            keyClause += key + "=?,";
            parameters.add(true);
        } else if ("false".equals(value))
        {
            keyClause += key + "=?,";
            parameters.add(false);
        } else
        {
            keyClause += key + "=?,";
            parameters.add(value);
        }
        return this;
    }

    public Usqler addWhere(String condition, Object... params)
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

    public String getSql()  {
        return updateClause + keyClause.substring(0, keyClause.length() - 1) + whereClause;
    }

    public Object[] getParams()
    {
        parameters.addAll(whereParameters);
        return parameters.toArray();
    }
}
