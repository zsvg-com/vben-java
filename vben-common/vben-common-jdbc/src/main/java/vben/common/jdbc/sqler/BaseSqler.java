package vben.common.jdbc.sqler;

import java.sql.SQLException;


//增删改SqlHelper的基类 为多个增删改时提供方便的统一处理
public abstract class BaseSqler {

    public abstract  String getSql() throws SQLException;

    public abstract  Object[] getParams();

    public abstract BaseSqler add(String key, Object value);

}
