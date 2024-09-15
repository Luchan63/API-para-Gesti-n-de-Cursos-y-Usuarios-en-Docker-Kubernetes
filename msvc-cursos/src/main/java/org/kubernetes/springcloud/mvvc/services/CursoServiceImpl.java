package org.kubernetes.springcloud.mvvc.services;

import org.kubernetes.springcloud.mvvc.client.UsuarioClientRest;
import org.kubernetes.springcloud.mvvc.modals.dto.Usuario;
import org.kubernetes.springcloud.mvvc.modals.entity.Curso;
import org.kubernetes.springcloud.mvvc.modals.entity.CursoUsuario;
import org.kubernetes.springcloud.mvvc.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClientRest clientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porIdConUsuario(Long id, String token) {
        Optional<Curso> curso = cursoRepository.findById(id);
        if (curso.isPresent()) {
            Curso cursoUsuario = curso.get();

            if (!cursoUsuario.getCursoUsuarios().isEmpty())
            {
                List<Long> ids = cursoUsuario.getCursoUsuarios().stream()
                        .map(CursoUsuario::getUsuarioId).toList();

                List<Usuario> usuarios = clientRest.bucarPorId(ids,token);
                cursoUsuario.setUsuarios(usuarios);

            }
                return Optional.of(cursoUsuario);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarCursoPorId(Long id) {
        cursoRepository.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId, String token)
    {
        Optional<Curso> cursos = cursoRepository.findById(cursoId);

        if(cursos.isPresent()) {
            Usuario usuarioMsvc = clientRest.detalle(usuario.getId(), token);

            Curso curso = cursos.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.agregarCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);

        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId, String token)
    {
        Optional<Curso> cursos = cursoRepository.findById(cursoId);
        if(cursos.isPresent()) {
            Usuario usuarioNuevoMsvc = clientRest.save(usuario, token);

            Curso curso = cursos.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

            curso.agregarCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioNuevoMsvc);

        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId, String token)
    {
        Optional<Curso> cursos = cursoRepository.findById(cursoId);
        if(cursos.isPresent()) {
            Usuario usuarioElminarMsvc = clientRest.detalle(usuario.getId(), token);

            Curso curso = cursos.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioElminarMsvc.getId());

            curso.eliminarCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioElminarMsvc);

        }
        return Optional.empty();
    }
}
