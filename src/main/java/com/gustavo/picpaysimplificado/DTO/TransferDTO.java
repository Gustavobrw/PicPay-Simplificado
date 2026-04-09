package com.gustavo.picpaysimplificado.DTO;

import java.math.BigDecimal;

public record TransferDTO(
        BigDecimal value,
        Long senderId,
        Long receiverId
) {
}
