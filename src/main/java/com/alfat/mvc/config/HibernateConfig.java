package com.alfat.mvc.config;


import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.alfat.mvc")
@EnableTransactionManagement
@PropertySource(value = "classpath:app.properties")
public class HibernateConfig {
    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        return properties;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.alfat.mvc.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}

//@Configuration
//@EnableTransactionManagement
//
//@PropertySource("classpath:app.properties")
//
//@EnableJpaRepositories("com.alfat.mvc")
////@ComponentScan(value = "web")
//
//public class HibernateConfig {
//    private static final String PROP_DATABASE_DRIVER = "db.driver";
//    private static final String PROP_DATABASE_PASSWORD = "db.password";
//    private static final String PROP_DATABASE_URL = "db.url";
//    private static final String PROP_DATABASE_USERNAME = "db.username";
//    private static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";
//    private static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
//    private static final String PROP_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
//    private static final String PROP_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
//    private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
//
//    private Environment env;
//
//    @Autowired
//    public void setEnv(Environment env) {
//        this.env = env;
//    }
//
//
//    /************* Start Spring JPA config details **************/
//
//    //@Bean
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
//        LocalContainerEntityManagerFactoryBean factoryBean =
//                new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setJpaVendorAdapter(getJpaVendorAdapter());
//        factoryBean.setDataSource(getDataSource());
//        factoryBean.setPersistenceUnitName("myJpaPersistenceUnit");
//        //factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//        factoryBean.setPackagesToScan(env.getRequiredProperty(PROP_ENTITYMANAGER_PACKAGES_TO_SCAN));
//        factoryBean.setJpaProperties(getHibernateProperties());
//        return factoryBean;
//    }
//
//    @Bean
//    public JpaVendorAdapter getJpaVendorAdapter() {
//        return new HibernateJpaVendorAdapter();
//    }
//
//
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager getTransactionManager() {
//        return new JpaTransactionManager(getEntityManagerFactoryBean().getObject());
//    }
//
//    /************* End Spring JPA config details **************/
//    @Bean
//    public DataSource getDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getRequiredProperty(PROP_DATABASE_DRIVER));
//        dataSource.setUrl(env.getRequiredProperty(PROP_DATABASE_URL));
//        dataSource.setUsername(env.getRequiredProperty(PROP_DATABASE_USERNAME));
//        dataSource.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));
//        return dataSource;
//    }
//
//    private Properties getHibernateProperties() {
//        Properties properties = new Properties();
//        properties.put(PROP_HIBERNATE_DIALECT, env.getRequiredProperty(PROP_HIBERNATE_DIALECT));
//        properties.put(PROP_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
//        properties.put(PROP_HIBERNATE_FORMAT_SQL, env.getRequiredProperty(PROP_HIBERNATE_FORMAT_SQL));
//        properties.put(PROP_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(PROP_HIBERNATE_HBM2DDL_AUTO));
//
//        return properties;
//    }
//}