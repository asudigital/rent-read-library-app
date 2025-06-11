package com.crio.rent_read.config.security;

import com.crio.rent_read.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;




@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
  @Autowired
  UserService userService;

  @Autowired
  CustomAccessDeniedHandler customAccessDeniedHandler;
  
    // @Bean
    // SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    //     /**
    //     * CSRF, when enabled will not let you make requests from any client except for the whitelisted clients.
    //     * It is sometimes disabled while testing so that requests can be made from localhost and Postman.
    //     * CSRF can be disabled in Basic Auth as it sends the username and password in the heaaders.
    //     */
    //     httpSecurity.csrf(csrf -> csrf.disable());



    //     httpSecurity.authenticationProvider(authenticationProvider());

    //     //Filter all requests except for /login and /register
    //     httpSecurity.authorizeHttpRequests(configurer -> configurer
    //             .requestMatchers("/auth/signup", "/auth/login")
    //             // .requestMatchers("/auth/**")
    //             .permitAll()
    //             .anyRequest()
    //             .authenticated())               
    //             .exceptionHandling(exception -> 
    // exception.accessDeniedHandler(customAccessDeniedHandler)
    //         );

    //     // Explicitly tell Spring Security that we are using Basic Auth
    //     httpSecurity.httpBasic(Customizer.withDefaults());

    //     return httpSecurity.build();
    // }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrf -> csrf.disable()) // ✅ Disable CSRF for testing (Postman, etc.)
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(configurer -> configurer
                .requestMatchers("/auth/signup", "/auth/login").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> 
                exception.accessDeniedHandler(customAccessDeniedHandler) // ✅ Custom 403 handler
            )
            .httpBasic(Customizer.withDefaults()); // ✅ Basic Auth support (for headers in Postman)
    
        return httpSecurity.build();
    }
    


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }  
    
    @Bean
public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
    DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
    handler.setPermissionEvaluator(null); // optional if using permission evaluator
    return handler;
}

}


