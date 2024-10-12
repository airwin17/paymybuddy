package com.pmb.paymybuddy.configuration;

import java.util.ArrayList;
import java.util.List;
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
    
    
    /**
     * <li>Configures the security filter chain with the following rules:
     *   <ul>
     *     <li>CSRF is disabled.</li>
     *     <li>anyone can create new account.</li>
     *     <li>Authenticated users can access specific URLs related to their accounts and transactions.</li>
     *     <li>Specifies the login form and its processing URL.</li>
     *     <li>Configures the logout URL and its success URL.</li>
     *   </ul>
     * </li>
     * 
     */
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
