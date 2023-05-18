package es.uma.informatica.sii.converter;

        import es.uma.informatica.sii.DTO.UsuarioDTO;
        import es.uma.informatica.sii.entities.Usuario;
        import org.springframework.stereotype.Component;

@Component
public class DTOToUsuario {

    public static Usuario toUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setDni(dto.getDni());
        usuario.setEmail(dto.getEmail());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido1(dto.getApellido1());
        usuario.setApellido2(dto.getApellido2());
        usuario.setTelefono(dto.getTelefono());
        usuario.setRoles(dto.getRoles());
        usuario.setPassword(dto.getPassword());
        usuario.setUsername(dto.getUsername());
        return usuario;
    }

}
