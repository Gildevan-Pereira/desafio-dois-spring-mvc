package aac.br.springmvc_tres.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for setting up security rules.
 */
@Configuration
public class SecurityConfig {

    /**
     * Bean for password encoding using BCrypt.
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for in-memory user details service with predefined users and roles.
     * @return UserDetailsService
     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build());
//        manager.createUser(User.withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build());
//        return manager;
//    }

    /**
     * Bean for configuring HTTP security with authorization and authentication rules.
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationSuccessHandler successHandler) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/public/**").permitAll()  // Allow unauthenticated access to login and public pages
                        .requestMatchers("/register", "/public/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")       // Restrict access to /admin/** to ADMIN role only
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")  // Allow access to /user/** for USER and ADMIN roles
                        .requestMatchers("/h2-console/**").permitAll()       // Allow access to H2 console
                        .anyRequest().authenticated()                        // All other requests require authentication
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                )
                .formLogin(form -> form
                        .loginPage("/login")                                // Custom login page
                        .defaultSuccessUrl("/home", true)              // Redirect to home after successful login
                        .successHandler(successHandler)
                        .permitAll()                                        // Allow all to access login page
                )
                .logout(logout -> logout
                        .permitAll()                                        // Allow all to log out
                );

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}