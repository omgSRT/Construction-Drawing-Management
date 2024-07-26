package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID customerId;
    String customerName;
    String address;
    @Pattern(regexp = "\\d+", message = "Số điện thoại chỉ có thể bao gồm số")
    @Size(min = 8, max = 14, message = "Sô điện thoại tối thiểu 8 số và tối đa 14 số")
    String phone;
    @Email
    String email;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProjectCustomer> projectCustomers;
}
