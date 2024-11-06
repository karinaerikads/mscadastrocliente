package io.github.mscadastrocliente.mscadastrocliente.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long clienteId;
    private String nome;
    private String email;
    private Long enderecoId;
    private String rua;
    private String cidade;
    private String estado;

    public ClienteDTO(Long clienteId, String nome, String email,
                                 Long enderecoId, String rua, String cidade, String estado) {
        this.clienteId = clienteId;
        this.nome = nome;
        this.email = email;
        this.enderecoId = enderecoId;
        this.rua = rua;
        this.cidade = cidade;
        this.estado = estado;
    }
}
