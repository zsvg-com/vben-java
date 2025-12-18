package vben.common.jdbc.sqler;

import lombok.Getter;
import lombok.Setter;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.root.DbType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//查询用的SqlHelper 配合JdbcTemplate使用
public class Sqler
{
    private String fromClause = "";
    private String whereClause = "";
    private String orderClause = "";
    private String selectClause = "";
    private String groupClause = "";
    @Setter
    @Getter
    private Integer panum = 1;
    @Getter
    @Setter
    private Integer pasiz = 10;
    @Getter
    @Setter
    private int autoType=1;
    private final List<Object> parameters = new ArrayList<>(); // 参数清单

    public Sqler()
    {

    }

    public Sqler(String table, Integer panum, Integer pasiz)
    {
        this.selectClause = "SELECT t.id,t.name";
        this.fromClause = " FROM " + table+" t";
        if (panum != null)
        {
            this.panum = panum;
        }
        if (pasiz != null)
        {
            this.pasiz = pasiz;
        }
    }

    public Sqler(String fields, String table, Integer panum, Integer pasiz)
    {
        this.selectClause = "SELECT " + fields;
        this.fromClause = " FROM " + table+" t";
        if (panum != null)
        {
            this.panum = panum;
        }
        if (pasiz != null)
        {
            this.pasiz = pasiz;
        }
    }


    public Sqler(String table)
    {
        this.selectClause = "SELECT t.id,t.name";
        this.fromClause = " FROM " + table+" t";
    }

    public Sqler(String fields, String table)
    {
        this.selectClause = "SELECT " + fields;
        this.fromClause = " FROM " + table+" t";
    }

    public void addSelect(String fields)
    {
        selectClause += "," + fields;
    }

    public Sqler addInnerJoin(String fields, String table, String condition)
    {
        if (!fields.isEmpty())
        {
            selectClause += "," + fields;
        }
        fromClause += " INNER JOIN " + table + " ON " + condition;
        return this;
    }

    public Sqler addLeftJoin(String fields, String table, String condition)
    {
        if (!fields.isEmpty())
        {
            selectClause += "," + fields;
        }
        fromClause += " Left JOIN " + table + " ON " + condition;
        return this;
    }

