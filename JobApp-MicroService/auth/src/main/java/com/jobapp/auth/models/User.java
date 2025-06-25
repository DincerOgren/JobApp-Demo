package com.jobapp.auth.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String password;
    String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();



    public User(@NotBlank(message = "Name is required") @Size(min = 2, message = "Name should be at least 2 character") String name,
                String encryptedPass, @NotBlank(message = "Email is required") @Email(message = "Must be a valid email") String email,
                Set<Role> roles) {

        this.name     = name;
        this.password= encryptedPass;
        this.email    = email;
        this.roles    = roles;
    }
}
