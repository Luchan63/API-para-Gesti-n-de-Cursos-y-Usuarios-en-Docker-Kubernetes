package org.kubernetes.springcloud.mvvc.usuarios.services;

import org.kubernetes.springcloud.mvvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();
    Optional<Usuario> buscarPorId(Long id);
    void eliminar(Long id);
    Usuario guardar(Usuario usuario);
    Optional <Usuario> buscarPorEmail(String email);
    boolean existe(String email);

    List<Usuario>listarPorId(Iterable<Long> ids);

}
