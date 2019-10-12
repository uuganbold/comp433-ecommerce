package edu.luc.comp433.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {
        "edu.luc.comp433.business",
        "edu.luc.comp433.persistence",
        "edu.luc.comp433.bootstrap"
})
@PropertySource("classpath:application.properties")
@Import(PersistenceJPAConfig.class)
public class MainConfig implements EnvironmentAware {

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    @Bean(value = "dataSource", destroyMethod = "close")
    @Profile("prod")
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }


    @Bean
    @Profile("prod")
    public HikariConfig hikariConfig() {
        HikariConfig hk = new HikariConfig();
        hk.setPoolName("springHikariCP");
        hk.setConnectionTestQuery("SELECT 1");
        hk.setDataSourceClassName(env.getProperty("datasource.driverClassName"));
        hk.setMaximumPoolSize(Integer.parseInt(env.getProperty("datasource.maximumPoolSize")));
        hk.setIdleTimeout(Integer.parseInt(env.getProperty("datasource.idleTimeout")));

        Properties dsP = new Properties();
        dsP.setProperty("url", env.getProperty("datasource.url"));
        dsP.setProperty("user", env.getProperty("datasource.username"));
        dsP.setProperty("password", env.getProperty("datasource.password"));
        hk.setDataSourceProperties(dsP);
        return hk;
    }

    @Bean("dataSource")
    @Profile("dev")
    public DataSource h2TestDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }


}
