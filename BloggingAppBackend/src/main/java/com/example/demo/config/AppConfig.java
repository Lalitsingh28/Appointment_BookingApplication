package com.example.demo.config;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.AuthEntry;
import com.example.demo.security.AuthToken;


@Configuration
public class AppConfig {
	
	
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
	
	
	  @Autowired
	  private CustomUser customUser;

	  @Autowired
	  private AuthEntry unauthorizedHandler;

	  @Bean
	  public AuthToken authenticationJwtTokenFilter() {
	    return new AuthToken();
	  }
	  
	  @Bean
	  public DaoAuthenticationProvider authenticationProvider() {
	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	       
	      authProvider.setUserDetailsService(customUser);
	      authProvider.setPasswordEncoder(passwordEncode());
	   
	      return authProvider;
	  }
	  
	  @Bean
	  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	  }
	  
	@Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.cors().and().csrf().disable()
	        .authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/auth/login","/user/register").permitAll()
			.requestMatchers("/auth/currentUser","/category/**").hasAuthority("ADMIN")
			.requestMatchers("/user/**","/post/**","/comment/**").hasAuthority("USER").and()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	    .authorizeHttpRequests().requestMatchers(HttpMethod.GET).permitAll().anyRequest().denyAll();
	    http.authenticationProvider(authenticationProvider());

	    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	    
	    return http.build();
	  }
	
	@Bean
	public PasswordEncoder passwordEncode() {	
		return new BCryptPasswordEncoder();
	}


}
