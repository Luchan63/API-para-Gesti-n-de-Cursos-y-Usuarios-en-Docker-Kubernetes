package org.kubernetes.springcloud.mvvc.controller;

import feign.FeignException;
import org.kubernetes.springcloud.mvvc.modals.dto.Usuario;
import org.kubernetes.springcloud.mvvc.modals.entity.Curso;
import org.kubernetes.springcloud.mvvc.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @GetMapping(value = "/list")
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Curso> detalle(@PathVariable Long id,  @RequestHeader(value = "Authorization", required = true) String token) {
        Optional<Curso> cursos = cursoService.porIdConUsuario(id, token); //cursoService.buscarPorId(id);
        if (cursos.isPresent()) {
            return ResponseEntity.ok(cursos.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<?> guardar(@Valid @RequestBody Curso curso, BindingResult result) {
       if (result.hasErrors()) {
           return validar(result);
       }

        return ResponseEntity.ok(cursoService.guardar(curso));
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors())
        {
            return validar(result);
        }

        Optional<Curso> curso1 = cursoService.buscarPorId(id);
        if (curso1.isPresent()) {
            Curso cursoDb = curso1.get();
            cursoDb.setNombre(curso.getNombre());
           return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(cursoDb));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.buscarPorId(id);
        if (curso.isPresent()) {
            cursoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@PathVariable Long cursoId, @RequestBody Usuario usuario , @RequestHeader(value = "Authorization", required = true) String token) {
        Optional<Usuario> usuarios;
        try {
            usuarios = cursoService.asignarUsuario(usuario, cursoId, token);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje",
                            "no existe el usuario por el id " +
                                    "o error en la comunicacion: " + e.getMessage()));
        }

        if (usuarios.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarios.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId, @RequestHeader(value = "Authorization", required = true) String token) {
        Optional<Usuario> usuarios;
        try {
            usuarios = cursoService.crearUsuario(usuario, cursoId,token);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje",
                            "no se pudo crear el usuario " +
                                    "o error en la comunicacion: " + e.getMessage()));
        }

        if (usuarios.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarios.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId, @RequestHeader(value = "Authorization", required = true) String token) {
        Optional<Usuario> usuarios;
        try {
            usuarios = cursoService.eliminarUsuario(usuario, cursoId, token);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje",
                            "no existe el usuario por " +
                                    "el id o error en la comunicacion: " + e.getMessage()));
        }

        if (usuarios.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(usuarios.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/eliminar-curso-usuario/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id) {
        cursoService.eliminarCursoPorId(id);
        return ResponseEntity.noContent().build();
    }



    private ResponseEntity<Map<String,String>> validar(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        {
            result.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "El campo " +err.getField() + " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
    }
}
