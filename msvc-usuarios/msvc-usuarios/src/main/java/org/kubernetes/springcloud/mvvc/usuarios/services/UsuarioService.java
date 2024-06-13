package org.kubernetes.springcloud.mvvc.usuarios.services;

import org.kubernetes.springcloud.mvvc.usuarios.models.entity.Ususario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Ususario> listar();
    Optional<Ususario> buscarPorId(Long id);
    void eliminar(Long id);
    Ususario guardar(Ususario ususario);
    Optional<Ususario> buscarPorEmail(String email);
    boolean existe(String email);

    List<Ususario>listarPorId(Iterable<Long> ids);

}
