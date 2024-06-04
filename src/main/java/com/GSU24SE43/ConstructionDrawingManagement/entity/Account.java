package com.GSU24SE43.ConstructionDrawingManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID accountId;
    String username;
    String password;
    Date createdDate;
    String accountStatus;
    @ElementCollection
    Set<String> roles;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    Staff staff;
}
