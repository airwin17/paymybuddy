package com.pmb.paymybuddy.configuration;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.pmb.paymybuddy.services.UserService;


@Configuration
@EnableWebSecurity
public class DatasourceConfig {
        
    @Bean
    public DataSource getDatasource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/paymybuddydb");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("rootroot");
        return dataSourceBuilder.build();
    }
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf->csrf.disable())
        .authorizeHttpRequests(req->req
        .requestMatchers("/api/user/saveUser").permitAll()
        .requestMatchers("/api/user/**").authenticated()
        .requestMatchers("/profil").permitAll()
        .requestMatchers("/addRelationship").permitAll()
        .requestMatchers("/addTransaction").permitAll()
        .requestMatchers("/signin").permitAll()
        .anyRequest().permitAll()).formLogin(login->login.loginPage("/loginPage").permitAll()
        .loginProcessingUrl("/login"))
        .authenticationManager(authenticationProvider());
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }
    @Bean 
    public ProviderManager authenticationProvider() {
        List<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProviderList.add(daoAuthenticationProvider);
        ProviderManager providerManager = new ProviderManager(authenticationProviderList);
        return providerManager;
    }
}
