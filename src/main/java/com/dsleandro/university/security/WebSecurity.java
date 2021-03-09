package com.dsleandro.university.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
			.antMatchers("/resources/**", "/css/**").permitAll()
			.antMatchers("/signup").permitAll()
			.antMatchers("/student/**").hasAnyAuthority("ROLE_STUDENT")
			.antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
			.anyRequest().authenticated().and()
		.formLogin()
			.loginPage("/login").failureUrl("/login?error=true")
			.defaultSuccessUrl("/")
			.usernameParameter("dni")
			.passwordParameter("password").and()
		.logout()
			.permitAll()
			.logoutSuccessUrl("/login").and()
		.exceptionHandling().accessDeniedPage("/access_denied");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

}
