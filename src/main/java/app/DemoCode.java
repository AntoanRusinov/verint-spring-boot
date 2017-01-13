package app;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication // gor AuthenticationBuilder to work properly
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DemoCode extends WebSecurityConfigurerAdapter{

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		        auth.authenticationProvider(new AuthenticationProvider() {
		            
		        	@Override
		            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		                String email = (String) authentication.getPrincipal();
		                String providedPassword = (String) authentication.getCredentials();
		                
		                User user = null;//userRepository.findUserByEmail(email);
		                
		                if (user == null || !passwordEncoder().matches(providedPassword, user.getPassword())) {
		                    throw new BadCredentialsException("Username/Password does not match for " + authentication.getPrincipal());
		                }
		                
		                return new UsernamePasswordAuthenticationToken(email, providedPassword, Collections.singleton(new SimpleGrantedAuthority("USER")));
		            }

		            @Override
		            public boolean supports(Class<?> authentication) {
		                return true;
		            }
		        });
	    }

	   @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	
}
