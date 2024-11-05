package io.github.mscadastrocliente.mscadastrocliente.dto;

import io.github.mscadastrocliente.mscadastrocliente.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
