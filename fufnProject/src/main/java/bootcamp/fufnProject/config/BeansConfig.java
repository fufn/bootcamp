package bootcamp.fufnProject.config;

import bootcamp.fufnProject.beans.DataBaseBean;
import bootcamp.fufnProject.services.UserService;
import bootcamp.fufnProject.services.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean(name = "db")
    public DataBaseBean dataBaseBean(){
        return new DataBaseBean();
    }

    @Bean
    public UserService userService(){
        return new UserServiceImpl();
    }

}
