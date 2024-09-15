package org.kubernetes.springcloud.mvvc.client;

import org.kubernetes.springcloud.mvvc.modals.dto.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuario", url = "http://msvc-usuario:8001")
public interface UsuarioClientRest {

    @GetMapping(value = "/{id}")
    Usuario detalle(@PathVariable Long id, @RequestHeader(value = "Authorization", required = true) String token);

    @PostMapping(value = "/insert")
    Usuario save(@RequestBody Usuario usuario, @RequestHeader(value = "Authorization", required = true) String token);

    @GetMapping(value = "/usuario-por-curso")
    List<Usuario> bucarPorId(@RequestParam Iterable<Long> ids, @RequestHeader(value = "Authorization", required = true) String token);
}
