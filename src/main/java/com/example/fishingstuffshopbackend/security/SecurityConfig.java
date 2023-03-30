package com.example.fishingstuffshopbackend.security;

import com.example.fishingstuffshopbackend.filter.CustomAuthenticationFilter;
import com.example.fishingstuffshopbackend.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenManager tokenManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authenticationManagerBean(), tokenManager);
        filter.setFilterProcessesUrl("/api/v*/login");

        http.csrf()
                .disable();
        http.cors()
                .disable();
        http.sessionManagement()
                .sessionCreationPolicy(STATELESS);
        http.authorizeRequests()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**")
                .permitAll();
        http.authorizeRequests()
                .antMatchers("/api/v*/login/**", "/api/v*/token/refresh/**", "/api/v*/registration/**")
                .permitAll();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v*/category/**", "/api/v*/product/**")
                .permitAll();
        http.authorizeRequests()
                .antMatchers("/api/v*/user/**")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN");
        http.authorizeRequests()
                .anyRequest()
                .authenticated();
        http.addFilter(filter);
        http.addFilterBefore(new CustomAuthorizationFilter(tokenManager), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
