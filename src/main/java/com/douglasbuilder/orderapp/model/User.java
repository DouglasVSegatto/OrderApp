package com.douglasbuilder.orderapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String firstName;
    @Column
    private String lastName;

    @Column(unique = true)
    private String email;

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
