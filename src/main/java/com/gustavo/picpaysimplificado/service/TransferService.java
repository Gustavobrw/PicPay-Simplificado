package com.gustavo.picpaysimplificado.service;

import com.gustavo.picpaysimplificado.DTO.TransferDTO;
import com.gustavo.picpaysimplificado.entity.Transfer.Transfer;
import com.gustavo.picpaysimplificado.entity.User.User;
import com.gustavo.picpaysimplificado.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final TransferRepository repository;
    private final UserService userService;
    private final NotificationService notificationService;
    private final AthorizationService athorizationService;

    public Transfer createTransfer(TransferDTO transferDTO) throws Exception {
        User sender = userService.findUserById(transferDTO.senderId());
        User receiver = userService.findUserById(transferDTO.receiverId());

        userService.validateTransfer(sender, transferDTO.value());

        boolean isAuthorized = athorizationService.authorizeTransfer(sender, transferDTO.value());
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

}
