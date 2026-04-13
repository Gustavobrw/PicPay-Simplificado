package com.gustavo.picpaysimplificado.service;

import com.gustavo.picpaysimplificado.DTO.TransferDTO;
import com.gustavo.picpaysimplificado.entity.Transfer.Transfer;
import com.gustavo.picpaysimplificado.entity.User.User;
import com.gustavo.picpaysimplificado.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final TransferRepository repository;
    private final UserService userService;
    private final RestTemplate restTemplate;
    private final NotificationService notificationService;

    public Transfer createTransfer(TransferDTO transferDTO) throws Exception {
        User sender = userService.findUserById(transferDTO.senderId());
        User receiver = userService.findUserById(transferDTO.receiverId());

        userService.validateTransfer(sender, transferDTO.value());

        boolean isAuthorized = authorizeTransfer(sender, transferDTO.value());
        if (!isAuthorized) {
            throw new Exception("Transferência não autorizada");
        }

        Transfer transfer = new Transfer();
        transfer.setAmount(transferDTO.value());
        transfer.setSender(sender);
        transfer.setReceiver(receiver);
        transfer.setDate(LocalDateTime.now());

        sender.setSaldo(sender.getSaldo().subtract(transferDTO.value()));
        receiver.setSaldo(receiver.getSaldo().add(transferDTO.value()));

        repository.save(transfer);
        userService.saveUser(sender);
        userService.saveUser(receiver);

        notificationService.sendNotification(sender, "Transação realizada com sucesso!");
        notificationService.sendNotification(receiver, "Você recebeu uma transferência!");

        return transfer;

    }

    public Boolean authorizeTransfer(User sender, BigDecimal value) {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(
                    "https://util.devi.tools/api/v2/authorize",
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map body = response.getBody();
                Map data = (Map) body.get("data");

                return data != null && Boolean.TRUE.equals(data.get("authorization"));
            }

        } catch (Exception e) {
            return false;
        }

        return false;
    }

}
