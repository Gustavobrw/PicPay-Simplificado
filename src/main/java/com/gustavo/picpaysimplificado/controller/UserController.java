package com.gustavo.picpaysimplificado.controller;

import com.gustavo.picpaysimplificado.DTO.UserDTO;
import com.gustavo.picpaysimplificado.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Data
public class UserController {

    private final UserService service;

    @PostMapping("/create")
    public String createUser(@RequestBody UserDTO request) {
        try {
            service.createUser(request);
            return "Usuário criado com sucesso!";
        } catch (Exception e) {
            return "Erro ao criar usuário: " + e.getMessage();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> listUser(){
        List<UserDTO> user = service.listUser();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<UserDTO> listUserId(@PathVariable Long id){
        UserDTO user = service.listUserId(id);
        if(user != null){
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDTO request) {
        if(service.listUserId(id) != null){
            service.updateUser(id,request);
            return ResponseEntity.ok("Usuário atualizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        if(service.listUserId(id) != null){
            service.deleteUser(id);
            return "Usuário deletado com sucesso!";
        }
        return "Usuário não encontrado.";
    }
}
