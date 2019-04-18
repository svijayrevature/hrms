package com.revature.hrms.mssql.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.Data;

@Data
@Configuration
@EnableTransactionManagement
public class HrmsMSSQLDBConfig {

  @Bean(name = "sqlDb")
  @ConfigurationProperties(prefix = "spring.hrms.sql")
  public DataSource sqlDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "sqlJdbcTemplate")
  public JdbcTemplate sqlJdbcTemplate(@Qualifier("sqlDb") DataSource mssql) {
    return new JdbcTemplate(mssql);
  }

  @Bean(name = "mssqlSessionFactory")
  public LocalSessionFactoryBean getMssqlSessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(sqlDataSource());
    sessionFactory.setPackagesToScan("com.revature.hrms.mssql.model");
    sessionFactory.setHibernateProperties(additionalProperties());
    return sessionFactory;
  }

  @Bean(name = "mssqlTransactionManager")
  public HibernateTransactionManager mssqlTransactionManager() {
    return new HibernateTransactionManager(getMssqlSessionFactory().getObject());
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor mssqlExceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  private Properties additionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
    properties.setProperty("hibernate.default_schema", "cosec1.dbo");
    properties.setProperty("hibernate.show_sql", "false");
    properties.setProperty("hibernate.format_sql", "true");
    properties.setProperty("hibernate.use_sql_comments", "true");
    properties.setProperty("hibernate.generate_statistics", "false");
    properties.setProperty("hibernate.connection.autocommit", "false");
    properties.setProperty("hibernate.id.new_generator_mappings", "false");

    return properties;
  }
}
