package org.kubernetes.springcloud.mvvc.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "http://msvc-cursos:8002")
public interface CursoFeingClient {

    @DeleteMapping(value = "/eliminar-curso-usuario/{id}")
    void eliminarCursoUsuarioPorId( @PathVariable Long id);
}
