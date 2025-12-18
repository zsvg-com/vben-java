package vben.common.jpa.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

@Component
public class JpaNamingStrategy implements PhysicalNamingStrategy {


    protected String addUnderscores(String name) {
        if (name == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(name.replace('.', '_'));
        for (int i = 1; i < stringBuffer.length() - 1; i++) {
            if (Character.isLowerCase(stringBuffer.charAt(i-1)) && Character.isUpperCase(stringBuffer.charAt(i)) && Character.isLowerCase(stringBuffer.charAt(i+1))) {
                stringBuffer.insert(i++, '_');
            }
        }
        return stringBuffer.toString().toLowerCase();
    }

    @Override
    public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        String tid=addUnderscores(identifier.getText());
        if(tid.contains("2")&&tid.indexOf("2")!=tid.length()-1){
            tid=tid.replaceFirst("2", "2_");
        }else if(tid.contains("3")&&tid.indexOf("3")!=tid.length()-1){
            tid=tid.replaceFirst("3", "3_");
        }else if(tid.contains("4")&&tid.indexOf("4")!=tid.length()-1){
            tid=tid.replaceFirst("4", "4_");
        }else if(tid.contains("5")&&tid.indexOf("5")!=tid.length()-1){
            tid=tid.replaceFirst("5", "5_");
        }
        return Identifier.toIdentifier(tid);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        String fid=addUnderscores(identifier.getText());
//        return Identifier.toIdentifier(fid+"_");
        return Identifier.toIdentifier(fid);
    }


}
