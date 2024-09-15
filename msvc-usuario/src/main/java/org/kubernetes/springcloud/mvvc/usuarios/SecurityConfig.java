package org.kubernetes.springcloud.mvvc.usuarios;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/authorized","/login").permitAll()
                .antMatchers(HttpMethod.GET, "/list", "/{id}").hasAnyAuthority("SCOPE_read", "SCOPE_write")
                .antMatchers(HttpMethod.POST, "/insert").hasAuthority("SCOPE_write")
                .antMatchers(HttpMethod.PUT, "/update/{id}").hasAuthority("SCOPE_write")
                .antMatchers(HttpMethod.DELETE, "/delete/{id}").hasAuthority("SCOPE_write")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/msvc-usuario-client")).csrf().disable()
                .oauth2Client(withDefaults()).csrf().disable()
                .oauth2ResourceServer().jwt();

        return http.build();
    }
}
