package com.sc.webim.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sc.webim.services.UserDetailsServiceDefault;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().
			antMatchers("/login").permitAll().
			antMatchers("/threads/**").permitAll().
			antMatchers("/drone").permitAll().
			antMatchers("/drone/*").permitAll().
			antMatchers("/user").hasAnyRole("DIRETTORE").
			antMatchers("/direttore/**").hasAnyRole("DIRETTORE").
			antMatchers("/css/**").permitAll().
			antMatchers("/images/**").permitAll().
			antMatchers("/").permitAll().
			antMatchers("/**").hasAnyRole("DRONE", "DIRETTORE").
				and().formLogin().loginPage("/login").defaultSuccessUrl("/threads", true)
					.successForwardUrl("/threads")
					.defaultSuccessUrl("/threads")
					.failureUrl("/login?error=true").permitAll().
				and().logout().logoutSuccessUrl("/") // NB se commentiamo
														// questa riga,
														// viene richiamata
														// /login?logout
					.invalidateHttpSession(true).permitAll().
			and().csrf().disable();
	}

	@Bean
	public UserDetailsService groupDetailsService() {
		return new UserDetailsServiceDefault();
	};

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(groupDetailsService()).passwordEncoder(this.passwordEncoder);

	}

}