package com.witl.kalimba.webplayer;

/*@auther : Ambarish Kumar
 * 
 */

import javax.sql.DataSource;
 



 



import com.witl.kalimba.webplayer.UserJDBCTemplate;
//import com.witl.kalimba.webplayer.ADUserJDBCTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
 
@Configuration
@ComponentScan(basePackages="com.witl.kalimba.webplayer")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter{
 
	//Conf conf = new Conf();
    @Bean
    public ViewResolver getViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
     
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
 
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://108.59.85.14:3306/MELODY");
        dataSource.setUsername("kalimbauser");
        dataSource.setPassword("Mel0di@K@limb@");
         
        return dataSource;
    }
     
    @Bean
    public UserJDBCTemplate getUserJDBCTemplate() {
        return new UserJDBCTemplate(getDataSource());
    }
    
   /* @Bean
    public SonicUserJDBCTemplate getSonicUserJDBCTemplate() {
        return new SonicUserJDBCTemplate(getDataSource());
    }
    */
    /*@Bean
    public ADUserJDBCTemplate getAdUserJDBCTemplate() {
        return new ADUserJDBCTemplate(getDataSource());
    }*/
}