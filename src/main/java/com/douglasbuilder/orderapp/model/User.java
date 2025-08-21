package com.douglasbuilder.orderapp.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue
    @Column
    private Long id;
    @Column
    @NonNull
    private String firstName;
    @Column
    @NonNull
    private String lastName;
    @Column(unique = true)
    @NonNull
    private String email;

    public String fullName(){
        return getFirstName() + " " + getLastName();
    }

}
