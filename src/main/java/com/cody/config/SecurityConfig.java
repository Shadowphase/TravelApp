package com.cody.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	AccessDeniedHandler accessDeniedHandler;

	@Autowired
	BCryptPasswordEncoder bCryptEncoder;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/","/home","/register","/registerUser","/rest/*","/rest/rooms/*","/rooms","/error").permitAll().anyRequest().authenticated()
		.and().logout().logoutSuccessUrl("/home").permitAll()
		.and().formLogin().loginPage("/login").permitAll()
		.and().exceptionHandling().accessDeniedPage("/login")
		.and().httpBasic();
		http
		.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
		.and().csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/images/*","/css/*","/js/*");
	}
}
