package org.kubernetes.springcloud.mvvc.repository;

import org.kubernetes.springcloud.mvvc.modals.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {

    @Modifying
    @Query("delete from CursoUsuario curso where curso.usuarioId = ?1")
    void eliminarCursoUsuarioPorId(Long id);
}
