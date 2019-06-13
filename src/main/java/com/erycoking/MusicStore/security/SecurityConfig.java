package com.erycoking.MusicStore.security;

import com.erycoking.MusicStore.config.CorsFilter;
import com.erycoking.MusicStore.security.jwt.JwtConfigurer;
import com.erycoking.MusicStore.security.jwt.JwtTokenProvider;
import com.erycoking.MusicStore.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder passwordEncoder;
    private ClientService clientService;
    private JwtTokenProvider jwtTokenProvider;
    private CorsFilter corsFilter;

    @Autowired
    public SecurityConfig(BCryptPasswordEncoder passwordEncoder, ClientService clientService, JwtTokenProvider jwtTokenProvider, CorsFilter corsFilter) {
        this.passwordEncoder = passwordEncoder;
        this.clientService = clientService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.corsFilter = corsFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(clientService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/auth/signin").permitAll()
                    .antMatchers(HttpMethod.GET, "/songs**").authenticated()
                    .antMatchers(HttpMethod.PUT, "/songs**").hasAnyRole(new String[]{"ROLE_ADMIN", "ROLE_CO_ADMIN"})
                    .antMatchers(HttpMethod.DELETE, "/songs**").hasAnyRole(new String[]{"ROLE_ADMIN", "ROLE_CO_ADMIN"})
                    .antMatchers(HttpMethod.GET, "/artist**").authenticated()
                    .antMatchers(HttpMethod.PUT, "/artist**").hasAnyRole(new String[]{"ROLE_ADMIN", "ROLE_CO_ADMIN"})
                    .antMatchers(HttpMethod.DELETE, "/artist**").hasAnyRole(new String[]{"ROLE_ADMIN", "ROLE_CO_ADMIN"})
                    .antMatchers("/playlists**").authenticated()
                    .anyRequest()
                    .permitAll()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}