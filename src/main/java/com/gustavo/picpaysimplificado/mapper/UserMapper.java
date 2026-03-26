package com.gustavo.picpaysimplificado.mapper;

import com.gustavo.picpaysimplificado.DTO.UserDTO;
import com.gustavo.picpaysimplificado.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserMapper {

    public User toEntity(UserDTO userDTO) {
       User user = new User();
         user.setNome(userDTO.nome());
            user.setEmail(userDTO.email());
            user.setSenha(userDTO.senha());
            user.setDocument(userDTO.document());
            user.setSaldo(BigDecimal.ZERO); // Inicializa o saldo com zero
            return user;
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getNome(),
                user.getEmail(),
                user.getSenha(),
                user.getDocument()
        );
    }
}
