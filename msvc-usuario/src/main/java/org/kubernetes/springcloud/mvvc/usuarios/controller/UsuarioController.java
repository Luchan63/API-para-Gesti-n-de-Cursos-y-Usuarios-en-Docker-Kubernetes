package org.kubernetes.springcloud.mvvc.usuarios.controller;

import org.kubernetes.springcloud.mvvc.usuarios.models.entity.Usuario;
import org.kubernetes.springcloud.mvvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
        Optional<Usuario> optionalUsusario = usuarioService.buscarPorId(id);
        if (optionalUsusario.isPresent())
        {
            return ResponseEntity.ok().body(optionalUsusario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result)
    {

        if (result.hasErrors())
        {
            return validar(result);
        }
        if (!usuario.getEmail().isEmpty() && usuarioService.buscarPorEmail(usuario.getEmail()).isPresent())
        {
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("error", "Email ya existe"));
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
    }


    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors())
        {
            return validar(result);
        }

        Optional<Usuario> optionalUsusario = usuarioService.buscarPorId(id);
        if (optionalUsusario.isPresent()) {
            Usuario usuarioDb = optionalUsusario.get();
            if (!usuario.getEmail().isEmpty() && !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) && usuarioService.buscarPorEmail(usuario.getEmail()).isPresent())
            {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("error", "Email ya existe"));
            }
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuarioDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id)
    {
        Optional<Usuario> optionalUsusario = usuarioService.buscarPorId(id);
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

    @GetMapping(value = "/authorized")
    public Map<String, Object> authorized(@RequestParam(name = "code") String code)
    {
        return Collections.singletonMap("code", code);
    }

    @GetMapping(value = "/login")
    public ResponseEntity<?> login(@RequestParam String email)
    {
        Optional<Usuario> optionalUsusario = usuarioService.buscarPorEmail(email);
        if (optionalUsusario.isPresent()) {
            return ResponseEntity.ok().body(optionalUsusario.get());
        }
        return ResponseEntity.notFound().build();
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
