package org.kubernetes.springcloud.mvvc.usuarios.services;

import org.kubernetes.springcloud.mvvc.usuarios.client.CursoFeingClient;
import org.kubernetes.springcloud.mvvc.usuarios.models.entity.Ususario;
import org.kubernetes.springcloud.mvvc.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoFeingClient cursoFeingClient;

    @Override
    @Transactional(readOnly = true)
    public List<Ususario> listar() {
        return (List<Ususario>) usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ususario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
        cursoFeingClient.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional
    public Ususario guardar(Ususario ususario) {
        return usuarioRepository.save(ususario);
    }

    @Override
    public Optional<Ususario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public boolean existe(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ususario> listarPorId(Iterable<Long> ids) {
        return (List<Ususario>) usuarioRepository.findAllById(ids);
    }
}
