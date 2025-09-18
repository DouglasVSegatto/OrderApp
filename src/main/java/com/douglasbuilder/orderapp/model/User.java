package com.douglasbuilder.orderapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;

    @Column(unique = true)
    private String email;

    public String fullName() {
        return getFirstName() + " " + getLastName();
    }

//    @OneToOne(mappedBy = "user")
//    private Cart cart;
}
