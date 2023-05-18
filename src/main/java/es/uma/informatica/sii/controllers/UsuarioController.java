package es.uma.informatica.sii.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import es.uma.informatica.sii.converter.DTOToUsuario;
import es.uma.informatica.sii.converter.UsuarioToDTO;
import es.uma.informatica.sii.service.ConflictoException;
import es.uma.informatica.sii.service.NoEncontradoException;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import es.uma.informatica.sii.DTO.UsuarioDTO;
import es.uma.informatica.sii.entities.Usuario;
import es.uma.informatica.sii.service.UsuarioDBService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDBService usuarioDBService;

    @Autowired
    private DTOToUsuario dtoConverter;

    @Autowired
    private UsuarioToDTO usuarioConverter;

    //Método encargado de devolver toda la lista de usuarios
    @GetMapping("")
    @CrossOrigin(origins = "http://localhost:4200/")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioDBService.getAllUsuarios();
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        for (Usuario u : usuarios) {
            usuariosDTO.add(usuarioConverter.toDTO(u)); //añade a la lista los DTO
        }
        return ResponseEntity.ok(usuariosDTO); //Respuesta 200 OK con la lista
    }

    //Método encargado de crear un nuevo usuario
    @PostMapping("")
    @CrossOrigin(origins = "http://localhost:4200/")
    @RolesAllowed({"VICERRECTORADO", "RESPONSABLESEDE"}) //Roles autorizados
    public ResponseEntity<?> addUsuario(@RequestBody UsuarioDTO nuevoUsuario, UriComponentsBuilder builder) {
        usuarioDBService.exitsByEmail(nuevoUsuario.getEmail());
        Usuario usuario = dtoConverter.toUsuario(nuevoUsuario);
        usuarioDBService.addUsuario(usuario);
        URI uri = builder.path("/usuarios")
                .path(String.format("/%d", usuario.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    //Método encargado de devolver todos los usuarios
    @GetMapping("/{idUsuario}")
    @CrossOrigin(origins = "http://localhost:4200/")
    @RolesAllowed({"VICERRECTORADO"})
    public ResponseEntity<UsuarioDTO> getUserById(@PathVariable Long idUsuario) {
        Usuario usuarioById = usuarioDBService.getUsuarioById(idUsuario);
        UsuarioDTO userDto = usuarioConverter.toDTO(usuarioById);
        return ResponseEntity.ok(userDto);

    }

    @PutMapping("/{idUsuario}")
    @CrossOrigin(origins = "http://localhost:4200/")
    @RolesAllowed({"VICERRECTORADO"})
    public ResponseEntity<?> updateUsuario(@PathVariable Long idUsuario, @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuarioActulizable = dtoConverter.toUsuario(usuarioDTO);
        usuarioDBService.updateUsuario(usuarioActulizable);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idUsuario}")
    @CrossOrigin(origins = "http://localhost:4200/")
    @RolesAllowed({"VICERRECTORADO"})
    public ResponseEntity<?> deleteUsuario(@PathVariable Long idUsuario) {
        usuarioDBService.deleteUsuario(idUsuario);
        return ResponseEntity.ok().build();
    }

    //Respuesta 404 para cuando se lance NoEncontradoException
    @ExceptionHandler(NoEncontradoException.class)
    @CrossOrigin(origins = "http://localhost:4200/")
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public String noEncontrado(NoEncontradoException e) {
        return e.getMessage();
    }

    //Respuesta 409 para cuando se lance ConflictoException
    @ExceptionHandler(ConflictoException.class)
    @CrossOrigin(origins = "http://localhost:4200/")
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public String conflicto(ConflictoException e) {
        return e.getMessage();
    }
}
