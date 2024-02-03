package com.labsandware.quickpoll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.labsandware.quickpoll.security.QuickPollUserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private QuickPollUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider authProvide =  new DaoAuthenticationProvider();

        authProvide.setUserDetailsService(userDetailsService);
        authProvide.setPasswordEncoder(passwordEncoder());

        return authProvide;
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .securityMatchers((matchers) -> matchers
                        .requestMatchers("/v3/polls/ **")
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/v1/**").permitAll()
                        .requestMatchers("/v2/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/api-docs/**").permitAll()
                        .anyRequest().hasRole("USER"))
                .httpBasic(Customizer.withDefaults())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
