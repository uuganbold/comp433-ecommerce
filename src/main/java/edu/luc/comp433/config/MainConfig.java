package edu.luc.comp433.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackages = {
        "edu.luc.comp433.business"
})
@Import(PersistenceJPAConfig.class)
@PropertySource("classpath:application.properties")
public class MainConfig {

    private Environment env;

    public MainConfig(Environment env){
        this.env=env;
    }
}
