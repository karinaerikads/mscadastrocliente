package io.github.mscadastrocliente.mscadastrocliente.controller;

import io.github.mscadastrocliente.mscadastrocliente.dto.AuthenticationDTO;
import io.github.mscadastrocliente.mscadastrocliente.dto.LoginResponseDTO;
import io.github.mscadastrocliente.mscadastrocliente.dto.RegisterDTO;
import io.github.mscadastrocliente.mscadastrocliente.infra.repository.UsuarioRepository;
import io.github.mscadastrocliente.mscadastrocliente.infra.security.TokenService;
import io.github.mscadastrocliente.mscadastrocliente.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario novoUsuario = new Usuario(data.login(), encryptedPassword, data.role());

        this.repository.salvarUsuario(novoUsuario);

        return ResponseEntity.ok().build();
    }

}
