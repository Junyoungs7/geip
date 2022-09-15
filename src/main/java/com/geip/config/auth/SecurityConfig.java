package com.geip.config.auth;

import com.geip.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/loginform", "/css/**", "/img/**", "/js/**", "/lib/**", "/scss/**").permitAll()
                .antMatchers("/").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        http.formLogin()
                .loginPage("/loginform")
                .defaultSuccessUrl("/")
                .failureUrl("/loginform");

        return http.build();
    }
}
