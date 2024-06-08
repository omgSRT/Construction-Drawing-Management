package com.GSU24SE43.ConstructionDrawingManagement.entity;

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
    private String location;
    private Date creationDate;
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "folderId")
    Folder folder;


    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Drawing> drawings = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name ="departmentId")
    Department department;

    @ManyToOne
    @JoinColumn(name = "accountId")
    Account account;

}