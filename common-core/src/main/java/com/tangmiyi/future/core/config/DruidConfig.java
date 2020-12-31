package com.tangmiyi.future.core.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Druid配置
 */
@Configuration
public class DruidConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.druid.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.druid.initialSize}")
    private Integer initialSize;
    @Value("${spring.datasource.druid.minIdle}")
    private Integer minIdle;
    @Value("${spring.datasource.druid.maxActive}")
    private Integer maxActive;
    @Value("${spring.datasource.druid.maxWait}")
    private Integer maxWait;
    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;
    @Value("${spring.datasource.druid.validationQuery}")
    private String validationQuery;
    @Value("${spring.datasource.druid.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${spring.datasource.druid.testOnBorrow}")
    private Boolean testOnBorrow;
    @Value("${spring.datasource.druid.testOnReturn}")
    private Boolean testOnReturn;
    @Value("${spring.datasource.druid.poolPreparedStatements}")
    private Boolean poolPreparedStatements;
    @Value("${spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize}")
    private Integer maxPoolPreparedStatementPerConnectionSize;

    /**
     * 实现web监控配置
     * @return
     */
//    @Bean
//    public ServletRegistrationBean druidServlet() {
//        // 进行druid监控的配置处理操作
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
//                new StatViewServlet(), "/druid/*");
//        // 白名单
//        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
//        // 黑名单
//        servletRegistrationBean.addInitParameter("deny", "");
//        //用户名
//        servletRegistrationBean.addInitParameter("loginUsername", "root");
//        //密码
//        servletRegistrationBean.addInitParameter("loginPassword", "root");
//        //是否可以重置数据源
//        servletRegistrationBean.addInitParameter("resetEnable", "false");
//        return servletRegistrationBean;
//    }
//
//    /**
//     * 监控
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean(){
//        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new WebStatFilter());
//        // 所有请求进行监控处理
//        filterRegistrationBean.addUrlPatterns("/*");
//        // 排除
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
//        return filterRegistrationBean;
//    }

    @Bean
    public DataSource druidDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        if(initialSize != null) {
            datasource.setInitialSize(initialSize);
        }
        if(minIdle != null) {
            datasource.setMinIdle(minIdle);
        }
        if(maxActive != null) {
            datasource.setMaxActive(maxActive);
        }
        if(maxWait != null) {
            datasource.setMaxWait(maxWait);
        }
        if(timeBetweenEvictionRunsMillis != null) {
            datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        }
        if(minEvictableIdleTimeMillis != null) {
            datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        }
        if(validationQuery!=null) {
            datasource.setValidationQuery(validationQuery);
        }
        if(testWhileIdle != null) {
            datasource.setTestWhileIdle(testWhileIdle);
        }
        if(testOnBorrow != null) {
            datasource.setTestOnBorrow(testOnBorrow);
        }
        if(testOnReturn != null) {
            datasource.setTestOnReturn(testOnReturn);
        }
        if(poolPreparedStatements != null) {
            datasource.setPoolPreparedStatements(poolPreparedStatements);
        }
        if(maxPoolPreparedStatementPerConnectionSize != null) {
            datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        }
        List<Filter> filters = new ArrayList<>();
        filters.add(wallFilter());
        datasource.setProxyFilters(filters);
        return datasource;
    }

//    @Primary
//    @Bean("dataSource")
//    public DataSource dataSourceProxy(DruidDataSource druidDataSource) {
//        DataSourceProxy dataSourceProxy = new DataSourceProxy(druidDataSource);
//        return dataSourceProxy;
//    }

//    /**
//     * init mybatis sqlSessionFactory
//     * @Param: dataSourceProxy  datasource proxy
//     * @Return: DataSourceProxy  datasource proxy
//     */
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSourceProxy dataSourceProxy) throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(dataSourceProxy);
//        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath*:/mapper/*.xml"));
//        factoryBean.setTypeAliasesPackage("com.bitao.*.entity");
//        //开启驼峰命名转换
//        factoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
//        factoryBean.getObject().getConfiguration().setLogImpl(StdOutImpl.class);
//        factoryBean.getObject().getConfiguration().setCacheEnabled(false);
//        factoryBean.setTransactionFactory(new JdbcTransactionFactory());
//        return factoryBean.getObject();
//    }

//    /**
//     * 慢查询
//     * @return
//     */
//    @Bean
//    public StatFilter statFilter(){
//        StatFilter statFilter = new StatFilter();
//        statFilter.setLogSlowSql(true);
//        statFilter.setMergeSql(true);
//        statFilter.setSlowSqlMillis(5000);
//        return statFilter;
//    }
//
    @Bean
    public WallFilter wallFilter(){
        WallFilter wallFilter = new WallFilter();
        //允许执行多条SQL
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);
        wallFilter.setConfig(config);
        return wallFilter;
    }
}
