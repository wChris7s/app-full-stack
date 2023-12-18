package com.chris.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


// Habilita la seguridad a nivel de método utilizando anotaciones como @Secured
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
   @Autowired
   private UserDetailsService userDetailsService;


   @Bean
   // Nos permite registrar lo que devuelve un método en el contenedor de Spring, lo que nos permite hacer Autowired.
   // Bean para devolver un codificador de contraseñas BCrypt.
   public static BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   // Configuración del AuthenticationManager con un UserDetailsService y el codificador de contraseñas
   @Override
   @Autowired
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
   }

   // Bean para exponer el AuthenticationManager como un bean de Spring
   @Bean("authenticationManager")
   @Override
   protected AuthenticationManager authenticationManager() throws Exception {
      return super.authenticationManager();
   }

   // Configuración de las reglas de seguridad HTTP
   @Override
   public void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
       .anyRequest().authenticated() // Todas las solicitudes requieren autenticación
       .and()
       .csrf().disable() // Deshabilita la protección CSRF
       .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Configura la política de manejo de sesiones como sin estado (stateless)
   }

   /*
   La protección CSRF es una medida de seguridad que evita que ataques maliciosos suplanten la identidad del usuario
   y realicen acciones no deseadas en su nombre. Funciona generando tokens únicos y aleatorios en el lado del servidor
   y luego comparando esos tokens en las solicitudes POST para asegurarse de que provengan del mismo sitio web que
   los generó.
   http.csrf().disable(): Esta línea deshabilita la protección CSRF. En ciertos casos, cuando estás creando una
   API REST o manejando la seguridad de otra manera, puedes decidir deshabilitar CSRF porque la aplicación no
   mantiene un estado de sesión entre solicitudes y no almacena cookies de sesión. Sin embargo, deshabilitar
   CSRF puede dejar la aplicación vulnerable a ciertos tipos de ataques si no se implementan otras medidas de
   seguridad.

   http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS): Esta configuración indica que
   la aplicación no mantendrá estados de sesión en el servidor entre solicitudes. En aplicaciones stateless, cada
   solicitud HTTP se trata como independiente y no se mantiene información de sesión en el servidor. Es común en
   aplicaciones RESTful y APIs donde cada solicitud debe llevar toda la información necesaria para procesarla,
   sin depender de estados anteriores almacenados en el servidor.
   El uso de STATELESS es útil para aplicaciones donde no necesitas mantener un estado de sesión en el servidor,
   lo que puede reducir la carga y complejidad en la gestión del estado de la sesión del usuario.
    */
}
