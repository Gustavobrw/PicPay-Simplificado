package com.gustavo.picpaysimplificado.DTO;

import com.gustavo.picpaysimplificado.entity.User.UserType;

public record UserDTO(
        String nome,
        String email,
        String senha,
        Long document,
        UserType userType
) {
}
