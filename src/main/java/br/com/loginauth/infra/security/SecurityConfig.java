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
    private SecurityFilter securityFilter;  // Seu filtro de segurança customizado

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Desabilitando CSRF pois a aplicação é stateless
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Adicionando suporte ao CORS
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Configurando como stateless
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()  // Permite o login sem autenticação
                                .requestMatchers(HttpMethod.POST, "/user/register").permitAll()  // Permite o registro sem autenticação
                                .requestMatchers(HttpMethod.POST, "/user/register/student").permitAll()  // Permite o registro de student sem autenticação
                                .requestMatchers(HttpMethod.POST, "/user/register/parent").permitAll()  // Permite o registro de parent sem autenticação
                                .requestMatchers(HttpMethod.POST, "/user/register/professor").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/register/coordination").permitAll()// Permite o registro de professor sem autenticação
                                .requestMatchers(HttpMethod.GET, "/user/all").permitAll()  // Permite pegar todos os usuários sem autenticação
                                .requestMatchers(HttpMethod.POST, "/schoolclasses").permitAll()
                                .requestMatchers(HttpMethod.GET, "/schoolclasses").permitAll()
                                .requestMatchers(HttpMethod.POST, "/lessons").permitAll()
                                .requestMatchers(HttpMethod.GET, "/lessons").permitAll()
                                .requestMatchers(HttpMethod.POST, "/disciplines").permitAll()
                                .requestMatchers(HttpMethod.GET, "/disciplines").permitAll()
                                .requestMatchers(HttpMethod.POST, "/schoolclasses/addstudent").permitAll()
                                .requestMatchers(HttpMethod.GET, "/schoolclasses").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() //swagger""

                                .anyRequest().authenticated()

                        // TASK 0059: Alterar autorização de acesso à rotas
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));  // Permite acesso ao front-end no localhost:4200
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Métodos HTTP permitidos
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
