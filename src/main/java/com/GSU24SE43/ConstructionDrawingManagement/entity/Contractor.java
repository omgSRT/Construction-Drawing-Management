package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contractor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID contractorId;
    String contractorName;
    String address;
    @Size(min = 8, max = 14, message = "Sô điện thoại tối thiểu 8 số và tối đa 14 số")
    String phone;
    @Email
    String email;
    @Size(max = 13, message = "TIN max is 13 number")
    int tax_identification_number;
    String business_license;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "contractor_project",
            joinColumns = @JoinColumn(name = "contractor_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    Set<Project> projects;
}
