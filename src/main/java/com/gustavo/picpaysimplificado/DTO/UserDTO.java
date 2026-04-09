package com.gustavo.picpaysimplificado.DTO;

import com.gustavo.picpaysimplificado.entity.User.UserType;

import java.math.BigDecimal;

public record UserDTO(
        String nome,
        Long document,
        BigDecimal saldo,
        String email,
        String senha,
        UserType userType
) {
}
