package com.gustavo.picpaysimplificado.repository;

import com.gustavo.picpaysimplificado.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
