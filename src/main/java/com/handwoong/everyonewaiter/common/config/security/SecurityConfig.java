package com.handwoong.everyonewaiter.common.config.security;

import com.handwoong.everyonewaiter.common.config.client.ClientConfig;
import com.handwoong.everyonewaiter.common.config.security.handler.CustomAccessDeniedHandler;
import com.handwoong.everyonewaiter.common.config.security.handler.CustomAuthenticationEntryPoint;
import com.handwoong.everyonewaiter.common.config.security.uri.AccessAllowUri;
import com.handwoong.everyonewaiter.common.config.security.uri.AdminAllowUri;
import com.handwoong.everyonewaiter.common.config.security.uri.AllowUri;
import com.handwoong.everyonewaiter.common.config.security.uri.AnonymousAllowUri;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ClientConfig clientConfig;
    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        configureBasicSecurity(http);
        configureAuthorizationSecurity(http);
        configureSecurityExceptionHandler(http);
        configureSecurityFilter(http);
        return http.build();
    }

    private void configureBasicSecurity(final HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private void configureAuthorizationSecurity(final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
            auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                .requestMatchers(convertUriToPathMatcher(AccessAllowUri.values())).permitAll()
                .requestMatchers(convertUriToPathMatcher(AnonymousAllowUri.values())).anonymous()
                .requestMatchers(convertUriToPathMatcher(AdminAllowUri.values())).hasRole("ADMIN")
                .anyRequest().authenticated()
        );
    }

    private AntPathRequestMatcher[] convertUriToPathMatcher(final AllowUri[] allowUris) {
        return Arrays.stream(allowUris)
            .map(AllowUri::getUri)
            .map(AntPathRequestMatcher::antMatcher)
            .toArray(AntPathRequestMatcher[]::new);
    }

    private void configureSecurityExceptionHandler(final HttpSecurity http) throws Exception {
        http.exceptionHandling(exceptionHandling ->
            exceptionHandling
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler()));
    }

    private void configureSecurityFilter(final HttpSecurity http) {
        http.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(List.of(clientConfig.getClientUrl()));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
