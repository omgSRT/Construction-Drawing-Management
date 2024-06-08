package com.GSU24SE43.ConstructionDrawingManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

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
    Set<String> roleName;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    Staff staff;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Project> projectList;
}
