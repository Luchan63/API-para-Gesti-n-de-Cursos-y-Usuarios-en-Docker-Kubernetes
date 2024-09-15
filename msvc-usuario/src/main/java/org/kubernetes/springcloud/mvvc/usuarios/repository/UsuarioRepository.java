package org.kubernetes.springcloud.mvvc.usuarios.repository;

import org.kubernetes.springcloud.mvvc.usuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Long> {

    Optional <Usuario> findByEmail(String email);

    @Query("SELECT u from Usuario u where u.email= ?1")
    Optional<Usuario> porEmail(String email);

    boolean existsByEmail(String email);
}
