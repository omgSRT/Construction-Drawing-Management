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
public class DepartmentProject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID departmentProjectId;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    @JsonIgnoreProperties(value = {"departmentProjects"}, allowSetters = true)
    Department department;

    @ManyToOne
    @JoinColumn(name = "projectId")
    @JsonIgnoreProperties(value = {"departmentProjects"}, allowSetters = true)
    Project project;
}
