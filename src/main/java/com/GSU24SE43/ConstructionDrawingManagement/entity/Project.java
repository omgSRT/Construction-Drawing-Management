package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
    private String description;
    private Date creationDate;
    private Date startDate;
    private Date endDate;
    private String status;

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

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnoreProperties(value = { "project" }, allowSetters = true)
    List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "projectId")
    Staff staff;

    @ManyToMany(mappedBy = "projects")
    Set<Contractor> contractors;


}
