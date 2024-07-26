package org.kubernetes.springcloud.mvvc.usuarios.controller;

import org.kubernetes.springcloud.mvvc.usuarios.models.entity.Ususario;
import org.kubernetes.springcloud.mvvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UsuarioController
{
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private Environment env;

    @GetMapping(value = "/list")
    public ResponseEntity<?> listar()
    {
        Map<String, Object> body = new HashMap<>();
        body.put("usuarios", usuarioService.listar());
        body.put("pod_info", env.getProperty("MY_POD_NAME") + ": " + env.getProperty("MY_POD_IP"));
        body.put("texto", env.getProperty("config.texto"));  // Asegúrate de que esta línea esté correcta
//        return Collections.singletonMap("user", usuarioService.listar());
        return ResponseEntity.ok(body);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id)
    {
        Optional<Ususario> optionalUsusario = usuarioService.buscarPorId(id);
        if (optionalUsusario.isPresent())
        {
            return ResponseEntity.ok().body(optionalUsusario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<?> crear(@Valid @RequestBody Ususario ususario, BindingResult result)
    {

        if (result.hasErrors())
        {
            return validar(result);
        }
        if (!ususario.getEmail().isEmpty() && usuarioService.buscarPorEmail(ususario.getEmail()).isPresent())
        {
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("error", "Email ya existe"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(ususario));
    }


    @PutMapping(value = "update/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Ususario ususario, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors())
        {
            return validar(result);
        }

        Optional<Ususario> optionalUsusario = usuarioService.buscarPorId(id);
        if (optionalUsusario.isPresent()) {
            Ususario ususarioDb = optionalUsusario.get();
            if (!ususario.getEmail().isEmpty() && !ususario.getEmail().equalsIgnoreCase(ususarioDb.getEmail()) && usuarioService.buscarPorEmail(ususario.getEmail()).isPresent())
            {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("error", "Email ya existe"));
            }
            ususarioDb.setNombre(ususario.getNombre());
            ususarioDb.setEmail(ususario.getEmail());
            ususarioDb.setPassword(ususario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(ususarioDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id)
    {
        Optional<Ususario> optionalUsusario = usuarioService.buscarPorId(id);
        if (optionalUsusario.isPresent()) {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/usuario-por-curso")
    public ResponseEntity<?> bucarPorId(@RequestParam List<Long> ids)
    {
      return ResponseEntity.ok(usuarioService.listarPorId(ids));
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        {
            result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " +err.getField() + " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
    }
}
