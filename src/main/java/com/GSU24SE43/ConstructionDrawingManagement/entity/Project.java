package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String address;
    private String description;
    private Date creationDate;
    private Date startDate;
    private Date endDate;
    private String status;

    // Plot area details
    @DecimalMin(value = "25", message = "Diện tích tối thiểu là 25 m2")
    private double plotArea;
    private int maxFloorNumber;
    @DecimalMin(value = "2.1", message = "Độ cao tối thiểu phải là 2.1 mét")
    private double totalHeight;
    private String constructionTerms;
    //cốt nền (cao độ)
    @DecimalMin(value = "1.5", message = "Cốt nền tối thiểu phải là 1.5 mét")
    private double groundElevation;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FloorDetail> floorDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Folder> folders;

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    List<DepartmentProject> departmentProjects;

    @ManyToOne
    @JoinColumn(name = "accountId")
//    @JsonIgnoreProperties(value = { "projectList" }, allowSetters = true)
    @JsonIgnore
    Account account;

    @ManyToOne
    @JoinColumn(name = "landPurposeId")
    LandPurpose landPurpose;

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnoreProperties(value = { "project" }, allowSetters = true)
    List<Task> tasks;

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProjectCustomer> projectCustomers;
}
