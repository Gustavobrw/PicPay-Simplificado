package com.gustavo.picpaysimplificado.service;

import com.gustavo.picpaysimplificado.DTO.UserDTO;
import com.gustavo.picpaysimplificado.entity.User;
import com.gustavo.picpaysimplificado.mapper.UserMapper;
import com.gustavo.picpaysimplificado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository repository;

    public String createUser(UserDTO request) {
        User user = userMapper.toEntity(request);
        repository.save(user);

        return "Usuário criado com sucesso!";
    }

    public List<UserDTO> listUser(){
        List<User> users = repository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public UserDTO listUserId(Long id){
        return repository.findById(id)
                .map(userMapper::toDTO)
                .orElse(null);
    }

    public UserDTO updateUser(Long id, UserDTO request){
        User userAtt = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        userAtt.setNome(request.nome());
        userAtt.setSenha(request.senha());
        if(userAtt.getDocument() != request.document()){
            userAtt.setDocument(request.document());
        }
        if(userAtt.getEmail() != request.email()) {
            userAtt.setEmail(request.email());
        }
        return userMapper.toDTO(repository.save(userAtt));
    }

    public void deleteUser(Long id){
        repository.deleteById(id);
    }

}
