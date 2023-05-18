package es.uma.informatica.sii.converter;

        import es.uma.informatica.sii.DTO.UsuarioDTO;
        import es.uma.informatica.sii.entities.Usuario;
        import org.springframework.stereotype.Component;

@Component
public class UsuarioToDTO {

    public UsuarioDTO toDTO(Usuario usuario) { //SIN STATIC
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setDni(usuario.getDni());
        dto.setEmail(usuario.getEmail());
        dto.setNombre(usuario.getNombre());
        dto.setApellido1(usuario.getApellido1());
        dto.setApellido2(usuario.getApellido2());
        dto.setTelefono(usuario.getTelefono());
        dto.setRoles(usuario.getRoles());
        dto.setPassword(usuario.getPassword());
        dto.setUsername(usuario.getUsername());
        return dto;
    }

}

