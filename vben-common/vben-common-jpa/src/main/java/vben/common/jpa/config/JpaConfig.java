package vben.common.jpa.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
//@AutoConfiguration
@EnableTransactionManagement
public class JpaConfig {

    @Value("${spring.datasource.primary.jpa.entityScan}")
    private String ENTITY_SCAN;

    @Value("${spring.datasource.primary.jpa.hbm2ddl.auto}")
    private String DDL_AUTO;

//    @Value("${spring.datasource.primary.jpa.integrator}")
//    private String INTEGRATOR;

//    @Primary
//    @Bean(name = "entityManager")
//    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
//        return entityManagerFactory(builder).getObject().createEntityManager();
//    }

    private Map<String, Object> getVendorProperties() {
        Map<String, Object> map = new HashMap<>();
//        map.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
//        map.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        map.put("hibernate.hbm2ddl.auto", DDL_AUTO);
        map.put("hibernate.physical_naming_strategy", "vben.common.jpa.config.JpaNamingStrategy");
        map.put("hibernate.use_sql_comments", true);
        map.put("hibernate.integrator_provider", (IntegratorProvider) () -> Collections.singletonList(JpaCommentIntegrator.INSTANCE));
//        if(StrUtils.isNotEmpty(INTEGRATOR)){
//            map.put("hibernate.integrator_provider", (IntegratorProvider) () -> Collections.singletonList(SpringUtils.getBean(INTEGRATOR)));
//        }
        return map;
    }


//    @Primary
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(dataSource)
//                .packages(ENTITY_SCAN)
//                .properties(getVendorProperties())
//                .persistenceUnit("persistenceUnit")
//                .build();
//    }
//
//    @Primary
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
//        return new JpaTransactionManager(entityManagerFactory(builder).getObject());
//    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(ENTITY_SCAN)
                .properties(getVendorProperties())
                .persistenceUnit("primary")
                .build();
    }


    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
