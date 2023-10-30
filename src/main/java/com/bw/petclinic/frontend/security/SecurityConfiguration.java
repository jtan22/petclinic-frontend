package com.bw.petclinic.frontend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {

    @Bean
    public UserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    /**
     * TODO: Disable CSRF for now, so that our POST request can proceed.
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(customizer -> customizer
                        .requestMatchers(HttpMethod.GET, "/owners/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/owners/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/pets/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/pets/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/visits/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/visits/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/vets/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/actuator/**").authenticated()
                        .anyRequest().permitAll())
//                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form.loginPage("/login").loginProcessingUrl("/authenticate").permitAll())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

}
