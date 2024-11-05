package io.github.mscadastrocliente.mscadastrocliente.infra.repository;

import io.github.mscadastrocliente.mscadastrocliente.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO usuario (login, password, role) VALUES (:#{#usuario.login}, :#{#usuario.password}, :#{#usuario.role})", nativeQuery = true)
    void salvarUsuario(@Param("usuario") Usuario usuario);

    @Query(value = "SELECT *FROM usuario WHERE login = :login", nativeQuery = true)
    Usuario findByLogin(@Param("login") String login);

}
