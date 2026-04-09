package com.gustavo.picpaysimplificado.service;

import com.gustavo.picpaysimplificado.DTO.UserDTO;
import com.gustavo.picpaysimplificado.entity.User.User;
import com.gustavo.picpaysimplificado.entity.User.UserType;
import com.gustavo.picpaysimplificado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void validateTransfer(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.LOJISTA) {
            throw new Exception("Usuário do tipo lojista não pode realizar transferências");
        }

        if (sender.getSaldo().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente para realizar a transferência");
        }
    }

    public User findUserById(Long id) throws Exception {
        return repository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public void saveUser(User user) {
        repository.save(user);
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }


}
