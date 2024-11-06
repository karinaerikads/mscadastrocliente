package io.github.mscadastrocliente.mscadastrocliente.infra.repository;

import io.github.mscadastrocliente.mscadastrocliente.domain.Cliente;
import io.github.mscadastrocliente.mscadastrocliente.domain.Endereco;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO cliente (nome, email, endereco_id, criado_por) VALUES (:#{#cliente.nome}, :#{#cliente.email}, :enderecoId, :criadoPor)", nativeQuery = true)
    void salvarCliente(@Param("cliente") Cliente cliente, @Param("enderecoId") Long enderecoId, @Param("criadoPor") String criadoPor);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO endereco (rua, cidade, estado) VALUES (:#{#endereco.rua}, :#{#endereco.cidade}, :#{#endereco.estado})", nativeQuery = true)
    void salvarEndereco(@Param("endereco") Endereco endereco);

    @Query(value = "SELECT id FROM endereco WHERE rua = :#{#endereco.rua} AND cidade = :#{#endereco.cidade} AND estado = :#{#endereco.estado} ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Long findEnderecoId(@Param("endereco") Endereco endereco);

    @Query(value = "SELECT * FROM cliente ORDER BY LOWER(nome) ASC", nativeQuery = true)
    List<Cliente> findAllClientesOrdenadosPorNome();

    @Query(value = "SELECT * FROM cliente c JOIN endereco e ON c.endereco_id = e.id WHERE estado = :estado", nativeQuery = true)
    List<Cliente> buscarClientePorEstado(@Param("estado") String estado);

}
