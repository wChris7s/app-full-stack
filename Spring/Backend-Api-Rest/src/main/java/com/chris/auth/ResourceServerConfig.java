package com.chris.auth;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

   // Configuración de las reglas de seguridad para los endpoints
   @Override
   public void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
       // Se permite el acceso a ciertos endpoints sin autenticación
       .antMatchers(HttpMethod.GET, "/api/clientes", "/api/clientes/page/**", "/api/upload/img/**", "/images/**").permitAll()
       /*
       .antMatchers("/api/clientes/{id}").permitAll()
       .antMatchers("/api/facturas/**").permitAll()
       .antMatchers(HttpMethod.GET, "/api/clientes/{id}").hasAnyRole("USER", "ADMIN")
       .antMatchers(HttpMethod.POST, "/api/clientes/upload").hasAnyRole("USER", "ADMIN")
       .antMatchers(HttpMethod.POST, "/api/clientes").hasRole("ADMIN")
       .antMatchers("/api/clientes/**").hasRole("ADMIN")*/
       .anyRequest().authenticated() // Se requiere autenticación para cualquier otra solicitud
       .and().cors().configurationSource(corsConfigurationSource()); // Configuración de CORS
   }

   // Configuración de CORS (Cross-Origin Resource Sharing)
   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowedOrigins(List.of("http://localhost:4200")); // Origen permitido
      config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos HTTP permitidos
      config.setAllowCredentials(true); // Permitir credenciales (cookies, por ejemplo)
      config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization")); // Encabezados permitidos

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", config); // Configura CORS para todas las rutas
      return source;
   }

   // Configuración de un filtro CORS para aplicar la configuración CORS a todas las solicitudes
   @Bean
   public FilterRegistrationBean<CorsFilter> corsFilter() {
      FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
      bean.setOrder(Ordered.HIGHEST_PRECEDENCE); // Define la prioridad del filtro CORS
      return bean;
   }

   /*
   Esto es crucial para que la configuración CORS se aplique temprano en el ciclo de vida de la solicitud,
   permitiendo que las reglas de CORS se verifiquen y apliquen antes de que otros filtros procesen la solicitud.
   Esto garantiza que la lógica CORS se ejecute primero y tenga efecto en todas las solicitudes, permitiendo
   o restringiendo el acceso según la configuración definida.
    */
}
