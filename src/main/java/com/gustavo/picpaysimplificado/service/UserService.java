package com.gustavo.picpaysimplificado.service;

import com.gustavo.picpaysimplificado.DTO.UserDTO;
import com.gustavo.picpaysimplificado.entity.User.User;
import com.gustavo.picpaysimplificado.entity.User.UserType;
import com.gustavo.picpaysimplificado.mapper.UserMapper;
import com.gustavo.picpaysimplificado.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;



}
