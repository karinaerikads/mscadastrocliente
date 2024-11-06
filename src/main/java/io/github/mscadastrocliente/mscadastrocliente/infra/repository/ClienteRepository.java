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
    @Query(value = "INSERT INTO cliente (nome, email, endereco_id, criado_por, data_cadastro) VALUES (:#{#cliente.nome}, :#{#cliente.email}, :enderecoId, :#{#cliente.criadoPor}, :#{#cliente.dataCadastro})", nativeQuery = true)
    void salvarCliente(@Param("cliente") Cliente cliente, @Param("enderecoId") Long enderecoId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO endereco (rua, cidade, estado) VALUES (:#{#endereco.rua}, :#{#endereco.cidade}, :#{#endereco.estado})", nativeQuery = true)
    void salvarEndereco(@Param("endereco") Endereco endereco);

    @Query(value = "SELECT id FROM endereco WHERE rua = :#{#endereco.rua} AND cidade = :#{#endereco.cidade} AND estado = :#{#endereco.estado} ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Long buscarEnderecoId(@Param("endereco") Endereco endereco);

    @Query(value = "SELECT * FROM cliente ORDER BY LOWER(nome) ASC", nativeQuery = true)
    List<Cliente> findAllClientesOrdenadosPorNome();

    @Query(value = "SELECT *FROM cliente WHERE id = :clienteId", nativeQuery = true)
    Cliente buscarCliente(@Param("clienteId") Long clienteId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cliente WHERE id = :clienteId", nativeQuery = true)
    int excluirCliente(@Param("clienteId") Long clienteId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM endereco WHERE id = :enderecoId", nativeQuery = true)
    int excluirEndereco(@Param("enderecoId") Long enderecoId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE endereco SET rua = :#{#endereco.rua}, cidade = :#{#endereco.cidade}, estado = :#{#endereco.estado} WHERE id = :#{#endereco.id}", nativeQuery = true)
    void atualizarEndereco(Endereco endereco);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cliente SET nome = :#{#cliente.nome}, email = :#{#cliente.email}, modificado_por :#{#cliente.modificadoPor}, data_alteracao  :#{#cliente.dataAlteracao} WHERE id = :#{#cliente.id}", nativeQuery = true)
    void atualizarCliente(Cliente cliente);

}
