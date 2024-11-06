package io.github.mscadastrocliente.mscadastrocliente.controller;

import io.github.mscadastrocliente.mscadastrocliente.domain.Cliente;
import io.github.mscadastrocliente.mscadastrocliente.dto.ClienteDTO;
import io.github.mscadastrocliente.mscadastrocliente.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @PostMapping
    public String salvar(@RequestBody Cliente cliente) {
        clienteService.salvarClienteComEndereco(cliente);
        return "Cliente e Endereço salvos com sucesso!";
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientesOrdenadosPorNome() {
        List<Cliente> clientes = clienteService.listarTodosClientesOrdenadosPorNome();
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("{id}")
    String atualizar(@RequestBody ClienteDTO clienteDTO){
        clienteService.atualizarClienteComEndereco(clienteDTO);
        return "Atualizado com sucesso!";
    }

    @DeleteMapping("{idCliente}")
    public String deletar(@PathVariable Long idCliente){
        clienteService.deletarCliente(idCliente);
        return "Cliente deletado com sucesso";
    }
}
