package com.blender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebSecurity
@EnableWebMvc
public class WebSecurity extends WebSecurityConfigurerAdapter {



     @Override
     protected void configure(HttpSecurity http) throws Exception {
          http.csrf().disable()
                  .authorizeRequests()
                  .antMatchers("/api/**").permitAll()
                  .antMatchers("/actuator/**").permitAll()
                  .anyRequest()
                  .authenticated()
                  .and()
                  .httpBasic();
     }
     @Bean
     public PasswordEncoder passwordEncoder(){

          return new BCryptPasswordEncoder();
     }


}
