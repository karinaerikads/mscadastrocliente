package io.github.mscadastrocliente.mscadastrocliente.service;

import io.github.mscadastrocliente.mscadastrocliente.domain.Cliente;
import io.github.mscadastrocliente.mscadastrocliente.domain.Endereco;
import io.github.mscadastrocliente.mscadastrocliente.exception.DatabaseOperationException;
import io.github.mscadastrocliente.mscadastrocliente.exception.EnderecoNotFoundException;
import io.github.mscadastrocliente.mscadastrocliente.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Transactional
    public void salvarClienteComEndereco(Cliente cliente, String criadoPor){
        salvarEndereco(cliente.getEndereco());
        salvarCliente(cliente, buscarEnderecoId(cliente.getEndereco()), criadoPor);
    }

    private void salvarEndereco(Endereco endereco) {
        try {
            clienteRepository.salvarEndereco(endereco);
        } catch (Exception e) {
            throw new DatabaseOperationException("Falha ao salvar o endereço.", e);
        }
    }

    private Long buscarEnderecoId(Endereco endereco) {
        Long enderecoId = clienteRepository.findEnderecoId(endereco);
        if (enderecoId == null) {
            throw new EnderecoNotFoundException("Endereço não encontrado após a inserção.");
        }
        return enderecoId;
    }

    private void salvarCliente(Cliente cliente, Long enderecoId, String criadoPor) {
        try {
            clienteRepository.salvarCliente(cliente, enderecoId, criadoPor);
        } catch (Exception e) {
            throw new DatabaseOperationException("Falha ao salvar o cliente.", e);
        }
    }

    public List<Cliente> listarTodosClientesOrdenadosPorNome() {
        return clienteRepository.findAllClientesOrdenadosPorNome();
    }

}
