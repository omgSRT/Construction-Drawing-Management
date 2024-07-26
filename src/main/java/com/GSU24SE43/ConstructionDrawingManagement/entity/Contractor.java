package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
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
    @Pattern(regexp = "\\d+", message = "Số điện thoại chỉ có thể bao gồm số")
    @Size(min = 8, max = 14, message = "Sô điện thoại tối thiểu 8 số và tối đa 14 số")
    String phone;
    @Email
    String email;
    @Pattern(regexp = "\\d+", message = "Mã số thuế chỉ có thể bao gồm số")
    @Size(min = 10, max = 13, message = "Độ dài mã số thuế tối thiểu 10 và tối đa 13 số")
    String taxIdentificationNumber;
    String businessLicense;

    @JsonIgnore
    @OneToMany(mappedBy = "contractor", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProjectContractor> projectContractors;
}
