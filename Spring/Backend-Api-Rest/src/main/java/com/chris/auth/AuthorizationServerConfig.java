package com.chris.auth;

import antlr.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;  // Codificador de contraseñas.

   @Autowired
   @Qualifier("authenticationManager")
   private AuthenticationManager authenticationManager;  // Gestor de autenticación.

   @Autowired
   private InfoAdicionalToken infoAdicionalToken;  // Clase para añadir información adicional al token.

   // Configuración de la seguridad del servidor de autorización
   @Override
   public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
      security.tokenKeyAccess("permitAll()") // Permite el acceso público a la clave para generar tokens.
       .checkTokenAccess("isAuthenticated()"); // Requiere autenticación para verificar tokens.
   }

   // Configuración de clientes que pueden acceder al servidor de autorización
   @Override
   public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      clients.inMemory()
       .withClient("angular-app") // Cliente Angular
       .secret(passwordEncoder.encode("12345")) // Contraseña encriptada
       .scopes("read", "write") // Permisos del cliente
       .authorizedGrantTypes("password", "refresh_token") // Tipos de concesión de autorización
       .accessTokenValiditySeconds(3600) // Tiempo de vida del token de acceso
       .refreshTokenValiditySeconds(3600); // Tiempo de vida del token de actualización
   }

   /*
   Concesiones.
   Refresh_token: Es un tipo de token que permite a un cliente obtener un nuevo token de acceso sin tener que
   volver a autenticarse. Este token se intercambia por un nuevo token de acceso una vez que el token original
   expira. Ayuda a mantener la sesión activa sin requerir que el usuario vuelva a ingresar sus credenciales.
   Password: El cliente solicita un token de acceso proporcionando directamente el nombre de usuario y contraseña
   del usuario.
   Authorization Code: Típicamente utilizado por aplicaciones web server-side. El cliente redirige al usuario a
   una página de autorización donde el usuario inicia sesión y otorga permisos a la aplicación. Luego, el servidor
   de autorización redirige de vuelta al cliente con un código de autorización, que el cliente intercambia por
   un token de acceso y, opcionalmente, un refresh_token.
   Implicit: Principalmente utilizado por aplicaciones de cliente web donde la confidencialidad del cliente no
   puede ser garantizada. En este flujo, el token de acceso se obtiene directamente después de la autenticación
   del usuario, sin necesidad de un intercambio explícito de código de autorización.
   Client Credentials: La aplicación cliente auténtica directamente con el servidor de autorización utilizando
   sus propias credenciales (ID de cliente y secreto). No implica la participación de un usuario final; el token
   se emite en base a las credenciales de la aplicación cliente.
    */

   // Configuración de los endpoints del servidor de autorización
   @Override
   public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
      tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));

      endpoints.authenticationManager(authenticationManager) // Gestor de autenticación
       .tokenStore(tokenStore()) // Almacenamiento del token
       .accessTokenConverter(accessTokenConverter()) // Convertidor de tokens
       .tokenEnhancer(tokenEnhancerChain); // Mejora el token con información adicional
   }

   /*
   El TokenEnhancer es una interfaz que permite agregar información adicional al token generado.
   Puede utilizarse para incluir datos personalizados en el token JWT, como información de usuario,
   roles o cualquier otro detalle relevante.
    */

   // Bean para crear el almacenamiento del token JWT
   @Bean
   public JwtTokenStore tokenStore() {
      return new JwtTokenStore(accessTokenConverter());
   }


   // Bean para crear el convertidor de token JWT
   @Bean
   public JwtAccessTokenConverter accessTokenConverter() {
      JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
      jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVATE); // Clave para firmar el token en el servidor de autorización
      jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLIC); // Clave para verificar el token en el servidor de recursos
      return jwtAccessTokenConverter;
   }
}
