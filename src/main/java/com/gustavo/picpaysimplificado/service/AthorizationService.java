package com.gustavo.picpaysimplificado.service;

import com.gustavo.picpaysimplificado.entity.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AthorizationService {
    private final RestTemplate restTemplate;

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
