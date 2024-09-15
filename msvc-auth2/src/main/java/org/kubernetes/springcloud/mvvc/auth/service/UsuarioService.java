package org.kubernetes.springcloud.mvvc.auth.service;


import org.kubernetes.springcloud.mvvc.auth.modals.dto.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private WebClient.Builder client;

    @Autowired
    private Environment env;

    private Logger log = LoggerFactory.getLogger(UsuarioService.class);
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        try {
            Usuario usuario = client.build()
                    .get()
//                    .uri(env.getProperty("LB_USUARIOS_URI")+ "/login", uri -> uri.queryParam("email", email).build())
                    .uri("http://msvc-usuario/login", uri -> uri.queryParam("email", email).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Usuario.class)
                    .block();
            log.info("usuario login: {}", usuario.getEmail());
            log.info("usuario nombre: {}", usuario.getNombre());
            log.info("password: {}", usuario.getPassword());
            return new User(email, usuario.getPassword(), true, true, true,true, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        }
        catch (RuntimeException e)
        {
            String error = "Error en el login, no existe el usuario " + email + " en el sistema";
            log.error(error);
            log.error(e.getMessage());
            throw new UsernameNotFoundException(error);
        }
    }
}
