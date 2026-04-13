package com.gustavo.picpaysimplificado.controller;

import com.gustavo.picpaysimplificado.DTO.TransferDTO;
import com.gustavo.picpaysimplificado.entity.Transfer.Transfer;
import com.gustavo.picpaysimplificado.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<Transfer> createTransfer(@RequestBody TransferDTO transfer) throws Exception {
        Transfer newTransfer = transferService.createTransfer(transfer);
        return ResponseEntity.ok(newTransfer);
    }
}
