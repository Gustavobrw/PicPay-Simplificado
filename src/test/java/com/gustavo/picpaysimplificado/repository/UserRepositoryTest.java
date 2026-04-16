package com.gustavo.picpaysimplificado.repository;

import com.gustavo.picpaysimplificado.DTO.UserDTO;
import com.gustavo.picpaysimplificado.entity.User.User;
import com.gustavo.picpaysimplificado.entity.User.UserType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Deve encontrar um usuário pelo documento")
    void findUserByDocumentCase1() {
        Long document = 123456789L;
        UserDTO data = new UserDTO("Gustavo", document, new BigDecimal(10), "teste@email.com", "12345", UserType.USUARIO);
        createUser(data);

        Optional<User> result = userRepository.findUserByDocument(document);

        assertThat(result.isPresent()).isTrue();

    }

    @Test
    @DisplayName("Não deve encontrar um usuário pelo documento quando o documento não existir")
    void findUserByDocumentCase2() {
        Long document = 123456789L;

        Optional<User> result = userRepository.findUserByDocument(document);

        assertThat(result.isEmpty()).isTrue();

    }

    private User createUser(UserDTO data) {
        User newUser = new User(data);
        entityManager.persist(newUser);
        return newUser;
    }
}