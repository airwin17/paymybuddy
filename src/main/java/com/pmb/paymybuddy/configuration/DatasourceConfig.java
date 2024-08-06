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

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.pmb.paymybuddy.repositories.BankAcountRepository;
import com.pmb.paymybuddy.repositories.ConnectionRepository;
import com.pmb.paymybuddy.repositories.UserRepository;
import com.pmb.paymybuddy.services.UserService;




@Configuration
@EnableWebSecurity
public class DatasourceConfig {
    private UserRepository userRepository;
    private BankAcountRepository bankAcountRepository;
    private ConnectionRepository connectionRepository;
    
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
        .requestMatchers("/api/transaction/saveTransaction").authenticated()
        .requestMatchers("/profil").authenticated()
        .requestMatchers("/addRelationship").authenticated()
        .requestMatchers("/addTransaction").authenticated()
        .requestMatchers("/signin").anonymous()
        .anyRequest().permitAll()).formLogin(login->login.loginPage("/loginPage").permitAll()
        .loginProcessingUrl("/login").usernameParameter("email").defaultSuccessUrl("/addTransaction")
        ).logout(logout->logout.logoutUrl("/logout").logoutSuccessUrl("/loginPage"))
        .authenticationManager(authenticationProvider());
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean public UserService userService(UserRepository userRepository, BankAcountRepository bankAcountRepository, ConnectionRepository connectionRepository) {
        return new UserService(userRepository,bankAcountRepository,connectionRepository);
    }
    @Bean 
    public ProviderManager authenticationProvider() {
        List<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService(userRepository,bankAcountRepository,connectionRepository));
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProviderList.add(daoAuthenticationProvider);
        ProviderManager providerManager = new ProviderManager(authenticationProviderList);
        return providerManager;
    }
}
