package es.uma.informatica.sii.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uma.informatica.sii.converter.DTOToUsuario;
import es.uma.informatica.sii.converter.UsuarioToDTO;
import es.uma.informatica.sii.entities.Usuario;
import es.uma.informatica.sii.repositories.UsuarioRepository;

@Service
public class UsuarioDBService {

    private static final int LONGITUD_CONTRASENA = 12;
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DTOToUsuario dtoConverter;

    @Autowired
    private UsuarioToDTO usuarioConverter;

    //Devuelve todos los usuarios del REPOSITORIO
    public List<Usuario> getAllUsuarios() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    //Devuelve el usuario a partir de su id. Lanza excepcion sino lo encuentra
    public Usuario getUsuarioById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new NoEncontradoException("Usuario con " + id + " no ha sido encontrado.");
        }
    }

    //Añade un nuevo usuario al repositorio
    public Usuario addUsuario(Usuario u) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(u.getEmail()); //Buscamos si hay usuarios con mismo EMAIL
        if (usuario.isPresent()) { // Si hay un usuario lanzamos excepcion
            throw new ConflictoException("El usuario con " + usuario.get().getEmail() + "ya esta registrado.");
        } else { //Sino hay usuario con mismo EMAIL
            return usuarioRepository.save(u);
        }
    }

    public Usuario updateUsuario(Usuario usuarioActualizable) {
        if (!usuarioRepository.existsById(usuarioActualizable.getId())) {
            throw new NoEncontradoException("Usuario con " + usuarioActualizable.getId() + " no ha sido encontrado.");
        } else {
            return usuarioRepository.save(usuarioActualizable);
        }
    }

    public void deleteUsuario(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (!optionalUsuario.isPresent()) {
            throw new NoEncontradoException("Usuario con " + id + " no ha sido encontrado.");
        } else {
            Usuario usuario = optionalUsuario.get();
            usuarioRepository.delete(usuario);
        }
    }

    public boolean exitsByEmail(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new ConflictoException("Usuario con " + email + " no ha sido encontrado.");
        } else {
            return false;
        }
    }
}




