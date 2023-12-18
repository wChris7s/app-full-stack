package com.chris.auth;

import com.chris.entity.Usuario;
import com.chris.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InfoAdicionalToken implements TokenEnhancer {

   @Autowired
   private UserService userService;

   // Método de la interfaz TokenEnhancer para mejorar el token de acceso
   @Override
   public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
      // Obtiene información del usuario utilizando el servicio UserService
      Usuario usuario = userService.findByUsername(oAuth2Authentication.getName());

      // Crea un mapa para almacenar información adicional que se agregará al token
      Map<String, Object> info = new HashMap<>();

      // Agrega información personalizada al mapa
      info.put("info_adicional", "Hola que tal!: ".concat(oAuth2Authentication.getName()));
      info.put("nombre", usuario.getNombre());
      info.put("apellido", usuario.getApellido());
      info.put("email", usuario.getEmail());

      // Agrega la información adicional al token de acceso
      ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);

      // Devuelve el token de acceso con la información adicional agregada
      return oAuth2AccessToken;
   }
}
