package net.engineeringdigest.journalApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.engineeringdigest.journalApp.filter.JwtFilter;
import net.engineeringdigest.journalApp.service.CustomUserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class SpringSecuirity   {
	
	@Autowired
	CustomUserDetailsServiceImpl userDetailsServiceImpl;
	
	 @Autowired
	    private JwtFilter jwtFilter;
	
//	 @Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	        http
//	            .authorizeHttpRequests(authorize -> authorize
//	                .antMatchers("/public/**").permitAll() // Allow public access
//	                .antMatchers("/journal/**").authenticated() // Require authentication
//	               // .antMatchers("/user/**").authenticated() // Require authentication
//	                .antMatchers("/admin/**").hasRole("ADMIN") // Admin role required
//	                .anyRequest().authenticated() // All other requests require authentication
//	            )
//	            .httpBasic() // Enable basic authentication
//	            .and()
//	            .csrf().disable(); // Disable CSRF protection
//
//	        return http.build();
//	    }
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


	        return http.authorizeHttpRequests(request -> request
	                        .antMatchers("/public/**").permitAll()
	                        .antMatchers("/journal/**", "/user/**").authenticated()
	                        .antMatchers("/admin/**").hasRole("ADMIN")
	                        .anyRequest().authenticated())
	                .csrf(AbstractHttpConfigurer::disable)
	                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
	                .build();
	    }


	 
	  @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	    }


	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	 
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }

}
