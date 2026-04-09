package com.gustavo.picpaysimplificado.repository;

import com.gustavo.picpaysimplificado.entity.Transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer,Long> {
}
