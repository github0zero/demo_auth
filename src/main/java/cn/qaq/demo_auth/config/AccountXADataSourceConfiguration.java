//package cn.qaq.demo_auth.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import io.seata.rm.datasource.DataSourceProxy;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class AccountXADataSourceConfiguration {
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DruidDataSource druidDataSource() {
//        return new DruidDataSource();
//    }
//
//    @Bean("dataSourceProxy")
//    public DataSource dataSource(DruidDataSource druidDataSource) {
//        return new DataSourceProxy(druidDataSource);
//    }
//
//
//    @Bean("jdbcTemplate")
//    public JdbcTemplate jdbcTemplate(DataSource dataSourceProxy) {
//        return new JdbcTemplate(dataSourceProxy);
//    }
//
//    @Bean
//    public PlatformTransactionManager txManager(DataSource dataSourceProxy) {
//        return new DataSourceTransactionManager(dataSourceProxy);
//    }
//
//}
