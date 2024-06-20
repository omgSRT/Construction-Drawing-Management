package com.GSU24SE43.ConstructionDrawingManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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

    @ManyToOne
    @JoinColumn(name = "directoryId")
    Directory directory;

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Folder> folders;

    @ManyToOne
    @JoinColumn(name ="departmentId")
    Department department;

    @ManyToOne
    @JoinColumn(name = "accountId")
    Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "projectId")
    Staff staff;


}
