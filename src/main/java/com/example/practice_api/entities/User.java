package com.example.practice_api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "address", length = 255, nullable = true)
    private String address;

    @Column(name = "phoneNumber", length = 255, nullable = true)
    private String phoneNumber;

    @Column(name = "gender", length = 255, nullable = true)
    private String gender;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 255, nullable = true)
    private String password;

    @Column(name = "age", length = 255, nullable = true)
    private Integer age;

    @Column(name = "status", length = 255, nullable = true)
    private String status;

    @Column(name = "role", length = 255, nullable = true)
    private String role;
}
