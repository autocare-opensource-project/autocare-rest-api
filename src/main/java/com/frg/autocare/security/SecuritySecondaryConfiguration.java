/**
 * AutoCare REST API - Security configuration.
 * Copyright (C) 2024  AutoCare REST API original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this application.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.frg.autocare.security;

import static com.frg.autocare.enums.Permission.ADMIN_CREATE;
import static com.frg.autocare.enums.Permission.ADMIN_DELETE;
import static com.frg.autocare.enums.Permission.ADMIN_READ;
import static com.frg.autocare.enums.Permission.ADMIN_UPDATE;
import static com.frg.autocare.enums.Permission.MANAGER_CREATE;
import static com.frg.autocare.enums.Permission.MANAGER_DELETE;
import static com.frg.autocare.enums.Permission.MANAGER_READ;
import static com.frg.autocare.enums.Permission.MANAGER_UPDATE;
import static com.frg.autocare.enums.Role.ADMIN;
import static com.frg.autocare.enums.Role.MANAGER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecuritySecondaryConfiguration {

  private final AuthenticationProvider authenticationProvider;

  @Value("${app.jwt.secret}")
  private String jwtSecret;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            req ->
                req.requestMatchers(
                        "/api/v1/auth/**",
                        "/.well-known/jwks.json",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html",
                        "/api-docs/**",
                        "/h2-console/**",
                        "/actuator/**")
                    .permitAll()
                    .requestMatchers("/api/v1/management/**")
                    .hasAnyRole(ADMIN.name(), MANAGER.name())
                    .requestMatchers(GET, "/api/v1/management/**")
                    .hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                    .requestMatchers(POST, "/api/v1/management/**")
                    .hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                    .requestMatchers(PUT, "/api/v1/management/**")
                    .hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                    .requestMatchers(DELETE, "/api/v1/management/**")
                    .hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
                    .requestMatchers("/api/v1/admin/**")
                    .hasRole(ADMIN.name())
                    .requestMatchers(GET, "/api/v1/admin/**")
                    .hasAuthority(ADMIN_READ.name())
                    .requestMatchers(POST, "/api/v1/admin/**")
                    .hasAuthority(ADMIN_CREATE.name())
                    .requestMatchers(PUT, "/api/v1/admin/**")
                    .hasAuthority(ADMIN_UPDATE.name())
                    .requestMatchers(DELETE, "/api/v1/admin/**")
                    .hasAuthority(ADMIN_DELETE.name())
                    .anyRequest()
                    .authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .oauth2ResourceServer(
            oauth2 ->
                oauth2.jwt(
                    (OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer jwt) ->
                        jwt.decoder(jwtDecoder())
                            .jwtAuthenticationConverter(jwtAuthenticationConverter())))
        // For H2 console
        .headers(
            headers ->
                headers.contentSecurityPolicy(
                    (HeadersConfigurer<HttpSecurity>.ContentSecurityPolicyConfig csp) ->
                        csp.policyDirectives("frame-ancestors 'self'")));

    return http.build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    return new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecret.getBytes()));
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    byte[] bytes = jwtSecret.getBytes();
    SecretKeySpec originalKey = new SecretKeySpec(bytes, 0, bytes.length, "HMACSHA256");
    return NimbusJwtDecoder.withSecretKey(originalKey).macAlgorithm(MacAlgorithm.HS256).build();
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
    authoritiesConverter.setAuthorityPrefix("");
    authoritiesConverter.setAuthoritiesClaimName("scope");

    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
    return converter;
  }
}
