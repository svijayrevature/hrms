package com.revature.hrms.config;

import lombok.Data;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Data
@Configuration
@EnableTransactionManagement
public class HrmsMySQLDBConfig {

    @Bean(name = "mysqlDb")
    @ConfigurationProperties(prefix = "spring.hrms.mysql")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mysqlJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("mysqlDb") DataSource dsMySQL) {
        return new JdbcTemplate(dsMySQL);
    }

    @Bean(name = "mysqlSessionFactory")
    public LocalSessionFactoryBean getMysqlSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(mysqlDataSource());
        sessionFactory.setPackagesToScan(
                "com.revature.hrms.model.mysql");
        sessionFactory.setHibernateProperties(additionalProperties());
        return sessionFactory;
    }

    @Bean(name = "mysqlTransactionManager")
    @Autowired
    @Primary
    public HibernateTransactionManager mysqlTransactionManager(
            @Qualifier("mysqlSessionFactory") SessionFactory sf) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor mysqlExceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
        properties.setProperty("hibernate.default_schema", "hrms");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");
        properties.setProperty("hibernate.generate_statistics", "false");
        properties.setProperty("hibernate.connection.autocommit", "false");
        properties.setProperty("hibernate.id.new_generator_mappings", "false");

        return properties;
    }
}
