package com.daicy.ad.push.conf;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public abstract class DaoConfigBase {

    private String username;

    private String password;

    private String url;

    private int minIdle = 1;

    private int maxActive = 50;

    private int maxWait = 10000;

    private int timeBetweenEvictionRunsMillis = 60000;

    private int minEvictableIdleTimeMillis = 30000;

    private boolean testWhileIdle = true;

    private boolean testOnBorrow = false;

    private boolean testOnReturn = false;

    private String[] mapperLocations;

    private DruidDataSource dataSource;

    private DataSourceTransactionManager transactionManager;

    private SqlSessionFactory sqlSessionFactory;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DaoConfigBase(String... mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    @PostConstruct
    public void init() {
        try {
            log.info("Init datasource: url: {}", url);
            dataSource = new DruidDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setTestWhileIdle(true);
            dataSource.setTestOnReturn(false);
            initDataSource(dataSource);
            dataSource.init();

            transactionManager = new DataSourceTransactionManager();
            transactionManager.setDataSource(dataSource);
            log.info("Init done");
        } catch (Throwable t) {
            log.error("Init common", t);
        }
    }

    protected void initDataSource(DruidDataSource dataSource) {
        log.info("Data source options: minIdle={}, maxActive={},maxWait={}", minIdle, maxActive, maxWait);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setValidationQuery("SELECT 'x'");
    }

    @PreDestroy
    public void destroy() {
        try {
            log.info("Close {}", url);
            dataSource.close();
            log.info("Close {} done", url);
        } catch (Throwable t) {
            log.error("Destroy common", t);
        }
    }

    public DataSource dataSource() throws SQLException {
        return dataSource;
    }

    @Autowired
    private ResourcePatternResolver resourceResolver;

    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        if (sqlSessionFactory == null) {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            Configuration config = new Configuration();
//            config.setMapUnderscoreToCamelCase(true);
//            config.setUseActualParamName(false);
            sqlSessionFactoryBean.setConfiguration(config);
            sqlSessionFactoryBean.setDataSource(dataSource());
            List<Resource> resources = new ArrayList<>();
            if (this.mapperLocations != null) {
                for (String mapperLocation : this.mapperLocations) {
                    try {
                        Resource[] mappers = resourceResolver.getResources(mapperLocation);
                        resources.addAll(Arrays.asList(mappers));
                    } catch (IOException e) {
                        log.error("IOException", e);
                        // ignore
                    }
                }
            }
            Resource[] arr = resources.toArray(new Resource[resources.size()]);
            sqlSessionFactoryBean.setMapperLocations(arr);
            sqlSessionFactory = sqlSessionFactoryBean.getObject();
        }
        return sqlSessionFactory;
    }

    public DataSourceTransactionManager transactionManager() {
        return transactionManager;
    }

    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(this.dataSource);
    }

    protected DruidDataSource createDruidDataSource(String driver, String url, String username, String password) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(30);
        dataSource.setMinIdle(1);
        dataSource.setMaxWait(60000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(30000);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setValidationQuery("SELECT 'x'");

        return dataSource;
    }
}