package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication // for AuthenticationBuilder to work properly
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	/*
	 * login and logout functionalities are enabled by default
	*/
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
		 	.authorizeRequests()
        	.antMatchers("/login").permitAll()
        	.antMatchers("/api/ping").permitAll()
        	.antMatchers("/jms/**").permitAll()
        	.anyRequest().authenticated()
            .and()
            .formLogin().permitAll()
            .and().httpBasic();
		
		http
			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

		/*
		Specify to allow any request that comes from the same origin to 
		frame this application. For example, if the application was hosted
		 on example.com, thenauth example.com could frame the application, 
		 but evil.com could not frame the application.
		*/
		http
		.headers().frameOptions().sameOrigin();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER").and()
        		.withUser("admin").password("secret").roles("ADMIN");
        
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}