package br.com.loginauth.infra.security;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                                .requestMatchers(HttpMethod.GET, "/user/all").permitAll()
                                .requestMatchers(HttpMethod.GET, "/user/{cpf}").permitAll()

                                .requestMatchers(HttpMethod.POST, "/student/register").permitAll()
                                .requestMatchers(HttpMethod.GET, "/student/{cpf}").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/student/update/{cpf}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/student/{cpf}/disciplines").permitAll()

                                .requestMatchers(HttpMethod.POST, "/parent/register").permitAll()
                                .requestMatchers(HttpMethod.GET, "/parent/{cpf}").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/parent/update/{cpf}").permitAll()

                                .requestMatchers(HttpMethod.POST, "/professor/register").permitAll()
                                .requestMatchers(HttpMethod.GET, "/professor/{cpf}").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/professor/update/{cpf}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/professor").permitAll()

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



                        // TASK 0059: Alterar autorização de acesso à rotas
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "https://mediotec-plus.vercel.app",
                "https://mediotec-frontend-fqikv39no-miguelamaral254s-projects.vercel.app"
        ));


        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));  // Cabeçalhos permitidos
        configuration.setAllowCredentials(true);  // Permite credenciais em requisições

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Configura o encoder de senhas como BCrypt
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();  // Retorna o manager de autenticação configurado
    }
}
