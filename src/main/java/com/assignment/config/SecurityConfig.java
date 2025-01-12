package com.assignment.config;

import com.assignment.auth.CustomAuthenticationProvider;
import com.assignment.auth.JwtAuthFilter;
import com.assignment.service.PetitionerSigningService;
import com.assignment.service.SLPPUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
    @Autowired
    private PetitionerSigningService petitionerSigningService;

    @Autowired
    private SLPPUserDetailsService userDetailsService;

    @Autowired
    JwtAuthFilter jwtAuthFilter;

   /* @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/petitioner/auth/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/petition-committee/**").hasRole("ADMIN") // Only ADMIN role can access
                .antMatchers("/slpp/**").hasAnyRole("USER", "ADMIN") // USER and ADMIN roles can access
                .anyRequest().authenticated() // All other endpoints require authentication
                .and()
                .sessionManagement()
                .sessionFixation().migrateSession() // Default: protect against session fixation attacks
                .maximumSessions(1) // Allow only one session per user
                .and().and()
                .csrf().disable()
                .cors().configurationSource(corsFilter()).and()
                .formLogin()
                .loginPage("http://localhost:3000/petitioner")
                .defaultSuccessUrl("http://localhost:3000/petitioner-dashboard", true)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("http://localhost/app/")
                .permitAll();
        return http.build();
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable().cors().configurationSource(corsFilter()).and()
                .authorizeRequests()
                .antMatchers("/api/petitioner/auth/signup", "/api/petitioner/auth/login3", "/swagger-ui/**", "/api/resources/**").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/api/petition-committee/**").hasRole("ADMIN") // Only ADMIN role can access
                .antMatchers("/api/slpp/**").hasAnyRole("USER", "ADMIN") // USER and ADMIN roles can access
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/api/petitioner/auth/logout")
                .logoutSuccessUrl("/app/")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();

    }



    @Bean
    public UrlBasedCorsConfigurationSource corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Allow cookies across origins
        config.addAllowedOriginPattern("*"); // Use "*" for all origins or specify your frontend URL
        config.addAllowedHeader("*"); // Allow all headers
        config.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, etc.)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }


}