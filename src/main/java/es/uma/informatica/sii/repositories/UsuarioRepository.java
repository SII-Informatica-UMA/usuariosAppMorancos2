package es.uma.informatica.sii.repositories;

import org.springframework.data.repository.CrudRepository;
import es.uma.informatica.sii.entities.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    Usuario findByUsername(String username);
}

