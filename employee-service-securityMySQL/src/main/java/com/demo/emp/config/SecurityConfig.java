package com.demo.emp.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private final String USER_NAME = "test";
	private final String PASSWORD = "test@123";
	private final String ROLE = "USER";
	
	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().
	 * httpBasic(); http.sessionManagement()
	 * .sessionCreationPolicy(SessionCreationPolicy.STATELESS).maximumSessions(10);
	 * 
	 * 
	 * }
	 */

	/*
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
	 * throws Exception { PasswordEncoder encoder =
	 * PasswordEncoderFactories.createDelegatingPasswordEncoder();
	 * auth.inMemoryAuthentication().withUser(USER_NAME).password(encoder.encode(
	 * PASSWORD)).roles(""); }
	 */
	
	
	@Override
    protected void configure(HttpSecurity httpSecurity)
      throws Exception {
        httpSecurity.authorizeRequests()
          .anyRequest()
          .authenticated()
          .and()
          .formLogin();
         
        httpSecurity.headers()
          .frameOptions()
          .sameOrigin();
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
	  throws Exception {
	    auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery("select email_id,password,enabled "
	        + "from tbl_user_details "
	        + "where email_id = ?")
	      .authoritiesByUsernameQuery("select email_id,authority "
	        + "from authorities "
	        + "where email_id = ?");
	}
	 
	
	  
	 
	
	
}