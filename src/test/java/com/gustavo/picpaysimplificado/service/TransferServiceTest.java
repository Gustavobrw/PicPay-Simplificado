package com.gustavo.picpaysimplificado.service;

import com.gustavo.picpaysimplificado.DTO.TransferDTO;
import com.gustavo.picpaysimplificado.entity.User.User;
import com.gustavo.picpaysimplificado.entity.User.UserType;
import com.gustavo.picpaysimplificado.repository.TransferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    @Mock
    private  TransferRepository repository;
    @Mock
    private  UserService userService;
    @Mock
    private  NotificationService notificationService;
    @Mock
    private  AthorizationService athorizationService;

    @Autowired
    @InjectMocks
    private TransferService transferService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve criar uma transferência com sucesso")
    void createTransferCase1() throws Exception {
        User sender = new User(1L, "Maria Souza",12345678900L, "maria@email.com","12345",new BigDecimal(10), UserType.USUARIO,null,null);
        User receiver = new User(2L, "Joao jao",12345678902L, "joao@email.com","12345",new BigDecimal(10), UserType.USUARIO,null, null);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(athorizationService.authorizeTransfer(any(), any())).thenReturn(true);

        TransferDTO request = new TransferDTO(new BigDecimal(10) , 1L, 2L);
        transferService.createTransfer(request);

        verify(repository, times(1)).save(any());

        sender.setSaldo(new BigDecimal(0));
        verify(userService, times(1)).saveUser(sender);

        receiver.setSaldo(new BigDecimal(20));
        verify(userService, times(1)).saveUser(receiver);

        verify(notificationService,times(1)).sendNotification(sender, "Transação realizada com sucesso!");
        verify(notificationService,times(1)).sendNotification(receiver, "Você recebeu uma transferência!");
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando a transferência não for autorizada")
    void createTransferCase2() throws Exception {
        User sender = new User(1L, "Maria Souza",12345678900L, "maria@email.com","12345",new BigDecimal(10), UserType.USUARIO,null,null);
        User receiver = new User(2L, "Joao jao",12345678902L, "joao@email.com","12345",new BigDecimal(10), UserType.USUARIO,null, null);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(athorizationService.authorizeTransfer(any(), any())).thenReturn(false);

        Exception thrown = Assertions.assertThrows(Exception.class,() -> {
            TransferDTO request = new TransferDTO(new BigDecimal(10) , 1L, 2L);
            transferService.createTransfer(request);
        });

        Assertions.assertEquals("Transferência não autorizada", thrown.getMessage());
    }
}