package vben.common.jdbc.sqler;

import java.util.ArrayList;
import java.util.List;

//插入用的SqlHelper 配合JdbcTemplate使用
public class Isqler extends BaseSqler
{
    private final String insertClause;
    private String keyClause = "";
    private String valueClause = "";
    private final List<Object> parameters = new ArrayList<>();

    public Isqler(String table)
    {
        this.insertClause = "INSERT INTO " + table;
    }

    public Isqler add(String key, Object value)
    {
        if ("true".equals(value))
        {
            keyClause += key + ",";
            valueClause += "?,";
            parameters.add(true);
        } else if ("false".equals(value))
        {
            keyClause += key + ",";
            valueClause += "?,";
            parameters.add(false);
        } else
        {
            keyClause += key + ",";
            valueClause += "?,";
            parameters.add(value);
        }
        return this;
    }

    public String getSql()
    {
        return insertClause + "(" + keyClause.substring(0, keyClause.length() - 1) + ")" + " VALUES(" + valueClause.substring(0, valueClause.length() - 1) + ")";
    }

    public Object[] getParams()
    {
        return parameters.toArray();
    }

}
