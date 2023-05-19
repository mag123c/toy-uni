package com.uu.uni.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.DispatcherType;
import static org.springframework.security.config.Customizer.withDefaults;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/img/**", "/css/**", "/js/**");
    }
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable().cors().disable()
	        .authorizeHttpRequests(request -> request	        		
	        	.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
	            .requestMatchers("/", "/users/**").permitAll()
                .anyRequest().authenticated()
	        )	        
	        .formLogin(login -> login
	                .loginPage("/users/signin")
	                .loginProcessingUrl("/users/signin")
	                .usernameParameter("id")
	                .passwordParameter("pw")
	                .defaultSuccessUrl("/uni/main", true)
	                .failureUrl("/users/signin")
	                .permitAll()
	        )
	        .logout(withDefaults());	// 로그아웃은 기본설정으로 (/logout으로 인증해제)
        
        
        
        return http.build();
	}


//		http.csrf().disable()
//			.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/users/**")).permitAll()        	        	
//			.and()
//				.formLogin()
//				.loginPage("/")
//				.defaultSuccessUrl("/uni/main");
//			.and()
//				.logout()
//				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
//				.logoutSuccessUrl("/")
//				.invalidateHttpSession(true);


	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}