    public Sqler addWhere(String condition, Object... params)
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
            Collections.addAll(parameters, params);
        }
        return this;
    }

    public Sqler addWhere(boolean append, String condition, Object... params)
    {
        if (append)
        {
            addWhere(condition, params);
        }
        return this;
    }

    public Sqler addEqual(String name, Object value)
    {
        if (value!=null&& StrUtils.isNotBlank(value+""))
        {
            if (whereClause.isEmpty())
            {
                whereClause = " WHERE " + name + " = ?";
            } else
            {
                whereClause += " AND " + name + " = ?";
            }
            parameters.add(value);
        }
        return this;
    }

    public Sqler addLike(String name, String value)
    {
        if (StrUtils.isNotBlank(value))
        {
            if (whereClause.isEmpty())
            {
                whereClause = " WHERE " + name + " like ?";
            } else
            {
                whereClause += " AND " + name + " like ?";
            }
            parameters.add("%"+value.trim()+"%");
        }
        return this;
    }

    public Sqler addGtStringDate(String name, String value)
    {
        if (StrUtils.isNotBlank(value))
        {
            if (whereClause.isEmpty())
            {
                whereClause = " WHERE " + name + ">=?";
            } else
            {
                whereClause += " AND " + name + ">=?";
            }
            parameters.add(value);
        }
        return this;
    }

    public Sqler addGtDate(String name, String value, String dbType)
    {
        if(DbType.ORACLE.equals(dbType)){
            if (StrUtils.isNotBlank(value))
            {
                if (whereClause.isEmpty())
                {
                    whereClause = " WHERE " + name + ">=to_date(?,'yyyy-MM-dd')";
                } else
                {
                    whereClause += " AND " + name + ">=to_date(?,'yyyy-MM-dd')";
                }
                parameters.add(value);
            }
        }else if(DbType.MYSQL.equals(dbType)){
            if (StrUtils.isNotBlank(value))
            {
                if (whereClause.isEmpty())
                {
                    whereClause = " WHERE unix_timestamp(" + name + ") >=unix_timestamp(?)";
                } else
                {
                    whereClause += " AND unix_timestamp(" + name + ")  >=unix_timestamp(?)";
                }
                parameters.add(value);
            }
        }

        return this;
    }

    public Sqler addLtDate(String name, String value, String dbType)
    {
        if(DbType.ORACLE.equals(dbType)) {
            if (StrUtils.isNotBlank(value)) {
                if (whereClause.isEmpty()) {
                    whereClause = " WHERE " + name + "<to_date(?,'yyyy-MM-dd')+1";
                } else {
                    whereClause += " AND " + name + "<to_date(?,'yyyy-MM-dd')+1";
                }
                parameters.add(value);
            }
        }else{
            if (StrUtils.isNotBlank(value)) {
                if (whereClause.isEmpty()) {
                    whereClause = " WHERE unix_timestamp(" + name + ") <=unix_timestamp(?)";
                } else {
                    whereClause += " AND unix_timestamp(" + name + ") <=unix_timestamp(?)";
                }
                parameters.add(value+" 23:59:59");
            }
        }
        return this;
    }

    public Sqler addLtStringDate(String name, String value)
    {
        if (StrUtils.isNotBlank(value))
        {
            if (whereClause.isEmpty())
            {
                whereClause = " WHERE " + name + "<=?";
            } else
            {
                whereClause += " AND " + name + "<=?";
            }
            parameters.add(value);
        }
        return this;
    }

    public Sqler addOrder(String propertyName)
    {
        if (orderClause.isEmpty())
        {
            orderClause = " ORDER BY " + propertyName;
        } else
        {
            orderClause += ", " + propertyName;
        }
        return this;
    }

    public Sqler addDescOrder(String propertyName)
    {
        if (orderClause.isEmpty())
        {
            orderClause = " ORDER BY " + propertyName + " DESC";
        } else
        {
            orderClause += ", " + propertyName + " DESC";
        }
        return this;
    }

    public Sqler addGroup(String gclause)
    {
        groupClause=" GROUP BY "+gclause;
        return this;
    }

    public Sqler addJoin(String joinClause)
    {
        this.fromClause += " " + joinClause;
        return this;
    }

    public Sqler selectCinfo(){
        addLeftJoin("oo1.name as cruna,t.crtim", "sys_org oo1", "oo1.id=t.cruid");
        return this;
    }


    public Sqler selectCUinfo(){
        addLeftJoin("oo1.name as cruna,t.crtim", "sys_org oo1", "oo1.id=t.cruid");
        addLeftJoin("oo2.name as upuna,t.uptim", "sys_org oo2", "oo2.id=t.upuid");
        return this;
    }



    //get and set-------------------------------------------

    public String getSql()
    {
        return selectClause + fromClause + whereClause +groupClause+ orderClause;
    }

    public String getLowerCaseSql()
    {
        //字段转成小写
        StringBuilder changeSelectClause= new StringBuilder();
        String[] strArr= selectClause.split(",");
        lowerCaseSelect(changeSelectClause, strArr);
        changeSelectClause.deleteCharAt(changeSelectClause.length()-1);
        return changeSelectClause + fromClause + whereClause+groupClause + orderClause;
    }

    public Sqler getNewGroupSqler(String groupClause){
        Sqler sqler = new Sqler("");
        sqler.fromClause=this.fromClause;
        sqler.whereClause=this.whereClause;
        sqler.groupClause=" group by "+groupClause;
        return sqler;
    }


    public String getSizeSql()
    {
        if("".equals(groupClause)){
            return "SELECT count(1)" + fromClause + whereClause;
        }
        else {
            return  "SELECT count(1) FROM (SELECT 1 " + fromClause + whereClause+groupClause+") SSZZ";
        }
    }

    public String getMysqlPagingSql()
    {
        int fromIndex = pasiz * (panum - 1);
        return selectClause + fromClause + whereClause+groupClause + orderClause + " limit " + fromIndex + "," + pasiz;
    }

    public String getPgsqlPagingSql()
    {
        int fromIndex = pasiz * (panum - 1);
        return selectClause + fromClause + whereClause+groupClause + orderClause + " limit " +  pasiz + " OFFSET " +fromIndex;
    }

    public String getSqlserverPagingSql()
    {
        int rownum = panum * pasiz;
        int rn = (panum - 1) * pasiz;
        if("".equals(orderClause)){
            orderClause = "order by t.id";
        }
        return " SELECT * FROM ("+selectClause+", ROW_NUMBER() OVER("+orderClause+") AS RowId " + fromClause + whereClause+groupClause  + ") PPGG  WHERE PPGG.RowId between " + rn + " and "+ rownum;
//      return " SELECT * FROM (SELECT PPGG.*, ROWNUM RN FROM (" + changeSelectClause.toString() + fromClause + whereClause+groupClause + orderClause + ") PPGG  WHERE ROWNUM <= " + rownum + ")  WHERE RN > " + rn;
    }

    public String getOraclePagingSql()
    {
        int rownum = panum * pasiz;
        int rn = (panum - 1) * pasiz;
        return " SELECT * FROM (SELECT PPGG.*, ROWNUM RN FROM (" + selectClause + fromClause + whereClause+groupClause + orderClause + ") PPGG  WHERE ROWNUM <= " + rownum + ")  WHERE RN > " + rn;
//        return " SELECT * FROM (SELECT PPGG.*, ROWNUM RN FROM (" + changeSelectClause.toString() + fromClause + whereClause+groupClause + orderClause + ") PPGG  WHERE ROWNUM <= " + rownum + ")  WHERE RN > " + rn;
    }

    public String getOraclePagingLowerCaseSql()
    {
        //字段转成小写
        StringBuilder changeSelectClause= new StringBuilder();
        String[] strArr= selectClause.split(",");
        lowerCaseSelect(changeSelectClause, strArr);
        changeSelectClause.deleteCharAt(changeSelectClause.length()-1);
        //分页
        int rownum = panum * pasiz;
        int rn = (panum - 1) * pasiz;
        return " SELECT * FROM (SELECT PPGG.*, ROWNUM RN FROM (" + changeSelectClause + fromClause + whereClause+groupClause + orderClause + ") PPGG  WHERE ROWNUM <= " + rownum + ")  WHERE RN > " + rn;
    }

    private void lowerCaseSelect(StringBuilder changeSelectClause, String[] strArr) {
        for (String aStrArr : strArr) {
            if(aStrArr.contains("(")&&!aStrArr.contains(")")){
                changeSelectClause.append(" ").append(aStrArr);
            }else if(aStrArr.contains("SELECT")){
                String[] firstArr = aStrArr.split(" ");
                if(firstArr.length>=3){
                    firstArr[firstArr.length - 1] = "\"" + firstArr[firstArr.length - 1] + "\"";
                }else{
                    firstArr[1]=firstArr[1]+" \"" + firstArr[1].substring(firstArr[1].lastIndexOf(".")+1) + "\"";
                }
                for (String afirstArr : firstArr) {
                    changeSelectClause.append(" ").append(afirstArr);
                }
            }else{

                String[] strArr2 = aStrArr.split(" ");
                if(strArr2.length>=2){
                    strArr2[strArr2.length - 1] = "\"" + strArr2[strArr2.length - 1] + "\"";
                }else{
                    strArr2[0]=strArr2[0]+" \"" + strArr2[0].substring(strArr2[0].lastIndexOf(".")+1) + "\"";
                }
                for (String aStrArr2 : strArr2) {
                    changeSelectClause.append(" ").append(aStrArr2);
                }
            }
            changeSelectClause.append(",");
        }
    }

    public Object[] getParams()
    {
        return parameters.toArray();
    }

}
