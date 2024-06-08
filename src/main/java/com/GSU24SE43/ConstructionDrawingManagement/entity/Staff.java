package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
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
    String fullName;
    String email;
    String address;
    String phone;
    String certificate;
    String degree;
    boolean isSupervisor;
// chua xng

// department
    @ManyToOne
    @JoinColumn(name = "departmentId")
    Department department;
// account
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "accountId")
    Account account;
// task
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Task> tasks = new ArrayList<>();
// access
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Access> accessList;
// comment
    @ManyToOne
    @JoinColumn(name = "commentId")
    Comment comment;

}
