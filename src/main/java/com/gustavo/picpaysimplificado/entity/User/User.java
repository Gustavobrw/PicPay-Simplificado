package com.gustavo.picpaysimplificado.entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gustavo.picpaysimplificado.DTO.UserDTO;
import com.gustavo.picpaysimplificado.entity.Transfer.Transfer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true, nullable = false)
    private Long document;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String senha;
    private BigDecimal saldo;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private List<Transfer> sentTransfers;

    @OneToMany(mappedBy = "receiver")
    @JsonIgnore
    private List<Transfer> receivedTransfers;

    public  User(UserDTO data){
        this.nome = data.nome();
        this.document = data.document();
        this.email = data.email();
        this.senha = data.senha();
        this.saldo = data.saldo();
        this.userType = UserType.USUARIO;
    }

}
