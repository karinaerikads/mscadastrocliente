package io.github.mscadastrocliente.mscadastrocliente.controller;

import io.github.mscadastrocliente.mscadastrocliente.domain.Cliente;
import io.github.mscadastrocliente.mscadastrocliente.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @PostMapping
    public String salvarCliente(@RequestBody Cliente cliente) {
        String criadoPor = SecurityContextHolder.getContext().getAuthentication().getName();
        clienteService.salvarClienteComEndereco(cliente, criadoPor);
        return "Cliente e Endereço salvos com sucesso!";
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientesOrdenadosPorNome() {
        List<Cliente> clientes = clienteService.listarTodosClientesOrdenadosPorNome();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/por-estado")
    public ResponseEntity<List<Cliente>> buscarClientesPorEstado(@RequestParam("estado") String estado) {
        List<Cliente> clientes = clienteService.buscarClientesPorEstado(estado);
        return ResponseEntity.ok(clientes);
    }
}
