package io.github.mscadastrocliente.mscadastrocliente.service;

import io.github.mscadastrocliente.mscadastrocliente.domain.Cliente;
import io.github.mscadastrocliente.mscadastrocliente.domain.Endereco;
import io.github.mscadastrocliente.mscadastrocliente.dto.ClienteDTO;
import io.github.mscadastrocliente.mscadastrocliente.exception.ClienteNotFoundException;
import io.github.mscadastrocliente.mscadastrocliente.exception.DatabaseOperationException;
import io.github.mscadastrocliente.mscadastrocliente.exception.EnderecoNotFoundException;
import io.github.mscadastrocliente.mscadastrocliente.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Transactional
    public void salvarClienteComEndereco(Cliente cliente){
        String criadoPor = SecurityContextHolder.getContext().getAuthentication().getName();
        cliente.setCriadoPor(criadoPor);

        salvarEndereco(cliente.getEndereco());

        Long enderecoId = buscarEnderecoId(cliente.getEndereco());
        cliente.setDataCadastro(LocalDateTime.now());

        salvarCliente(cliente, enderecoId);
    }

    private void salvarEndereco(Endereco endereco) {
        try {
            clienteRepository.salvarEndereco(endereco);
        } catch (Exception e) {
            throw new DatabaseOperationException("Falha ao salvar o endereço.", e);
        }
    }

    private Long buscarEnderecoId(Endereco endereco) {
        Long enderecoId = clienteRepository.buscarEnderecoId(endereco);
        if (enderecoId == null) {
            throw new EnderecoNotFoundException("Endereço não encontrado.");
        }
        return enderecoId;
    }

    private void salvarCliente(Cliente cliente, Long enderecoId) {
        try {
            clienteRepository.salvarCliente(cliente, enderecoId);
        } catch (Exception e) {
            throw new DatabaseOperationException("Falha ao salvar o cliente.", e);
        }
    }

    public List<Cliente> listarTodosClientesOrdenadosPorNome() {
        return clienteRepository.findAllClientesOrdenadosPorNome();
    }

    public void deletarCliente(Long idCliente){
        Cliente cliente = buscarClienteComEndereco(idCliente);
        deletarClienteComEndereco(cliente);
    }

    private Cliente buscarClienteComEndereco(Long idCliente){
        Cliente cliente = clienteRepository.buscarCliente(idCliente);
        if (cliente == null)
            throw new ClienteNotFoundException("Cliente não encontrado");
        if (cliente.getEndereco() == null)
            throw new EnderecoNotFoundException("Endereco do cliente não encontrado");

        return cliente;
    }

    private void deletarClienteComEndereco(Cliente cliente){
        try {
            clienteRepository.excluirCliente(cliente.getId());
            clienteRepository.excluirEndereco(cliente.getEndereco().getId());
        } catch (Exception e){
            throw new DatabaseOperationException("Falha ao deletar cliente.", e);
        }
    }
    @Transactional
    public void atualizarClienteComEndereco(ClienteDTO clienteDTO) {


        Cliente cliente = montarObjetoCliente(clienteDTO);

        atualizarEndereco(cliente.getEndereco());

        atualizarCliente(cliente);
    }

    private void atualizarEndereco(Endereco enderecoAtualizado){

        try {
            clienteRepository.atualizarEndereco(enderecoAtualizado);
        } catch (Exception e){
            throw new DatabaseOperationException("Falha ao atualizar o endereco.", e);
        }
    }

    private void atualizarCliente(Cliente cliente){
        try {
            clienteRepository.atualizarCliente(cliente);
        } catch (Exception e){
            throw new DatabaseOperationException("Falha ao atualizar o cliente.", e);
        }

    }

    private Cliente montarObjetoCliente(ClienteDTO clienteDTO){
        Cliente clienteAtualizado = new Cliente();

        clienteAtualizado.setId(clienteDTO.getClienteId());
        clienteAtualizado.setNome(clienteDTO.getNome());
        clienteAtualizado.setEmail(clienteDTO.getEmail());

        clienteAtualizado.setEndereco(new Endereco(clienteDTO.getEnderecoId()));
        clienteAtualizado.getEndereco().setRua(clienteDTO.getRua());
        clienteAtualizado.getEndereco().setCidade(clienteDTO.getCidade());
        clienteAtualizado.getEndereco().setEstado(clienteDTO.getEstado());

        String modificadoPor = SecurityContextHolder.getContext().getAuthentication().getName();
        clienteAtualizado.setModificadoPor(modificadoPor);

        clienteAtualizado.setDataAlteracao(LocalDateTime.now());

        return clienteAtualizado;
    }

}
