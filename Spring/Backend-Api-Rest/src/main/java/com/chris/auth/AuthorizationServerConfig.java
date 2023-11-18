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
   private BCryptPasswordEncoder passwordEncoder;

   @Autowired
   @Qualifier("authenticationManager")
   private AuthenticationManager authenticationManager;

   @Autowired
   private InfoAdicionalToken infoAdicionalToken;

   @Override
   public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
      security.tokenKeyAccess("permitAll()") // Genera el token
       .checkTokenAccess("isAuthenticated()"); // Valida el token;
   }

   @Override
   public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      clients.inMemory().withClient("angular-app")     //  Almacenamiento en memoria - Cliente: angular-app
       .secret(passwordEncoder.encode("12345"))    // Contraseña encriptada 12345
       .scopes("read", "write")                                // Alcance/permisos del cliente.
       .authorizedGrantTypes("password", "refresh_token")      // Tipos de conseción
       .accessTokenValiditySeconds(3600)                       // Tiempo de expiración del token.
       .refreshTokenValiditySeconds(3600);
   }

   @Override
   public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
      tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));
      endpoints.authenticationManager(authenticationManager)
       .tokenStore(tokenStore())
       .accessTokenConverter(accessTokenConverter())
       .tokenEnhancer(tokenEnhancerChain);
   }

   @Bean
   public JwtTokenStore tokenStore() {
      return new JwtTokenStore(accessTokenConverter());
   }


   @Bean
   public JwtAccessTokenConverter accessTokenConverter() {
      JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
      jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVATE);   // Crea el token en el servidor de autenticación.
      jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLIC);   // Valida el token en el servidor de recursos.
      return jwtAccessTokenConverter;
   }
}
