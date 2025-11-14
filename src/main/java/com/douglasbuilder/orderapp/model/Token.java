package com.douglasbuilder.orderapp.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column private String token;
    @Column private Instant expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //TODO LoggedOut attribute?

}
