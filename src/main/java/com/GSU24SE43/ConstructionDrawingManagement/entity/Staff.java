package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID staffId;
    String fullname;
    String email;
    String address;
    String phone;
    String certificate;
    String degree;
    boolean isAdmin;

    @ManyToOne
    @JoinColumn(name = "supervisorId")
    Staff supervisor;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    Department department;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "accountId")
    Account account;
}
