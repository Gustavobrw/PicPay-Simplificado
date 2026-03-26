package com.gustavo.picpaysimplificado.DTO;

public record UserDTO(
        String nome,
        String email,
        String senha,
        Long document
) {
}
