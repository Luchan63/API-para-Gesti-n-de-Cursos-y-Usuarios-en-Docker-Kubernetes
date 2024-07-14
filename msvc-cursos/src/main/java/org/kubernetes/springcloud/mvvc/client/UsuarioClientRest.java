package org.kubernetes.springcloud.mvvc.client;

import org.kubernetes.springcloud.mvvc.modals.dto.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios")
public interface UsuarioClientRest {

    @GetMapping(value = "/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping(value = "/insert")
    Usuario save(@RequestBody Usuario usuario);

    @GetMapping(value = "/usuario-por-curso")
    List<Usuario> bucarPorId(@RequestParam Iterable<Long> ids);
}
