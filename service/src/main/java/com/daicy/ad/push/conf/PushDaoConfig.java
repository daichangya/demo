package com.daicy.ad.push.conf;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:db.properties")
@MapperScan(basePackages = "com.daicy.ad.push.dao",
        sqlSessionFactoryRef = PushDaoConfig.SQL_SESSION_FACTORY_NAME)
public class PushDaoConfig extends DaoConfigBase {

    public static final String SQL_SESSION_FACTORY_NAME = "pushSqlSessionFactory";

    @Autowired
    @Qualifier("dataSource_push")
    private DataSource dataSource;

    public PushDaoConfig() {
        super("classpath:mapper/*.xml");
    }

    @Override
    public DataSource dataSource() throws SQLException {
        return this.dataSource;
    }

    @Bean(name = "dataSource_push")
    public DataSource customizedDataSource(
            @Value("${spring.datasource.druid.driverClassName}") String driver,
            @Value("${spring.datasource.druid.url}") String url,
            @Value("${spring.datasource.druid.username}") String username,
            @Value("${spring.datasource.druid.password}") String password) {
        return super.createDruidDataSource(driver,url, username, password);
    }

    @Override
    @Bean(name = SQL_SESSION_FACTORY_NAME)
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        return super.sqlSessionFactoryBean();
    }
    @Override
    @Bean("txManager_push")
    public DataSourceTransactionManager transactionManager() {
        return super.transactionManager();
    }

    @Override
    @Bean("jdbcTemplatePush")
    public JdbcTemplate jdbcTemplate() {
        return super.jdbcTemplate();
    }
}
