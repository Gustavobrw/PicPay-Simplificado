package com.gustavo.picpaysimplificado.service;

import com.gustavo.picpaysimplificado.DTO.NotificationDTO;
import com.gustavo.picpaysimplificado.entity.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final RestTemplate restTemplate;

    public void sendNotification(User user , String message)throws Exception{
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);
//        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest ,String.class);
//
//        if(!(notificationResponse.getStatusCode() == HttpStatus.OK)){
//            System.out.println("Falha ao enviar notificação para o usuário ");
//            throw new Exception("Falha ao enviar notificação");
//        }
        System.out.println("Notificação enviada para o usuário " + email + ": " + message);
    }
}
