package org.kubernetes.springcloud.mvvc.services;

import org.kubernetes.springcloud.mvvc.modals.dto.Usuario;
import org.kubernetes.springcloud.mvvc.modals.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listar();
    Optional<Curso> buscarPorId(Long id);
    Optional<Curso> porIdConUsuario(Long id,String token);
    Curso guardar(Curso curso);
    void eliminar(Long id);
    void eliminarCursoPorId(Long id);

    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId, String token);
    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId, String token);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId, String token);
}
