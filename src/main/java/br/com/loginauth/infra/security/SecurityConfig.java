package br.com.loginauth.infra.security;

import br.com.loginauth.infra.cors.CorsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/{cpf}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/student/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/student/{cpf}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/student/{cpf}/school-class").permitAll()
                        .requestMatchers(HttpMethod.GET, "/student/{cpf}/lessons").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/student/update/{cpf}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/student/{cpf}/disciplines").permitAll()
                        .requestMatchers(HttpMethod.GET, "/student").permitAll()
                        .requestMatchers(HttpMethod.POST, "/parent/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/parent/{cpf}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/parent/update/{cpf}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/professor/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/professor/{cpf}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/professor/update/{cpf}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/professor").permitAll()
                        .requestMatchers(HttpMethod.GET, "/professor/{cpf}/disciplines").permitAll()
                        .requestMatchers(HttpMethod.GET, "/professor/professor/{cpf}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/notifications/send").permitAll()
                        .requestMatchers(HttpMethod.GET, "/notifications/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/notifications/update").permitAll()
                        .requestMatchers(HttpMethod.POST, "/coordination/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/coordination/{cpf}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/coordination/update/{cpf}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/schoolclasses").permitAll()
                        .requestMatchers(HttpMethod.GET, "/schoolclasses").permitAll()
                        .requestMatchers(HttpMethod.GET, "/schoolclasses/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/schoolclasses/{classId}/students").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/schoolclasses/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/schoolclasses/addstudent").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/schoolclasses/{classId}/students/{studentCpf}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/lessons").permitAll()
                        .requestMatchers(HttpMethod.GET, "/lessons").permitAll()
                        .requestMatchers(HttpMethod.GET, "/lessons/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/lessons/name/{name}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/lessons/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/lessons/student/{cpf}/class/{schoolClassId}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/disciplines").permitAll()
                        .requestMatchers(HttpMethod.GET, "/disciplines/student/{cpf}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/disciplines").permitAll()
                        .requestMatchers(HttpMethod.GET, "/disciplines/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/disciplines/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/grades").permitAll()
                        .requestMatchers(HttpMethod.GET, "/grades").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/grades/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/grades/student/{cpf}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/grades/student/{cpf}/discipline/{disciplineId}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/grades/assessment/{assessmentId}/grades").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/grades/{id}").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
