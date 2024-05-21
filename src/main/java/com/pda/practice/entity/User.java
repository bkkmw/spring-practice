package com.pda.practice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class User {

    @Id
    private int id;
    private String userId;
    private String password;
    private String name;
    private String email;
    private String contact;
}
