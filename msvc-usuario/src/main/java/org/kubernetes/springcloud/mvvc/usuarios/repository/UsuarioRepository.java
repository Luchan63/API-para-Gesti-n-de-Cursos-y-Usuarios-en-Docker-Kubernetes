package org.kubernetes.springcloud.mvvc.usuarios.repository;

import org.kubernetes.springcloud.mvvc.usuarios.models.entity.Ususario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Ususario,Long> {

    Optional<Ususario> findByEmail(String email);

    @Query("SELECT u from Ususario u where u.email= ?1")
    Optional<Ususario> porEmail(String email);

    boolean existsByEmail(String email);
}
