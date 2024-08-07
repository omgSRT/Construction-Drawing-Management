package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    // department
    @ManyToOne
    @JoinColumn(name = "department_Id")
    @JsonIgnoreProperties(value = {"staffList"}, allowSetters = true)
    Department department;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"staff"}, allowSetters = true)
    @JoinColumn(name = "account_Id")
    Account account;
    // comment
    @JsonIgnoreProperties(value = {"staff"}, allowSetters = true)
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments;

    @JsonIgnoreProperties(value = {"staff"}, allowSetters = true)
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    List<DetailTask> detailTasks;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"staff"}, allowSetters = true)
    List<StaffFolder> staff_folders;

}
