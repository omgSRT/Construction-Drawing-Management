package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class ProjectContractor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID projectContractorId;

    @ManyToOne
    @JoinColumn(name = "projectId")
    @JsonIgnoreProperties(value = {"projectContractors"}, allowSetters = true)
    Project project;

    @ManyToOne
    @JoinColumn(name = "contractorId")
    @JsonIgnoreProperties(value = {"projectContractors"}, allowSetters = true)
    Contractor contractor;
}
